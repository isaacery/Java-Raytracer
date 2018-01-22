public class Rgb { //Values range from 0 to 1, with 1 being fully red/green/blue
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

}
