import java.util.*;
import java.io.*;
//TODO: Lowering a lightsource raises the light in the scene??? FIXED (I think)
public class Scene {
    private Camera camera;
    private Sphere[] shapes;
    private Rgb backgroundColour;
    private LightSource light; //TODO Multiple

    public Scene(Sphere[] shapes) {
        this.camera = new Camera();
        this.shapes = shapes;
        this.light = new LightSource(new Vector3(-0.5,-3,-2));
        backgroundColour = Rgb.BLACK;
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
                raster[h][w] = shadePoint(pos);
            }
        }
        return raster;
    }

    public Rgb shadePoint(Vector3 pos) {
        Vector3 dir = Vector3.fromTo(Vector3.ZERO, pos);
        Ray ray = new Ray(Vector3.ZERO, dir);
        Intersection i = minIntersection(getIntersections(ray));
        if (i == null) {
            return backgroundColour;
        }
        Sphere obj = i.getObject();
        Vector3 intersectPoint =
            Vector3.add(ray.getOrigin(), Vector3.scale(ray.getDirection(),i.getT()));
        Vector3 normal = Vector3.fromTo(obj.getPosition(), intersectPoint);
        double b_light = light.brightness(intersectPoint, normal); //TODO Multiple lights

        Ray shadowRay = new Ray(intersectPoint, light.getPosition());
        boolean shadow = false;
        for (Intersection i2: getIntersections(shadowRay)) {
            if (i2 != null) {
                if (i2.getT() >= 0.000000001) { //TODO built-in epsilon?
                    b_light = 0;
                }
            }
        }

        return obj.getColour().scaleBrightness(b_light);
    }

    private LinkedList<Intersection> getIntersections(Ray ray) {
        LinkedList<Intersection> intersections = new LinkedList<>();
        for (Sphere s: shapes) {
            if (s.getIntersection(ray) != null) {
                intersections.add(s.getIntersection(ray));
            }
        }
        return intersections;
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
        Sphere s1 = new Sphere(new Vector3(0,0,3), 1.0, new Rgb(163,22,33));
        Sphere s2 = new Sphere(new Vector3(-0.5,0.75,1.75), 0.6, new Rgb(102,207,192));
        Sphere s3 = new Sphere(new Vector3(0,0,2), 0.3, new Rgb(78,128,152));
        Sphere[] spheres = {s1, s2, s3};
        Scene sc = new Scene(spheres);
        Image i = new Image(1024, 1024, sc.toRaster(1024, 1024));
        i.writeToFile("test3");
    }
}
