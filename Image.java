import java.io.*;

public class Image {
    private String fileType = "P3";
    private int width;
    private int height;
    private int maxVal;
    private Rgb[][] raster = new Rgb[height][width];

    public Image(int w, int h, int m, Rgb[][] r) {
        width = w;
        height = h;
        maxVal = m;
        raster = r;
    }

    public void setPixel(int w, int h, Rgb c) {
        raster[h][w] = c;
    }

    public Rgb getPixel(int w, int h) {
        return raster[h][w];
    }

    public void writeToFile() throws IOException{
        System.out.println("Finished Generation, now writing to file.");
        BufferedWriter fileOut = new BufferedWriter(new FileWriter("image.ppm"));
        fileOut.write(fileType + "\n");
        fileOut.write(width + " " + height + "\n");
        fileOut.write(maxVal + "\n");
        for (Rgb[] row : raster) {
            for (Rgb rgb : row) {
                fileOut.write(rgb.toString() + "\t");
            }
        }
        fileOut.close();
    }

    public static void main(String[] args) throws IOException{
        int width = 200;
        int height = 100;
        Rgb[][] raster = new Rgb[height][width];
        for (int x=0; x<height; x++) {
            for (int y=0; y<width; y++) {
                //raster[x][y] = new Rgb ((float) y / 255,0,(float) x / 255); // Try using interpolation
            }
        }
        Image testImage = new Image(width, height, 255, raster);
        testImage.writeToFile();
    }
}
