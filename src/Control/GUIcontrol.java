package Control;

import Main.InputHandler;
import Module.Board;
import View.DrawObject;
import View.Screen;
import View.Color;
import View.Font;



/**
 * Created by Hank on 26.11.2014.
 */
public class GUIcontrol {
    private DrawObject field = new DrawObject(640, 480);
    private Screen screen;
    private int [] colors;
    private int[] pixels;

    public void setScreen(Screen screen){
        this.screen = screen;
    }

    public void setColors(int[] colors){
        this.colors = colors;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public void drawGameField() {
        field.draw();
        for (int i = 0; i < field.pixels.length; i++) {
            pixels[i] = field.pixels[i];
        }
    }

    public void drawObjects(Board board) {
        field.draw(board);
        for (int i = 0; i < field.pixels.length; i++) {
            if(field.pixels[i] != 0){
                pixels[i] = field.pixels[i];
            }
        }
    }

    public void drawTanks(Board board) {
        field.drawTanks(board);
        for (int i = 0; i < field.pixels.length; i++) {
            pixels[i] = field.pixels[i];
        }
    }

    public void drawBullets(Board board) {
        field.drawBullets(board);
        for (int i = 0; i < field.pixels.length; i++) {
            pixels[i] = field.pixels[i];
        }
    }

    public void drawText(String msg, int yOffSet) {
       Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, screen.h / 2 + yOffSet, Color.get(0, 555, 555, 555));
        for (int y = 0; y < screen.h; y++) {
            for (int x = 0; x < screen.w; x++) {
                int cc = screen.pixels[x + y * screen.w];
                if (cc < 255) pixels[x + y * field.width] = colors[cc];
            }
        }
    }

    public void clearAll() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
        for (int i = 0; i < screen.pixels.length; i++) {
            screen.pixels[i] = 0;
        }
    }

    public void drawStatistics(Board board) {
        String dies = "Deaths - " + Integer.toString(board.getDies().size());
        String kills = "Kills - " + Integer.toString(board.getKills().size());
        int xx = 640 - dies.length() * 8;
        int yy = 0;
        Font.draw(dies, screen, xx, yy, Color.get(0, 555, 555, 555));
        Font.draw(kills, screen, xx, yy + 8, Color.get(0, 555, 555, 555));
        for (int y = yy; y < yy + 16; y++) {
            for (int x = xx; x < xx + dies.length() * 8; x++) {
                int cc = screen.pixels[x + y * screen.w];
                if(cc == 0) continue;
                if (cc < 255) pixels[x + y * field.width] = colors[cc];
            }
        }
    }

    public void drawMainMenu() {
        clearAll();
        drawText("Start game", 0);
        drawText("Exit", 16);
        drawText("Press enter to start game", 16 * 4);
    }

    public void drawDeadMenu() {
        clearAll();
        drawText("game over !", 0);
        drawText("press enter to restart", 16);
    }
}
