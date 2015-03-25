package View;

import Module.*;

import java.util.*;

/**
 * Created by Hank on 22.11.2014.
 */
public class DrawObject extends Render {
    public Render eagle;
    public Render redBrik;
    public Render grass;
    public Render steel;
    public Render tank;
    public Render bullet;
    public Render enemyTank;
    public Render smallBullet;

    public DrawObject(int width, int height) {
        super(width, height);
        eagle = new Render(48, 48);
        eagle.pixels = Sprite.eagle.pixels;
        redBrik = new Render(48, 48);
        redBrik.pixels = Sprite.brik.pixels;
        grass = new Render(48, 48);
        grass.pixels = Sprite.grass.pixels;
        tank = new Render(45, 45);
        tank.pixels = Sprite.playerTank.pixels;
        bullet = new Render(48, 48);
        bullet.pixels = Sprite.bullet.pixels;
        enemyTank = new Render(45, 45);
        enemyTank.pixels = Sprite.enemyTank.pixels;
        steel = new Render(48, 48);
        steel.pixels = Sprite.steel.pixels;
        smallBullet = new Render(10,10);
        smallBullet.pixels = Sprite.smallBullet.pixels;
    }

    public void draw() {
       // clearPixels();
       // tank.pixels = Sprite.rotate(Sprite.playerTank, 90).pixels;
        //drawImage(tank, 10, 10);
    }

    public void draw(Board board) {
        clearPixels();
        List gameObjects = board.getGameObjects();
        for (int i = 0; i < gameObjects.size(); i++) {
            Board.ObjectContainer container = (Board.ObjectContainer) gameObjects.get(i);
            if(container.object instanceof RedBrick){
                drawImage(redBrik, container.x * 48, container.y * 48);
            }else if(container.object instanceof Grass){
                drawImage(grass, container.x * 48, container.y * 48);
            }else if(container.object instanceof Steel) {
                drawImage(steel, container.x * 48, container.y * 48);
            }else if(container.object instanceof Eagle) {
                drawImage(eagle, container.x * 48, container.y * 48);
            }
        }
    }

    public void drawTanks(Board board) {
        List tanks = board.getTanks();
        for (int i = 0; i < tanks.size(); i++) {
            Board.ObjectContainer container = (Board.ObjectContainer) tanks.get(i);
            if(container.object instanceof Player){
                tank.pixels = Sprite.rotate(Sprite.playerTank, container.vector.getAngle()).pixels;
                drawImage(tank, container.x, container.y);
            }else if(container.object instanceof Enemy){
                enemyTank.pixels = Sprite.rotate(Sprite.enemyTank, container.vector.getAngle()).pixels;
                drawImage(enemyTank, container.x, container.y);
            }
        }
    }

    public void drawBullets(Board board) {
        List bullets = board.getPlayerBullets();
        for (int i = 0; i < bullets.size(); i++) {
            Board.ObjectContainer container = (Board.ObjectContainer) bullets.get(i);
            if(container.object instanceof Bullet) {
                smallBullet.pixels = Sprite.rotate(Sprite.smallBullet, container.vector.getAngle()).pixels;
                drawImage(smallBullet, container.x, container.y);
            }
        }
    }
}
