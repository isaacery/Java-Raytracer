public abstract class Shape extends Object {
    protected Rgb colour;

    public Shape(Vector3 pos, Rgb c) {
        super(pos);
        colour = c;
    }

    /*  Returns minimum t such that ray intersects at
        distance t from it's origin along in it's direction.
        Returns null if no intersection occurs.             */
    public abstract Intersection getIntersection(Ray ray);
    /*
    public Vector3 getNormal() { //TODO BAD CODE DON'T DO THIS
        return Vector3.ZERO;
    }
    */

    public Rgb getColour() {
        return colour;
    }
}
