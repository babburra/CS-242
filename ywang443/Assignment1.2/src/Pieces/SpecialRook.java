package Pieces;

/**
 * Created by yutong on 9/14/16.
 */
public class SpecialRook extends Piece{
    /**
     * constructor for special rook
     * @param board game board
     * @param location piece location
     * @param owner piece owner
     */
    public SpecialRook(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    /**
     *
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return moves like a rook except no piece can block it
     */
    public boolean validMove(int des_x, int des_y){
        int cur_x = this.getLocation().getRow();
        int cur_y = this.getLocation().getCol();
        // if target location OFB or didn't move, return false
        if (outOfBoundary(des_x, des_y) || didntmove(cur_x, cur_y, des_x, des_y)){
            return false;
        }

        if ((cur_y != des_y && cur_x != des_x)){
            return false;
        }
        return true;
    }
}
