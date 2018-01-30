import java.util.*;
public class Ray {
    private Vector3 origin;
    private Vector3 direction;
    public Ray(Vector3 origin, Vector3 dir) {
        this.origin = origin;
        this.direction = dir;
    }
    /*  Calculates intersections between this
        ray and the spheres in provided scene   */
    public LinkedList<Intersection> getIntersections(Scene scene) {
        LinkedList<Intersection> intersections = new LinkedList<>();
        for (Shape s: scene.getShapes()) {
            if (s.getIntersection(this) != null) {
                intersections.add(s.getIntersection(this));
            }
        }
        return intersections;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 getOrigin() {
        return origin;
    }
}
