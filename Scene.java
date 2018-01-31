import java.util.*;
import java.io.*;
//TODO: Lowering a lightsource raises the light in the scene??? FIXED (I think)
public class Scene {
    private Camera camera;
    private Shape[] shapes;
    private Rgb backgroundColour;
    private LightSource[] lights;

    public Scene(Shape[] shapes, LightSource[] lights) {
        this.camera = new Camera();
        this.shapes = shapes;
        this.lights = lights;
        backgroundColour = Rgb.BLACK;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    //Currently assumes standard camera direction.
    public Rgb[][] toRaster(int width, int height) {
        Rgb[][] raster = new Rgb[height][width];
        /*  w_unit and h_unit are the physical spacing between pixels
            (width and height respectively) in the scene in order
            for the image to occupy a 2x2 square centred at (0,0,1).  */
        double w_unit = 2 / ((double) width);
        double h_unit = 2 / ((double) height);
        for (int h = 0; h < height; h++) {
            double h_d = 1 - (h_unit * (h+1));
            for (int w = 0; w < width; w++) {
                double w_d = (w_unit * (w+1)) -1;
                Vector3 pos = new Vector3(w_d, h_d, 1);
                raster[h][w] = colourPoint(pos);
            }
        }
        return raster;
    }

    /* pos is the position vector of the pixel being coloured */
    //TODO Update for multiple lights
    public Rgb colourPoint(Vector3 pos) {
        Vector3 dir = Vector3.fromTo(camera.getPosition(), pos);
        Intersection closest = closestToPixel(dir);
        //If no object is seen by the camera through pixel at pos, show background
        if (closest == null) {
            return backgroundColour;
        }
        Shape obj = closest.getObject();
        Vector3 intersectPoint =
            Vector3.add(camera.getPosition(), dir.scale(closest.getT()));
        Vector3 normal = Vector3.fromTo(obj.getPosition(), intersectPoint);
        double b_light = calculateBrightness(intersectPoint, normal);
        double b_shadow = calculateShadow(intersectPoint, normal);
        return obj.getColour().scaleBrightness(b_light * b_shadow);
    }

    private double calculateShadow(Vector3 intersectPoint, Vector3 normal) {
        //TODO unsure if I need normal
        double brightness = 1;
        //Iterate over each light multiplying their effect on the shadows.
        for (LightSource light: lights) {
            Vector3 dir = Vector3.fromTo(intersectPoint, light.getPosition());
            Ray shadowRay = new Ray(intersectPoint, dir);
            /*  Disregard any extremely small t values
                as they are a result of self intersection.  */
            Intersection i = minIntersection(filterGreaterThan(shadowRay.getIntersections(this), 0.0000000001));
            if (i != null) {
                double t = i.getT();
                brightness *= Math.min(1,5*t/light.getLuminance()); //TODO is this accurate?
            }
        }
        return brightness;
    }

    private double calculateBrightness(Vector3 intersectPoint, Vector3 normal) {
        double brightness = 0;
        for (LightSource light: lights) {
            brightness += light.brightness(intersectPoint, normal); //TODO Should I add here?
        }
        return Math.min(1, brightness);
    }

    /*  Returns an intersection closest to the
        camera beyond the pixel in direction dir.    */
    private Intersection closestToPixel(Vector3 dir) {
        Ray ray = new Ray(camera.getPosition(), dir);
        return minIntersection(filterGreaterThan(ray.getIntersections(this), 1));
    }

    /*  Removes interesections less than or equal to 1.
        Used to determine which intersections are
        in front of pixels and thus in view.            */
    private LinkedList<Intersection> filterGreaterThan(LinkedList<Intersection> intersections, double n) {
        LinkedList<Intersection> new_list = new LinkedList<Intersection>();
        for (Intersection i: intersections) {
            if (i.getT() > n) {
                new_list.add(i);
            }
        }
        return new_list;
    }

    /*  Returns minimum intersection in list, null if list is empty. */
    private Intersection minIntersection(LinkedList<Intersection> intersections) {
        if (intersections.size() == 0) {
            return null;
        }
        Intersection min =
            Collections.min(intersections, Comparator.comparingDouble(Intersection::getT));
        return min;
    }

    public static void main (String[] args) throws IOException {
        Sphere s1 = new Sphere(new Vector3(1.5,0,5), 2, new Rgb(163,22,33));
        Sphere s2 = new Sphere(new Vector3(-0.5,0.75,1.75), 0.6, new Rgb(102,207,192));
        Sphere s3 = new Sphere(new Vector3(0.1,0.3,2.2), 0.3, new Rgb(78,128,152));
        Shape[] shapes_ = {s1, s2, s3};
        LightSource l1 = new LightSource(new Vector3(-3,1,-2), 5);
        LightSource l2 = new LightSource(new Vector3(-0.5,1,-1), 5);
        LightSource[] lights_ = {l1,l2};
        Scene sc = new Scene(shapes_, lights_);
        Image i = new Image(1024, 1024, sc.toRaster(1024, 1024));
        i.writeToFile("test3");
    }
}
