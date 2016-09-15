package Pieces;

/**
 * Created by yutong on 9/7/16.
 */
public class Board{
    private Piece [][] chessboard;
    public Board(){
        chessboard = new Piece [8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                chessboard[i][j] = null;
            }
        }
    }

    public Piece[][] getBoard(){
        return chessboard;
    }
    //return a chess piece on the board, or null
    public Piece getPiece(int i, int j){
        if (i < 0 || i > 7 || j < 0 || j > 7){
            return null;
        }
        return chessboard[i][j];
    }

    /**
     * move a piece to location (i,j)
     * @param i coordinate x of destination tile
     * @param j coordinate y of destination tile
     * @param O an arbitrary piece
     * @return return true if it's a valid move, o/w return false
     */
    public boolean canMove(int i, int j, Object O){
        if (O instanceof Pawn){
            Pawn pawn = (Pawn) O;
            return pawn.validMove(i, j);
        }
        if (O instanceof Bishop){
            Bishop bishop = (Bishop) O;
            return bishop.validMove(i, j);
        }
        if (O instanceof Knight){
            Knight knight = (Knight) O;
            return knight.validMove(i, j);
        }
        if (O instanceof Rook){
            Rook rook = (Rook) O;
            return rook.validMove(i, j);
        }
        if (O instanceof Queen){
            Queen queen = (Queen) O;
            return queen.validMove(i, j);
        }
        if (O instanceof King){
            King king = (King) O;
            return king.validMove(i, j);
        }
        return false;
    }

    /**
     * add a piece to a location
     * @param p target piece p
     * @param location destination location
     */
    public void addPiece(Piece p, Location location){
        chessboard[location.getRow()][location.getCol()] = p;
    }

    /**
     * initialize a piece on the board
     * @param p target piece p
     */
    public void initPiece(Piece p){
        chessboard[p.getLocation().getRow()][p.getLocation().getCol()] = p;
    }

    /**
     * remove a piece from the board
     * @param p target piece p
     */
    public void removePiece(Piece p){
        if (p != null) {
            chessboard[p.getLocation().getRow()][p.getLocation().getCol()] = null;
        }
    }

    /**
     * move a piece to the designated location
     * @param p piece p
     * @param location destination location
     */
    public void movePiece(Piece p, Location location){
        if (canMove(location.getRow(), location.getCol(), p)){
            removePiece(p);
            removePiece(getPiece(location.getRow(), location.getCol()));
            addPiece(p, location);
            p.setLocation(location);
        }
    }

    /**
     *
     * @param cur_x piece that is currently under attack, coord x
     * @param cur_y piece that is currently under attack, coord y
     * @param des_x attacker, coord x
     * @param des_y attacker, coord y
     * @return return an array of locations that forms the path from attacker to defender,
     *          including attackers location since attackers might be killed by other pieces.
     *          return null if it's an invalid path.
     */
    public Location[] getPath(int cur_x, int cur_y, int des_x, int des_y){
        // if the path is horizontal/vertical
        Location[] paths = new Location[8];
        if (cur_x == des_x || cur_y == des_y){
            int dir_x = 0;
            int dir_y = 0;
            if (des_x == cur_x)
                dir_y = des_y > cur_y ? 1 : -1;
            if (des_y == cur_y)
                dir_x = des_x > cur_x ? 1 : -1;

            int dist = Math.abs(des_x - cur_x) > Math.abs(des_y - cur_y) ? Math.abs(des_x - cur_x) : Math.abs(des_y - cur_y);

            for (int i = 1; i < dist + 1; i++) {
                paths[i-1] = new Location(cur_x + i * dir_x, cur_y + i * dir_y);
            }
        }
        // if the path is diagonal
        else if (Math.abs(cur_x - des_x) == Math.abs(cur_y - des_y)) {
            int dir_x = des_x > cur_x ? 1 : -1;
            int dir_y = des_y > cur_y ? 1 : -1;
            for (int i = 1; i < Math.abs(des_x - cur_x) + 1; i++) {
                paths[i-1] = new Location(cur_x + i * dir_x, cur_y + i * dir_y);
            }
        }
        // knight path or invalid path -- no need to check
        else {
            for (int i = 0; i < 8; i++){
                paths[i] = null;
            }
        }
        return paths;

    }


}