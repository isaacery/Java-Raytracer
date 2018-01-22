public class Intersection {
    private double t;
    private Sphere obj;

    public Intersection(double t, Sphere obj) {
        this.t = t;
        this.obj = obj;
    }

    public double getT() {
        return t;
    }

    public Sphere getObject() {
        return obj;
    }
}
