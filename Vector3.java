public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return ("[" + x + "," + y + "," + z + "]");
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }

    public static Vector3 ZERO = new Vector3(0,0,0);
    // FORWARD is defined as a direction vector paralel to the z axis
    public static Vector3 FORWARD = new Vector3(1,0,0);

    public static double dot(Vector3 a, Vector3 b) {
        return (a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ());
    }

    public static double magnitude(Vector3 a) {
        return Math.sqrt(dot(a,a));
    }

    // Returns vector between a and b.
    public static Vector3 between(Vector3 a, Vector3 b) {
        return new Vector3 (b.getX()-a.getX(), b.getY()-a.getY(), b.getZ()-a.getZ());
    }

    // Multiplies each component by some scalar k
    public static Vector3 scale(Vector3 a, double k) {
        return new Vector3(a.getX() * k, a.getY() * k, a.getZ() * k);
    }

    //TODO: Should this be static?
    public static Vector3 normalize(Vector3 a) {
        return scale(a, 1/magnitude(a));
    }

    public static double angle(Vector3 a, Vector3 b) {
        return 0; //TODO
    }

}
