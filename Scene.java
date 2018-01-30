import java.util.*;
import java.io.*;
//TODO: Lowering a lightsource raises the light in the scene???
public class Scene {
    private Camera camera;
    private Sphere[] shapes;
    private Rgb backgroundColour;
    private LightSource light; //TODO Multiple

    public Scene(Sphere[] shapes) {
        this.camera = new Camera();
        this.shapes = shapes;
        this.light = new LightSource(new Vector3(-3,1,-2));
        backgroundColour = Rgb.BLACK;
    }

    public Sphere[] getShapes() {
        return shapes;
    }

    //Currently assumes standard camera direction.
    public Rgb[][] toRaster(int width, int height) {
        Rgb[][] raster = new Rgb[height][width];
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

    public Rgb colourPoint(Vector3 pos) {
        Vector3 dir = Vector3.fromTo(camera.getPosition(), pos);
        Intersection closest = closestToCamera(dir);
        if (closest == null) {
            return backgroundColour;
        }
        Sphere obj = closest.getObject();
        Vector3 intersectPoint =
            Vector3.add(camera.getPosition(), Vector3.scale(dir,closest.getT()));
        Vector3 normal = Vector3.fromTo(obj.getPosition(), intersectPoint);
        double b_light = light.brightness(intersectPoint, normal); //TODO Multiple lights
        double b_shadow = calculateShadow(intersectPoint, normal);
        return obj.getColour().scaleBrightness(b_light * b_shadow);
    }

    private double calculateShadow(Vector3 intersectPoint, Vector3 normal) {
        Ray shadowRay = new Ray(intersectPoint, light.getPosition());
        double brightness = 1;
        for (Intersection i2: shadowRay.getIntersections(this)) {
            if (i2 != null) {
                double t = i2.getT();
                if (t >= 0.000000001) { //TODO built-in epsilon?
                    brightness *= Math.min(1,10*t/light.getBrightness());
                }
            }
        }
        return brightness;
    }

    /*  Returns an intersection closest to the
        camera beyond the pixel in direction dir.    */
    private Intersection closestToCamera(Vector3 dir) {
        Ray ray = new Ray(camera.getPosition(), dir);
        return minIntersection(ray.getIntersections(this));
    }

    private Intersection minIntersection(LinkedList<Intersection> intersections) {
        if (intersections.size() == 0) {
            return null;
        }
        Intersection min = intersections.getFirst();
        for (Intersection i: intersections) {
            double t = i.getT();
            if (t > 1 && t < min.getT()) {
                min = i;
            }
        }
        if (min.getT() > 1) {
            return min;
        } else {
            return null;
        }
    }

    public static void main (String[] args) throws IOException {
        Sphere s1 = new Sphere(new Vector3(1,0,3), 1.0, new Rgb(163,22,33));
        Sphere s2 = new Sphere(new Vector3(-0.5,0.75,1.75), 0.6, new Rgb(102,207,192));
        Sphere s3 = new Sphere(new Vector3(-0.25,0,1.75), 0.3, new Rgb(78,128,152));
        Sphere[] spheres = {s1, s2, s3};
        Scene sc = new Scene(spheres);
        Image i = new Image(1024, 1024, sc.toRaster(1024, 1024));
        i.writeToFile("test3");
    }
}
