package Module;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Tank {
    private double speed;
    private int ammo = 1;
    public Type type;

    public enum Type{
        LIGHT(5, 10), MIDLE(10, 10), HEAVY(15, 10);

        private Type(int armor, int health){
            this.armor  = armor;
            this.health = health;
        }
        private final int armor;
        private final int health;

        public int getHealth() {
            return this.health;
        }
        public int getArmor() {
            return this.armor;
        }
    }

    public Tank(double speed, Type type) {
        this.speed  = speed;
        this.type   = type;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return this.ammo;
    }

}
