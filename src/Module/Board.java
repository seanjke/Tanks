package Module;

import Control.MoveControl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Board {
    private int MAX_ENEMY_ON_FIELD = 3;
    private List<ObjectContainer> gameObjects = new ArrayList<ObjectContainer>();
    private List<ObjectContainer> tanks = new ArrayList<ObjectContainer>();
    private List<ObjectContainer> playerBullets = new ArrayList<ObjectContainer>();
    private List kills = new ArrayList();
    private List dies = new ArrayList();
    private EnemyLogic logic = new EnemyLogic();
    private Collision collision = new Collision(this);
    private Level level = new Level();
    //EnemyCoords enemyCoords = new EnemyCoords();

    public static int PLAYER_SLOT;
    public List getGameObjects() {
        return this.gameObjects;
    }

    public void loadLevel() throws IOException {
        int lvlSize = level.getLvlSize();
        int[] lvl = level.getLevel();
        for (int y = 0; y < lvlSize; y++) {
            for (int x = 0; x < lvlSize; x++) {
                if(lvl[x + y * lvlSize] == 1) {
                  RedBrick redBrick = new RedBrick(false);
                    redBrick.setDestroyable();
                  ObjectContainer objectContainer =  new ObjectContainer(x, y, redBrick);
                    gameObjects.add(gameObjects.size(), objectContainer);
                }else if(lvl[x + y * lvlSize] == 2){
                    ObjectContainer objectContainer =  new ObjectContainer(x, y, new Steel(false));
                    gameObjects.add(gameObjects.size(), objectContainer);
                }else if(lvl[x + y * lvlSize] == 3){
                    ObjectContainer objectContainer =  new ObjectContainer(x, y, new Steel(false));
                    gameObjects.add(gameObjects.size(), objectContainer);
                }else if(lvl[x + y * lvlSize] == 4){
                    ObjectContainer objectContainer =  new ObjectContainer(x, y, new Grass(true));
                    gameObjects.add(gameObjects.size(), objectContainer);
                }else if(lvl[x + y * lvlSize] == 5) {
                    Eagle eagle = new Eagle(false);
                    eagle.setDestroyable();
                    ObjectContainer objectContainer =  new ObjectContainer(x, y, eagle);
                    gameObjects.add(gameObjects.size(), objectContainer);
                }
            }
        }
    }

    public void movePlayer(MoveControl.Move move) {
        ObjectContainer playerTankContainer = tanks.get(PLAYER_SLOT);
        if(canMove(playerTankContainer, move)) {
            playerTankContainer.vector = move.getPosition();
            playerTankContainer.x += move.getPosition().getX() * ((Player)playerTankContainer.object).getSpeed();
            playerTankContainer.y += move.getPosition().getY() * ((Player)playerTankContainer.object).getSpeed();
        }
    }

    public void moveEnemy() {
        for (int i = 0; i < tanks.size(); i++) {
            ObjectContainer enemyContainer = tanks.get(i);
            if (enemyContainer.object instanceof Enemy){
                if(canMove(enemyContainer, enemyContainer.vector)){
                    enemyContainer.x += enemyContainer.vector.getX() * ((Enemy)enemyContainer.object).getSpeed();
                    enemyContainer.y += enemyContainer.vector.getY() * ((Enemy)enemyContainer.object).getSpeed();
                }else {
                    logic.findWay(enemyContainer);
                }
            }
        }

    }

    private boolean canMove(ObjectContainer objectContainer, MoveControl.Move move) {
        if (collision.clash(objectContainer, gameObjects, move.getPosition())){
            return false;
        }else {
            return true;
        }
    }
    private boolean canMove(ObjectContainer objectContainer, Vector2D vector2D) {
        if (collision.clash(objectContainer, gameObjects, vector2D)){
            return false;
        }else {
            return true;
        }
    }

    public void addTanks() {
        for (int i = 1; i < 10; i+=3) {
            ObjectContainer objectContainer =  new ObjectContainer((i + 2)* 48, 0, new Enemy(2, Tank.Type.LIGHT));
            int enemySlot = tanks.size();
            objectContainer.vector = MoveControl.Move.BACK.getPosition();
            if(enemySlot < 4)
                tanks.add(enemySlot, objectContainer);
        }
    }

    public void addPlayer() {
        ObjectContainer objectContainer =  new ObjectContainer(3 * 48, 9 * 48, new Player(2, Tank.Type.LIGHT));
        PLAYER_SLOT = tanks.size();
        objectContainer.vector = MoveControl.Move.FORWARD.getPosition();
        tanks.add(PLAYER_SLOT, objectContainer);
    }

    public void addBullet(ObjectContainer container) {
        Bullet bullet;
        if(container.object instanceof Player) {
            bullet = new Bullet(false, true, 10);
            bullet.setSpeed(10);
        }else {
            bullet = new Bullet(true, false, 10);
            bullet.setSpeed(4);
        }
        ObjectContainer bulletContainer = new ObjectContainer(container.x + 20, container.y + 20, bullet, container.vector);
        bulletContainer.addOwner(((Tank)container.object));
        int bulletSlot = playerBullets.size();
        playerBullets.add(bulletSlot, bulletContainer);
    }

    public void playerShot() {
        ObjectContainer playerContainer = tanks.get(PLAYER_SLOT);
        if(!isHaveBullet(playerContainer))
            addBullet(playerContainer);

    }

    public void shot() {
        for (int slot = 0; slot < tanks.size(); slot++) {
            ObjectContainer tank = tanks.get(slot);
            if(tank.object instanceof Enemy) {
                if (!isHaveBullet(tank)) {
                    addBullet(tank);
                }
            }
        }
    }

    private boolean isHaveBullet(ObjectContainer container) {
        for (ObjectContainer bullet : playerBullets) {
            if (bullet.tank == container.object) {
                return true;
            }
        }
        return false;
    }

    public void moveBullets() {
        for (int i = 0; i < playerBullets.size(); i++) {
            ObjectContainer bullet = playerBullets.get(i);
            if(!collision.clash(bullet, gameObjects, bullet.vector) & !collision.clash(bullet, tanks, bullet.vector)) {
                bullet.x += bullet.vector.getX() * ((Bullet) bullet.object).getSpeed();
                bullet.y += bullet.vector.getY() * ((Bullet) bullet.object).getSpeed();
            }else{
                playerBullets.remove(i);
            }
        }
    }

    public boolean isEagleAlive(){
        for (int slot = 0; slot < gameObjects.size(); slot++) {
            ObjectContainer gameObject = gameObjects.get(slot);
            if(gameObject.object instanceof Eagle){
                return true;
            }
        }
        return false;
    }

    public List<ObjectContainer> getTanks() {
        return tanks;
    }

    public List<ObjectContainer> getPlayerBullets() {
        return playerBullets;
    }

    public void setPlayerOnRespawn() {
        ObjectContainer playerContainer = tanks.get(PLAYER_SLOT);
        playerContainer.x = 3 * 48;
        playerContainer.y = 9 * 48;
    }

    public void playerDied() {
        dies.add("die");
    }

    public List getDies() {
        return dies;
    }

    public void showStatistics() {
        System.out.println("Player dies " + dies.size() + " times");
    }

    public void clearField() {
        this.gameObjects = new ArrayList<ObjectContainer>();
        this.tanks = new ArrayList<ObjectContainer>();
        this.playerBullets = new ArrayList<ObjectContainer>();
        this.kills = new ArrayList();
        this.dies = new ArrayList();
    }

    public void playerKillEnemey() {
        kills.add("kill");
    }

    public List getKills() {
        return kills;
    }

    public void checkNumOfTanks() {
        if(kills.size() < 18){
            addTanks();
        }
    }

    public class ObjectContainer {
        public int x;
        public int y;
        public Object object;
        public Vector2D vector;
        public Tank tank;

        public ObjectContainer(int x, int y, Object object) {
            this.x = x;
            this.y = y;
            this.object = object;
        }

        public ObjectContainer(int x, int y, Object object, Vector2D vector) {
            this.x = x;
            this.y = y;
            this.object = object;
            this.vector = vector;
        }

        public void addOwner(Tank tank) {
            this.tank = tank;
        }
    }

    public class EnemyCoords {
        private int x;
        private int y;
        private List<EnemyCoords> coordsList = new ArrayList<EnemyCoords>();
        EnemyCoords enemyCoords;
        private EnemyCoords() {
            for (int i = 1; i < 10; i += 3) {
                x = (i + 2) * 48;
                y = 0;
                enemyCoords = new EnemyCoords();
                enemyCoords.x = x;
                enemyCoords.y = y;
                coordsList.add(enemyCoords);
            }
        }

        public List<EnemyCoords> getCoordsList() {
            return coordsList;
        }
    }

}
