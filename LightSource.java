public class LightSource extends Object {
    private double brightness = 10;
    public LightSource(Vector3 pos) {
        super(pos, false);
    }

    /*  Returns brightness value between 0 and 1
        between this light source and a point 'pos'
        on an object with surface normal 'normal'   */
    public double brightness(Vector3 pos, Vector3 normal) {
        Vector3 between = Vector3.fromTo(this.position, pos);
        double m = Vector3.magnitude(between);
        double b = brightness * Math.cos(Vector3.angle(between, normal)) / Math.pow(m,2);
        return Math.tanh(b);
    }
}
