package Pieces;

/**
 * Created by yutong on 9/7/16.
 */
public class King extends Piece{

    /**
     * constructor for king
     * @param board game board
     * @param location piece location
     * @param owner piece owner
     */
    public King(Board board, Location location, boolean owner){
        super(board, location, owner);
    }

    /**
     *
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return return true if it's a valid king move and no piece is blocking it, false o/w
     */
    public boolean validMove(int des_x, int des_y){
        int cur_x = this.getLocation().getRow();
        int cur_y = this.getLocation().getCol();
        if (outOfBoundary(des_x, des_y) || didntmove(cur_x, cur_y, des_x, des_y)){
            return false;
        }
        int diffx = Math.abs(cur_x - des_x);
        int diffy = Math.abs(cur_y - des_y);
        return diffx < 2 && diffy < 2;
    }
}
