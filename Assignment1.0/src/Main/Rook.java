package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class Rook extends Piece{

    public Rook(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, this.getLocation().getRow(), this.getLocation().getCol())){
            return false;
        }

        if ((this.getLocation().getCol() != j && this.getLocation().getRow() != i)){
            return false;
        }
        return !routeBlockedRook(i, j, this.getLocation().getRow(), this.getLocation().getCol());
    }
}
