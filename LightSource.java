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
        double b = brightness * Math.cos(Vector3.angle(between, normal)) / Math.pow(m,1.5);
        /*
        if (b < 0) {
            System.out.println("m: " + m);
            System.out.println("a: " + Math.cos(Vector3.angle(between, normal)));
            System.out.println("power:" + Math.pow(m,1.5));
            System.out.println("b:" + b);
        } */
        //TODO: How do I deal with negative values?
        if (b < 0) {
            return 0;
        }
        //TODO: How can I avoid using tanh?
        return Math.tanh(b);
    }
}
