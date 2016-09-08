package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class Knight extends Piece{

    public Knight(int i, int j, boolean owner){
        location.setLocation(i, j);
        setOwner(owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, location.getRow(),location.getCol())){
            return false;
        }
        if (location.getCol() + j <= 7 && location.getRow() + i <= 7){
            int diffi = Math.abs(location.getRow() - i);
            int diffj = Math.abs(location.getCol() - j);
            return ((diffi == 2 && diffj == 1) || (diffi == 1 && diffj == 2));
        }
        return false;
    }
}