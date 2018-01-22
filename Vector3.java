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

    public static Vector3 ZERO = new Vector3(0,0,0);
}
