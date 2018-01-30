public class Intersection {
    private double t;
    private Shape obj;

    public Intersection(double t, Shape obj) {
        this.t = t;
        this.obj = obj;
    }

    public double getT() {
        return t;
    }

    public Shape getObject() {
        return obj;
    }
}
