public class Plane extends Shape{
    private Vector3 normal;

    /* pos can be any point on the plane */
    public Plane(Vector3 pos, Vector3 normal, Rgb c) {
        super(pos, c);
        this.normal = normal.normalize();
    }

    public Intersection getIntersection(Ray ray) {
        Vector3 o = ray.getOrigin();
        Vector3 d = ray.getDirection();
        double k = Vector3.dot(d, normal);
        //ray will always intersect unless direction and normal are orthogonal
        if (k == 0) {
            return null;
        }
        double t = Vector3.dot(Vector3.fromTo(o, position), normal) / k;
        return new Intersection(t, this);
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector3 getNormal(Vector3 pos) { //TODO THIS IS BAD
        return normal;
    }

}
