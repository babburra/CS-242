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

    public Pawn(Board board, Location location, boolean owner){
        super(board, location, owner);
        setFirst(true);
    }

    public boolean validMove(int i, int j){
        // if target location OFB or didn't move, return false
        if (outOfBoundary(i, j) || didntmove(i, j, this.getLocation().getRow(),this.getLocation().getCol())){
            return false;
        }
        //player
        if (this.getLocation().getCol() == j && this.getLocation().getRow() + 1 == i) {
            if (getOwner()) {
                return true;
            }
            return false;
        }
        if (this.getLocation().getCol() + 1 == j && Math.abs(this.getLocation().getRow() - i) == 1 && getBoard().getPiece(i, j).getOwner() == false){
            return true;
        }
        //opponent
        if (this.getLocation().getCol() == j && this.getLocation().getRow() -1 == i){
            if (!getOwner()){
                return true;
            }
            return false;
        }
        return false;
    }

}
