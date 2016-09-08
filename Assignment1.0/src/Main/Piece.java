package Main;

/**
 * Created by yutong on 9/4/16.
 */
public abstract class Piece{

    //whether the piece is owned by this player
    private boolean owner;
    abstract public boolean validMove(int i, int j);
    protected Location location = new Location();
    protected Board board = new Board();

    public boolean getOwner(){
        return owner;
    }

    protected void setOwner(boolean own){
        this.owner = own;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
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

    //check to see if there's a piece is blocking the move
    public boolean routeBlockedBishop(int desti, int destj, int locx, int locy){
        if (desti < locx){
            if (destj < locy){ //3rd quadrant
                for (; desti < locx; desti++) {
                    Piece p = board.getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ // 2nd quadrant
                for (; desti < locx; desti++) {
                    Piece p = board.getPiece(desti, destj--);
                    if (p != null) {
                        return true;
                    }
                }
            }
        }
        if (desti > locx){
            if (destj < locy){ // 4th quadrant
                for (; desti > locx; desti--) {
                    Piece p = board.getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ //1st quadrant
                for (; desti > locx; desti--) {
                    Piece p = board.getPiece(desti, destj--);
                    if (p != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //check to see if there's a piece is blocking the move
    public boolean routeBlockedRook(int desti, int destj, int locx, int locy){
        if (desti < locx){ //moving backwards
            for ( ; desti < locx; desti++){
                Piece p = board.getPiece(desti,locy);
                if (p != null){
                    return true;
                }
            }
        }
        if (desti > locx){ //moving forward
            for ( ; locx < desti; locx++){
                Piece p = board.getPiece(locx,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj < locy){ //moving left
            for ( ; destj < locy; destj++){
                Piece p = board.getPiece(locx,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj > locy){ //moving right
            for ( ; locy < destj; locy++){
                Piece p = board.getPiece(desti,locy);
                if (p != null){
                    return true;
                }
            }
        }
        return false;
    }

}
