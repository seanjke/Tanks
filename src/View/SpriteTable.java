package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Hank on 11.08.14.
 */
public class SpriteTable {
    private String path;
    public int size;
    public int pixels[];
    public static SpriteTable sheet = new SpriteTable("/View/Images/Sheet.png", 480);
    public static SpriteTable bullets = new SpriteTable("/View/Images/bullets.png", 100);
    public static SpriteTable tanksSheet = new SpriteTable("/View/Images/TanksSheet.png", 450);
    public SpriteTable(String path, int size) {
        this.path = path;
        this.size = size;
        pixels = new int[size * size];
        LoadImage();
    }

    public void LoadImage(){
        try {
            BufferedImage bufferedImage = ImageIO.read(this.getClass().getResource(path));
            int w = bufferedImage.getWidth();
            int h = bufferedImage.getHeight();
            bufferedImage.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
