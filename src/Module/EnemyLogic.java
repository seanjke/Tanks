package Module;

import Control.MoveControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Администратор on 13.01.2015.
 */
public class EnemyLogic {

    Random random = new Random();

    public void findWay(Board.ObjectContainer enemyContainer) {
        List<MoveControl.Move> moves = new ArrayList<MoveControl.Move>();
        moves.add(MoveControl.Move.LEFT);
        moves.add(MoveControl.Move.FORWARD);
        moves.add(MoveControl.Move.RIGHT);
        moves.add(MoveControl.Move.BACK);
        enemyContainer.vector = moves.get(random.nextInt(4)).getPosition();
    }

}
