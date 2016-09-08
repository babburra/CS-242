package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class Knight extends Piece{

    public Knight(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, this.getLocation().getRow(),this.getLocation().getCol())){
            return false;
        }
        if (j <= 7 && i <= 7){
            int diffi = Math.abs(this.getLocation().getRow() - i);
            int diffj = Math.abs(this.getLocation().getCol() - j);
            return ((diffi == 2 && diffj == 1) || (diffi == 1 && diffj == 2));
        }
        return false;
    }
}