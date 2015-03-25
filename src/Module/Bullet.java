package Module;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Bullet {
    private boolean hitPlayer;
    private boolean hitEnemy;
    private int damage;
    private double speed;
    public Bullet(boolean hitPlayer, boolean hitEnemy, int damage) {
        this.hitPlayer = hitPlayer;
        this.hitEnemy = hitEnemy;
        this.damage = damage;
    }

    public boolean isCanHitPlayer() {
        return hitPlayer;
    }

    public boolean isCanHitEnemy() {
        return hitEnemy;
    }

    public int getDamage() {
        return damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }
}
