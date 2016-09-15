package Pieces;

/**
 * Created by yutong on 9/7/16.
 */
public class Knight extends Piece{

    /**
     * constructor for knight
     * @param board game board
     * @param location piece location
     * @param owner piece owner
     */
    public Knight(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    /**
     *
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return return true if it's a valid knight move and no piece is blocking it, false o/w
     */
    public boolean validMove(int des_x, int des_y){
        int cur_x = this.getLocation().getRow();
        int cur_y = this.getLocation().getCol();
        // if target location OFB or didn't move, return false
        if (outOfBoundary(des_x, des_y) || didntmove(cur_x, cur_y, des_x, des_y)){
            return false;
        }
        if (des_y <= 7 && des_x <= 7){
            int diffx = Math.abs(cur_x - des_x);
            int diffy = Math.abs(cur_y - des_y);
            return ((diffx == 2 && diffy == 1) || (diffx == 1 && diffy == 2));
        }
        return false;
    }
}