package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class Bishop extends Piece{

    public Bishop(int i, int j, boolean owner){
        location.setLocation(i, j);
        setOwner(owner);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, location.getRow(),location.getCol())){
            return false;
        }

        if (location.getCol() + j <= 7 && location.getRow() + i <= 7){
            // moving like a rook
            if (location.getCol() == j || location.getRow() == i || ((location.getRow() - i) != (location.getCol() - j))){
                return false;
            }
            return !routeBlockedBishop(i, j, location.getRow(), location.getCol());
        }
        return false;
    }
}
