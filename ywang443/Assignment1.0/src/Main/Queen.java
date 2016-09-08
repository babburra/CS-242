package Main;

import javafx.scene.input.PickResult;

/**
 * Created by yutong on 9/7/16.
 */
public class Queen extends Piece{

    public Queen(Board board, Location location, boolean owner){
        super(board, location ,owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, this.getLocation().getRow(), this.getLocation().getCol())){
            return false;
        }
        if ((this.getLocation().getRow() - i) == (this.getLocation().getCol() - j)){
            return !routeBlockedBishop(i, j, this.getLocation().getRow(), this.getLocation().getCol());
        }
        if (this.getLocation().getCol() == j || this.getLocation().getRow() == i){
            return !routeBlockedRook(i, j, this.getLocation().getRow(), this.getLocation().getCol());
        }
        return false;
    }
}
