public class LightSource extends Object {
    private double luminance;
    public LightSource(Vector3 pos, double luminance) {
        super(pos);
        this.luminance = luminance;
    }

    /*  Returns brightness value between 0 and 1
        between this light source and a point 'pos'
        on an object with surface normal 'normal'   */
    //TODO Currently works only for spheres (I think)?
    public double brightness(Vector3 pos, Vector3 normal) {
        Vector3 between = Vector3.fromTo(pos, this.position); //TODO Is this the correct direction?
        double m = Vector3.magnitude(between);
        //TODO Should I divide by magnitudes of between and normal?
        double b = luminance * Math.max(Vector3.dot(between, normal), 0) / Math.pow(m,1.75);
        //TODO: How can I avoid using tanh?
        return Math.tanh(b);
    }

    public double getLuminance(){
        return luminance;
    }
    /*
    public double shadow(Intersection[] i) {
        Vector3 dir = Vector3.fromTo(this.position, pos);
        Ray ray = new Ray(this.position, dir);

    }
    */
}
