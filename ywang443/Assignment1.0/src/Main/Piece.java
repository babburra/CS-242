package Main;

/**
 * Created by yutong on 9/4/16.
 */
public abstract class Piece{

    //whether the piece is owned by this player
    private boolean owner;
    private Location location;
    private Board board;
    public Piece(Board board, Location location, boolean owner){
        this.board = board;
        this.location = location;
        this.owner = owner;
    }
    abstract public boolean validMove(int i, int j);
    //protected Location location = new Location();

    public Board getBoard(){
        return board;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getOwner(){
        return owner;
    }

    /*protected void setOwner(boolean own){
        this.owner = own;
    }*/

    public void setLocation(Location location){
        this.location = location;
    }

    public boolean routeBlockedBishop(int desti, int destj, int locx, int locy){
        if (desti < locx){
            if (destj < locy){ //3rd quadrant
                for (; desti < locx; desti++) {
                    Piece p = this.getBoard().getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ // 2nd quadrant
                for (; desti < locx; desti++) {
                    Piece p = this.getBoard().getPiece(desti, destj--);
                    if (p != null) {
                        return true;
                    }
                }
            }
        }
        if (desti > locx){
            if (destj < locy){ // 4th quadrant
                for (; desti > locx; desti--) {
                    Piece p = this.getBoard().getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ //1st quadrant
                for (; desti > locx; desti--) {
                    Piece p = this.getBoard().getPiece(desti, destj--);
                    if (p != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean routeBlockedRook(int desti, int destj, int locx, int locy){
        if (desti < locx){ //moving backwards
            for ( ; desti < locx; desti++){
                Piece p = getBoard().getPiece(desti,locy);
                if (p != null){
                    return true;
                }
            }
        }
        if (desti > locx){ //moving forward
            for ( ; locx < desti; desti--){
                Piece p = getBoard().getPiece(desti,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj < locy){ //moving left
            for ( ; destj < locy; destj++){
                Piece p = getBoard().getPiece(locx,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj > locy){ //moving right
            for ( ; locy < destj; destj--){
                Piece p = getBoard().getPiece(desti,destj);
                if (p != null){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Piece other){
        if (other == null){
            return false;
        }
        return (this.getLocation().getRow() == other.getLocation().getRow()) && (this.getLocation().getCol() == other.getLocation().getCol()) && (this.getOwner() == other.getOwner());
    }

    //check if the piece is moving out of chessboard
    public boolean outOfBoundary(int i, int j){
        if(i < 0 || i > 7 || j < 0 || j > 7){
            return true;
        }
        return false;
    }

    public boolean didntmove(int desti, int destj, int loci, int locj){
        return desti == loci && destj == locj;
    }


}
