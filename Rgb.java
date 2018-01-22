public class Rgb { //
    /*  Values range from 0 to 1, with 1 indicating
        the strongest intensity of each color       */
    private float r;
    private float g;
    private float b;

    public Rgb(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
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
}
