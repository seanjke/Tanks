package View;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Hank on 11.08.14.
 */
public class Sprite {
    public int size;
    private int x;
    private int y;
    private int width, height;
    private SpriteTable spriteTable;
    public int[] pixels;
    public static Sprite eagle = new Sprite(48, 0, 0, SpriteTable.sheet);
    public static Sprite playerTank  = new Sprite(45, 0, 0, SpriteTable.tanksSheet);
    public static Sprite brik  = new Sprite(48, 48, 0, SpriteTable.sheet);
    public static Sprite grass  = new Sprite(48, 96, 0, SpriteTable.sheet);
    public static Sprite bullet  = new Sprite(48, 144, 0, SpriteTable.sheet);
    public static Sprite smallBullet = new Sprite(10, 0, 0, SpriteTable.bullets);
    public static Sprite enemyTank = new Sprite(45, 45, 0, SpriteTable.tanksSheet);
    public static Sprite steel = new Sprite(48, 0, 48, SpriteTable.sheet);
    public static int COUNTER;

    public Sprite(int size, int x, int y, SpriteTable spriteTable) {
        this.size = size;
        this.x = x;
        this.y = y;
        this.spriteTable = spriteTable;
        pixels = new int[size * size];
        loadSprite();
    }

    public Sprite(int[] pixels, int width, int height){
        this.size = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }

    private void loadSprite() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[x + y * size] = spriteTable.pixels[(x + this.x) + (y + this.y) * spriteTable.size];
            }
        }
    }

    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.size, sprite.size, angle),sprite.size, sprite.size);
    }

    private static double rot_x(double angle, double x, double y) {
        double cos = Math.cos(Math.toRadians(angle) - Math.PI / 2);
        double sin = Math.sin(Math.toRadians(angle) - Math.PI / 2);
        return  x * cos + y * -sin;
    }

    private static double rot_y(double angle, double x, double y) {
        double cos = Math.cos(Math.toRadians(angle) - Math.PI / 2);
        double sin = Math.sin(Math.toRadians(angle) - Math.PI / 2);
        return  x * sin + y * cos;
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle){
        int[] result = new int[width * height];
        double newX_x = rot_x(-angle, 1.0, 0.0);
        double newX_y = rot_y(-angle, 1.0, 0.0);
        double newY_x = rot_x(-angle, 0.0, 1.0);
        double newY_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int color = 0;
                if(xx < 0 || xx >= width || yy < 0 || yy >= height) {
                    color = Color.MAGENTA.getRGB();
                }else {
                    color = pixels[xx + yy * width];
                }
                try {
                    result[x + y * width] = color;
                }catch (IndexOutOfBoundsException e) {
                    continue;
                }
                x1 += newX_x;
                y1 += newX_y;
            }
            x0 += newY_x;
            y0 += newY_y;
        }
        COUNTER++;
        return result;
    }

}
