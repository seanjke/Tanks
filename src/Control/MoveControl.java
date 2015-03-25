package Control;

import Module.Vector2D;

/**
 * Created by Hank on 30.11.2014.
 */
public class MoveControl {
    public static enum Move {
        FORWARD(0, -1, -90), LEFT(-1, 0, 180), RIGHT(1, 0, 0), BACK(0, 1, 90);

        private Vector2D pos;

        Move(int x, int y, double angle) {
            this.pos = new Vector2D(x, y, angle);
        }
        public Vector2D getPosition(){
            return pos;
        }
    }
}
