import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

// writeToFile outputs image contained in raster to .PNG file

public class Image {
    private int width;
    private int height;
    private Rgb[][] raster = new Rgb[height][width];

    public Image(int w, int h, Rgb[][] r) {
        width = w;
        height = h;
        raster = r;
    }

    public void setPixel(int w, int h, Rgb c) {
        raster[h][w] = c;
    }

    public Rgb getPixel(int w, int h) {
        return raster[h][w];
    }

    public void writeToFile(String fileName) throws IOException{
        System.out.println("Finished Generation, now writing to file.");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(i,j,raster[j][i].toInt());
            }
        }
        ImageIO.write(image, "png", new File(fileName + ".png"));
    }

    public static void main(String[] args) throws IOException{
    }
}
