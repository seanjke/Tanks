package Module;
import java.awt.*;
import java.util.List;

/**
 * Created by Hank on 06.01.2015.
 */
public class Collision {
    public Rectangle rObs = new Rectangle();
    public Rectangle rTank = new Rectangle();
    public Board board;

    public Collision(Board board) {
        this.board = board;
    }

    public boolean clash(Board.ObjectContainer objectContainer, List<Board.ObjectContainer> gameObjects, Vector2D vector) {
        Vector2D pos = vector;
        for (int slot = 0; slot < gameObjects.size(); slot++) {
            Board.ObjectContainer gameObject = gameObjects.get(slot);
            if (gameObject.object instanceof Obstacle) {
                rObs.setBounds(gameObject.x * 48, gameObject.y * 48, 48, 48);
                if(objectContainer.object instanceof  Bullet) {
                    rTank.setBounds(objectContainer.x + pos.getX(), objectContainer.y + pos.getY(), 10, 10);
                    if (rTank.intersects(rObs) & !((Obstacle) gameObject.object).transparent) {
                       if(((Obstacle) gameObject.object).isDestroyable()){
                           gameObjects.remove(slot);
                       }
                        return true;
                    }
                    if(objectContainer.x + pos.getX() < 0 || objectContainer.x + pos.getX() > 630 || objectContainer.y + pos.getY() < 0 || objectContainer.y + pos.getY() > 470) {
                        return true;
                    }
                } else {
                    rTank.setBounds(objectContainer.x + pos.getX(), objectContainer.y+ pos.getY(), 46, 46);
                    if (rTank.intersects(rObs) & !((Obstacle) gameObject.object).transparent) {
                        Rectangle inter = rTank.intersection(rObs);
                        double area  = inter.getHeight() * inter.getWidth();
                        if(area < 10.0) {
                            System.out.println(area);
                            System.out.println(" Y " + inter.getY());
                            System.out.println(" X " + inter.getX());
                            if(vector.getX() == 0 & vector.getY() == 1) { //Если едем вниз
                                if(objectContainer.x == inter.getX()) {
                                    objectContainer.x = objectContainer.x + (int) inter.getWidth(); // Уперлись правым бортом
                                }else {
                                    objectContainer.x = objectContainer.x - (int) inter.getWidth(); // Уперлись левым бортом
                                }
                            }else if(vector.getX() == 1 & vector.getY() == 0){ //Если едем направо
                                System.out.println("Y " + objectContainer.y + " " + " X " + objectContainer.x);

                                if(objectContainer.y == inter.getY()) {
                                    objectContainer.y = objectContainer.y + (int)inter.getHeight();
                                }else {
                                    objectContainer.y = objectContainer.y - (int)inter.getHeight();
                                }
                            }else if(vector.getX() == -1 & vector.getY() == 0){ //Если едем налево
                                if(objectContainer.y == inter.getY()) {
                                    objectContainer.y = objectContainer.y + (int)inter.getHeight();
                                }else {
                                    objectContainer.y = objectContainer.y - (int)inter.getHeight();
                                }
                            }else if(vector.getX() == 0 & vector.getY() == -1){ //Если едем вверх
                                if(objectContainer.x == inter.getX()) {
                                    objectContainer.x = objectContainer.x + (int) inter.getWidth(); // уперлись левым бортом
                                }else {
                                    objectContainer.x = objectContainer.x - (int) inter.getWidth(); // уперлись правым бортом
                                }
                            }
                        }
                        return true;
                    }
                    if(objectContainer.x + pos.getX() < 0 || objectContainer.x + pos.getX() > 592 || objectContainer.y + pos.getY() < 0 || objectContainer.y + pos.getY() > 432) {
                        return true;
                    }
                }
            }else if(gameObject.object instanceof Enemy) {
                rTank.setBounds(0,0,0,0);
                rObs.setBounds(0,0,0,0);
                rObs.setBounds(gameObject.x, gameObject.y, 46, 46);
                if(objectContainer.object instanceof  Bullet) {
                    rTank.setBounds(objectContainer.x + pos.getX(), objectContainer.y + pos.getY(), 10, 10);
                    if (rTank.intersects(rObs) & ((Bullet) objectContainer.object).isCanHitEnemy()) {
                        gameObjects.remove(slot);
                        board.playerKillEnemey();
                        return true;
                    }
                }
            } else if(gameObject.object instanceof Player) {
                rTank.setBounds(0,0,0,0);
                rObs.setBounds(0,0,0,0);
                rObs.setBounds(gameObject.x, gameObject.y, 46, 46);
                if(objectContainer.object instanceof  Bullet) {
                    rTank.setBounds(objectContainer.x + pos.getX(), objectContainer.y + pos.getY(), 10, 10);
                    if (rTank.intersects(rObs) & ((Bullet) objectContainer.object).isCanHitPlayer()) {
                        board.playerDied();
                        board.setPlayerOnRespawn();
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
