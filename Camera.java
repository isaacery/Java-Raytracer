public class Camera extends Object {
    private Vector3 direction;

    public Camera() {
        super(Vector3.ZERO);
        direction = Vector3.FORWARD; //TODO: Implement direction properly
    }

    /*
    public Intersection closestIntersection(Vector3 pos) {
        Vector3 dir = Vector3.fromTo(this.position, pos);
        Ray ray = new Ray(this.position, dir);
        LinkedList<Intersection> intersections = ray.getIntersections(this);
        if (intersections.size() == 0) {
            return null;
        }
        for (Intersection i: intersections) {
            if (t <= 1) {

            }
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

    private Intersection minIntersection() {

    }

    public Camera(Vector3 dir) {
        super(Vector3.ZERO, false);
        direction = dir;
    }
    */
}
