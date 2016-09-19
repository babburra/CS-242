package Pieces;

/**
 * Created by yutong on 9/4/16.
 */
public class Pawn extends Piece{
    // can move up to 2 squares
    private boolean first;

    /**
     *
     * @return true if it's the first move of a pawn, false o/w
     */
    public boolean isFirst(){
        return first;
    }

    private void setFirst(boolean b){
        first = b;
    }

    /**
     * constructor for pawn
     * @param board game board
     * @param location piece location
     * @param owner piece owner
     */
    public Pawn(Board board, Location location, boolean owner){
        super(board, location, owner);
        setFirst(true);
    }

    /**
     *
     * @param des_x coord x of destination tile
     * @param des_y coord y of destination tile
     * @return true if it's a valid pawn move, false o/w
     */
    public boolean validMove(int des_x, int des_y){
        int cur_x = this.getLocation().getRow();
        int cur_y = this.getLocation().getCol();
        //int dir = getOwner() ? 1 : -1;
        // if target location OFB or didn't move, return false
        if (outOfBoundary(des_x, des_y) || didntmove(cur_x, cur_y, des_x, des_y)){
            return false;
        }
        //player moving 1 tile or 2 tiles when it's the first move
        if ((cur_y == des_y && cur_x + 1 == des_x) || (cur_y == des_y && cur_x + 2 == des_x && isFirst())) {
            if (getOwner()) {
                return true;
            }
            return false;
        }
        if (cur_y + 1 == des_y && Math.abs(cur_x - des_x) == 1 && !getBoard().getPiece(des_x, des_y).getOwner()){
            return true;
        }
        //opponent moving 1 tile or 2 tiles when it's the first move
        if ((cur_y == des_y && cur_x - 1 == des_x) || (cur_y == des_y && cur_x - 2 == des_x && isFirst())){
            if (!getOwner()){
                return true;
            }
            return false;
        }
        return false;
    }
}
