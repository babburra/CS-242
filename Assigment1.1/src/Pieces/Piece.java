package Pieces;

/**
 * Created by yutong on 9/4/16.
 */
public abstract class Piece{

    //whether the piece is owned by this player
    private boolean owner;
    private Location location;
    private Board board;
    /**
     * constructor for piece
     * @param board game board
     * @param location piece location
     * @param owner piece owner
     */
    public Piece(Board board, Location location, boolean owner){
        this.board = board;
        this.location = location;
        this.owner = owner;
    }
    public abstract boolean validMove(int des_x, int des_y);

    public Board getBoard(){
        return board;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getOwner(){
        return owner;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    /**
     *
     * @param cur_x coordinate x of starting tile
     * @param cur_y coordinate y of starting tile
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return Check path diagonally, True if the route is blocked, False o/w
     */
    public boolean checkDiagonally(int cur_x, int cur_y, int des_x, int des_y){
        int dir_x = des_x > cur_x ? 1 : -1;
        int dir_y = des_y > cur_y ? 1 : -1;
        for (int i = 1; i < Math.abs(des_x - cur_x) + 1; i++) {
            if (getBoard().getPiece(cur_x + i * dir_x, cur_y + i * dir_y) != null){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param cur_x coordinate x of starting tile
     * @param cur_y coordinate y of starting tile
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return Check path horizontally and vertically, True if the route is blocked, False o/w
     */

    public boolean checkInLine(int cur_x, int cur_y, int des_x, int des_y){
        int dir_x = 0;
        int dir_y = 0;
        if (des_x == cur_x)
            dir_y = des_y > cur_y ? 1 : -1;
        if (des_y == cur_y)
            dir_x = des_x > cur_x ? 1 : -1;
        int dist = Math.abs(des_x - cur_x) > Math.abs(des_y - cur_y) ? Math.abs(des_x - cur_x) : Math.abs(des_y - cur_y);

        for (int i = 1; i < dist + 1; i++) {
            if (getBoard().getPiece(cur_x + i * dir_x, cur_y + i * dir_y) != null){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param other the other chess piece need to be compared
     * @return Return true if the two pieces are the same piece, false o/w
     */
    public boolean equals(Piece other){
        if (other == null){
            return false;
        }
        return (this.getLocation().getRow() == other.getLocation().getRow()) && (this.getLocation().getCol() == other.getLocation().getCol()) && (this.getOwner() == other.getOwner());
    }

    /**
     *
     * @param des_x coordinate x of destination tile
     * @param des_y coordinate y of destination tile
     * @return true if destination tile is not on the board, false o/w
     */
    public boolean outOfBoundary(int des_x, int des_y){
        if(des_x < 0 || des_x > 7 || des_y < 0 || des_y > 7){
            return true;
        }
        return false;
    }

    /**
     *
     * @return true if the chess piece didn't move, false o/w
     */
    public boolean didntmove(int cur_x, int cur_y, int des_x, int des_y){
        return des_x == cur_x && des_y == cur_y;
    }
}
