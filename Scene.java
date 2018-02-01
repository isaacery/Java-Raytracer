import java.util.*;
import java.io.*;
//TODO: Lowering a lightsource raises the light in the scene??? FIXED (I think)
public class Scene {
    private Camera camera;
    private Shape[] shapes;
    private Rgb backgroundColour;
    private LightSource[] lights;
    private double ambientLight; // between 0 and 1

    public Scene(Shape[] shapes, LightSource[] lights) {
        this.camera = new Camera();
        this.shapes = shapes;
        this.lights = lights;
        this.ambientLight = 0.25;
        this.backgroundColour = Rgb.BLACK;
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
        for (int h = 1; h <= height; h++) {
            double h_d = 1 - (h_unit * h);
            for (int w = 1; w <= width; w++) {
                double w_d = (w_unit * w -1;
                Vector3 pos = new Vector3(w_d, h_d, 1);
                raster[h][w] = colourPoint(pos);
            }
        }
        return raster;
    }

    /* pos is the position vector of the pixel being coloured */
    public Rgb colourPoint(Vector3 pos) {
        Vector3 dir = Vector3.fromTo(camera.getPosition(), pos);
        Intersection closest = closestToPixel(dir);
        //If no shapeect is seen by the camera through pixel at pos, show background
        if (closest == null) {
            return backgroundColour;
        }
        Shape shape = closest.getShape();
        Vector3 intersectPoint =
            Vector3.add(camera.getPosition(), dir.scale(closest.getT()));
        // Assumes sphere
        //Vector3 normal = Vector3.fromTo(shape.getPosition(), intersectPoint);
        Vector3 normal = shape.getNormal(intersectPoint);
        double brightness = calculateShading(intersectPoint, normal);
        return shape.getColour().scaleBrightness(brightness);
    }

    private double calculateShading(Vector3 intersectPoint, Vector3 normal) {
        double shadowScalar = 1;
        double lightScalar = ambientLight;
        //Iterate over each light multiplying their effect on the shading.
        for (LightSource light: lights) {
            double shadow = calculateShadow(intersectPoint, light);
            shadowScalar *= shadow; //TODO Should I multiply or subtract here?
            if (shadow == 1) { //Light only illuminates point if nothing is blocking it.
                lightScalar += light.brightness(intersectPoint, normal);
            }
        }
        return shadowScalar * Math.min(1, lightScalar);
        //return Math.min(1, shadowScalar) * Math.min(1, lightScalar);
    }

    private double calculateShadow(Vector3 intersectPoint, LightSource light) {
        Vector3 dir = Vector3.fromTo(intersectPoint, light.getPosition());
        Ray shadowRay = new Ray(intersectPoint, dir);
        /*  Disregard any extremely small t values
            as they are a result of self intersection.  */
        Intersection i = minIntersection(filterGreaterThan(shadowRay.getIntersections(this), 0.0000000001));
        if (i == null) {
            return 1;
        }
        double t = i.getT();
        //TODO Something about taking min at this stage is cutting off shadows strangely.
        return Math.min(1, 5*t/light.getLuminance());
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
        Sphere s2 = new Sphere(new Vector3(-1.0,-1.0,3), 1, new Rgb(102,207,192));
        Sphere s3 = new Sphere(new Vector3(0.2,-1.75,2.7), 0.3, new Rgb(78,128,152));
        Plane p1 = new Plane(new Vector3(0,-2,0), Vector3.UP, new Rgb(0.3,0.3,0.3));
        Shape[] shapes_ = {s1, s2, s3, p1};
        LightSource l1 = new LightSource(new Vector3(-3,1,-2), 3);
        LightSource l2 = new LightSource(new Vector3(-5,0,-1), 2);
        LightSource[] lights_ = {l1, l2};
        Scene sc = new Scene(shapes_, lights_);
        Image i = new Image(1024, 1024, sc.toRaster(1024, 1024));
        i.writeToFile("test");
    }
}
