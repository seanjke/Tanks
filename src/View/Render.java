package View;


import java.awt.Color;
/**
 * Created by Hank on 13.07.14.
 */
public class Render {
    public int width;
    public int height;
    public int[] pixels;

    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void drawImage(Render render, int xPos, int yPos) {
        for (int y = 0; y < render.height; y++) {
            int yCord = y + yPos;
            if(yCord < 0 || yCord >=480) {
                continue;
            }
            for (int x = 0; x < render.width; x++) {
               int xCord = x + xPos;
                if(xCord < 0 || xCord >=640) {
                    continue;
                }
                int alpha  = render.pixels[x + y * render.width];
                if(alpha != Color.MAGENTA.getRGB()){
                    pixels[xCord + yCord * width] = alpha;
                }
            }
        }
    }

    public void clearPixels() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }
}
