package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class King extends Piece{

    public King(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    public boolean validMove(int i, int j){
        if (outOfBoundary(i, j) || didntmove(i, j, this.getLocation().getRow(),this.getLocation().getCol())){
            return false;
        }
        int diffi = Math.abs(this.getLocation().getRow() - i);
        int diffj = Math.abs(this.getLocation().getCol() - j);
        return diffi < 2 && diffj < 2;
    }
}
