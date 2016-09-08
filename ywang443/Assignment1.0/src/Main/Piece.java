package Main;

/**
 * Created by yutong on 9/4/16.
 */
public abstract class Piece{

    //whether the piece is owned by this player
    private boolean owner;
    public Board board;
    abstract public boolean validMove(int i, int j);
    protected Location location = new Location();

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


}
