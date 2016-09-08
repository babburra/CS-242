package Main;
import java.security.acl.Owner;

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

    // move a piece, return true if it's a valid move, o/w return false
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

    public void addPiece(Piece p, Location location){
        chessboard[location.getRow()][location.getCol()] = p;
    }

    public void initPiece(Piece p){
        chessboard[p.getLocation().getRow()][p.getLocation().getCol()] = p;
    }

    public void removePiece(Piece p){
        if (p != null) {
            chessboard[p.getLocation().getRow()][p.getLocation().getCol()] = null;
        }
    }

    public void movePiece(Piece p, Location location){
        if (canMove(location.getRow(), location.getCol(), p)){
            removePiece(p);
            removePiece(getPiece(location.getRow(), location.getCol()));
            addPiece(p, location);
            p.setLocation(location);
        }
    }




}