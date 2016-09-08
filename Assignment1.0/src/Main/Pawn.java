package Main;

/**
 * Created by yutong on 9/4/16.
 */
public class Pawn extends Piece{
    // can move up to 2 squares
    private boolean first;
    public boolean isFirst(){
        return first;
    }
    private void setFirst(boolean b){
        first = b;
    }

    public Pawn(int i, int j, boolean owner){
        location.setLocation(i, j);
        setOwner(owner);
        setFirst(true);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, location.getRow(),location.getCol())){
            return false;
        }
        //player
        if (location.getCol() + 1 == j && location.getRow() == i){
            if (getOwner()){
                return true;
            }
            return false;
        }
        //opponent
        if (location.getCol() - 1 == j && location.getRow() == i){
            if (!getOwner()){
                return true;
            }
            return false;
        }
        return false;
    }

}
