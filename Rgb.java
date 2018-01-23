public class Rgb { //
    /*  Values range from 0 to 1, with 1 indicating
        the strongest intensity of each color       */
    private double r;
    private double g;
    private double b;

    public Rgb(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Rgb(Vector3 rgb) {
        this.r = rgb.getX();
        this.g = rgb.getY();
        this.b = rgb.getZ();
    }

    public String toString() {
        return (int) (r*255) + " " + (int) (g*255) + " " + (int) (b*255);
    }

    /*  Converts RGB value to int in the format
        accepted by BufferedImage.TYPE_INT_RGB  */
    public int toInt() {
        int rgb = (int) (r*255);
        rgb = (rgb << 8) + (int) (g*255);
        rgb = (rgb << 8) + (int) (b*255);
        return rgb;
    }

    /*  Converts RGB value to vector  */
    public Vector3 toVector() {
        return new Vector3(r,g,b);
    }

    public static Rgb RED = new Rgb(1,0,0);
    public static Rgb GREEN = new Rgb(0,1,0);
    public static Rgb BLUE = new Rgb(0,0,1);
    public static Rgb BLACK = new Rgb(0,0,0);
    public static Rgb WHITE = new Rgb(1,1,1);
}
