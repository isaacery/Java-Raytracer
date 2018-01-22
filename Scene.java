import java.util.*;
import java.io.*;
public class Scene {
    private Camera camera;
    private Sphere[] shapes;
    private Rgb backgroundColour;
    //private LightSource[] lights; TODO

    public Scene(Sphere[] shapes) {
        this.camera = new Camera();
        this.shapes = shapes;
        backgroundColour = Rgb.BLUE;
    }

    //Currently assumes standard camera direction.
    public Rgb[][] toRaster(int width, int height) {
        Rgb[][] raster = new Rgb[height][width];
        double w_unit = 2 / ((double) width);
        double h_unit = 2 / ((double) height);
        for (int h = 0; h < height; h++) {
            double h_d = 1 - (h_unit * h);
            for (int w = 0; w < width; w++) {
                double w_d = 1 - (w_unit * w);
                Vector3 pos = new Vector3(w_d, h_d, 1);
                Vector3 dir = Vector3.between(Vector3.ZERO, pos);
                //System.out.println("pos: " + pos.toString());
                //System.out.println("dir: " + dir.toString());
                Ray ray = new Ray(Vector3.ZERO, dir);
                Intersection i = getNearestIntersection(ray);
                if (i == null) {
                    raster[h][w] = backgroundColour;
                } else {
                    System.out.println("a");
                    raster[h][w] = i.getObject().getColour();
                }
            }
        }
        return raster;
    }

    private Intersection getNearestIntersection(Ray ray) {
        LinkedList<Intersection> intersections = new LinkedList<>();
        for (Sphere s: shapes) {
            if (s.getIntersection(ray) != null) {
                System.out.println("a");
                intersections.add(s.getIntersection(ray));
            }
        }
        return minIntersection(intersections);
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
        Sphere sp = new Sphere(Vector3.scale(Vector3.FORWARD, 2.0), 1.0, Rgb.RED);
        Sphere[] spheres = {sp};
        Scene sc = new Scene(spheres);
        Image i = new Image(1024, 1024, sc.toRaster(1024, 1024));
        i.writeToFile("test1");
    }
}
