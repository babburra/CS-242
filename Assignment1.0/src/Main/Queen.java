package Main;

import javafx.scene.input.PickResult;

/**
 * Created by yutong on 9/7/16.
 */
public class Queen extends Piece{

    public Queen(int i, int j, boolean owner){
        location.setLocation(i, j);
        setOwner(owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, location.getRow(),location.getCol())){
            return false;
        }
        if ((location.getRow() - i) == (location.getCol() - j)){
            return !board.routeBlockedBishop(i, j, location.getRow(), location.getCol());
        }
        if (location.getCol() == j || location.getRow() == i){
            return !board.routeBlockedRook(i, j, location.getRow(), location.getCol());
        }
        return false;
    }
}
