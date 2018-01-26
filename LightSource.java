public class LightSource extends Object {
    private double brightness = 10;
    public LightSource(Vector3 pos) {
        super(pos, false);
    }

    /*  Returns brightness value between 0 and 1
        between this light source and a point 'pos'
        on an object with surface normal 'normal'   */
    public double brightness(Vector3 pos, Vector3 normal) {
        Vector3 between = Vector3.fromTo(pos, this.position); //TODO Is this the correct direction?
        double m = Vector3.magnitude(between);
        double b = brightness * Math.max(Vector3.dot(between, normal), 0) / Math.pow(m,2);
        //TODO: How can I avoid using tanh?
        return Math.tanh(b);
    }
    /*
    public double shadow(Intersection[] i) {
        Vector3 dir = Vector3.fromTo(this.position, pos);
        Ray ray = new Ray(this.position, dir);

    }
    */
}
