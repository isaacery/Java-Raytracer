public class Sphere extends Shape {
    private double radius;
    private Rgb colour;
    
    public Sphere(Vector3 pos, double r, Rgb c) {
        super(pos, c);
        radius = r;
    }

    public Intersection getIntersection(Ray ray) {
        //TODO: how do we deal with the case where the ray doesn't intersect?
        Vector3 d = ray.getDirection();
        Vector3 o = ray.getOrigin();
        Vector3 s = position;
        Vector3 s_to_o = Vector3.fromTo(s, o);
        double a = Vector3.dot(d, d);
        double b = Vector3.dot(Vector3.scale(d, 2), s_to_o);
        double c = Vector3.dot(s_to_o, s_to_o) - Math.pow(radius,2);
        double t1 = ((-1 * b) + Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2 * a);
        double t2 = ((-1 * b) - Math.sqrt(Math.pow(b,2) - 4*a*c)) / (2 * a);
        //TODO: figure out what to do with t2

        if (Double.isNaN(t2)) {
            return null;
        }
        return new Intersection(t2, this);
    }
}
