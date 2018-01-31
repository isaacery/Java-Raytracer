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

    //TODO: Should this be static?
    public Vector3 normalize() {
        return this.scale(1/magnitude(this));
    }

    public static Vector3 ZERO = new Vector3(0,0,0);
    // FORWARD is defined as a direction vector paralel to the z axis
    public static Vector3 FORWARD = new Vector3(1,0,0);

    public static Vector3 UP = new Vector3(0,1,0);

    public static double dot(Vector3 a, Vector3 b) {
        return (a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ());
    }

    public static double magnitude(Vector3 a) {
        return Math.sqrt(dot(a,a));
    }

    public static Vector3 add(Vector3 a, Vector3 b) {
        return new Vector3(a.getX()+b.getX(), a.getY()+b.getY(), a.getZ()+b.getZ());
    }

    // Returns vector from a to b
    public static Vector3 fromTo(Vector3 a, Vector3 b) {
        return add(b, a.scale(-1));
        //return new Vector3 (b.getX()-a.getX(), b.getY()-a.getY(), b.getZ()-a.getZ());
    }

    // Returns acute angle between a and b in radians
    public static double angle(Vector3 a, Vector3 b) {
        return Math.acos(dot(a,b) / (magnitude(a) * magnitude(b)));
    }

    // Multiplies each component by some scalar k
    public Vector3 scale(double k) {
        return new Vector3(x*k, y*k, z*k);
    }

}
