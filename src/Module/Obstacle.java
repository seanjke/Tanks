package Module;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Obstacle {
    public boolean transparent;
    private boolean destroyable;

    public Obstacle(boolean transparent) {
        this.transparent = transparent;
    }

    public boolean isDestroyable() {
        return this.destroyable;
    }

    public void setDestroyable() {
        this.destroyable = true;
    }
}
