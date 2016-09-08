package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class King extends Piece{

    public King(int i, int j, boolean owner){
        location.setLocation(i, j);
        setOwner(owner);
    }

    public boolean validMove(int i, int j){
        if (outOfBoundary(i, j) || didntmove(i, j, location.getRow(),location.getCol())){
            return false;
        }
        int diffi = Math.abs(location.getRow() - i);
        int diffj = Math.abs(location.getCol() - j);
        return diffi < 2 && diffj < 2;
    }
}
