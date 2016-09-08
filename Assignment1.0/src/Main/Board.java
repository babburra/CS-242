package Main;
import java.security.acl.Owner;
import java.util.Objects;
import java.util.Queue;

/**
 * Created by yutong on 9/7/16.
 */
public class Board{
    private Piece [][] board;
    public Board(){
        // board size 8
        board = new Piece [8][8];
        initBoard();
        for(int i=0; i<8;i++){
            board[1][i] = new Pawn(i,1,true);
            board[6][i] = new Pawn(i,6,false);
        }
        // player
        board[0][0] = new Rook(0,0,true);
        board[0][7] = new Rook(7,0,true);
        board[0][1] = new Knight(1,0,true);
        board[0][6] = new Knight(6,0,true);
        board[0][2] = new Bishop(2,0,true);
        board[0][5] = new Bishop(5,0,true);
        board[0][3] = new Queen(3,0,true);
        board[0][4] = new King(4,0,true);
        // opponent
        board[7][0] = new Rook(0,7,false);
        board[7][7] = new Rook(7,7,false);
        board[7][1] = new Knight(1,7,false);
        board[7][6] = new Knight(6,7,false);
        board[7][2] = new Bishop(2,7,false);
        board[7][5] = new Bishop(5,7,false);
        board[7][3] = new Queen(3,7,false);
        board[7][4] = new King(4,7,false);
    }

    // initialize the board with all location set to null
    public void initBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = null;
            }
        }
    }
    //return a chess piece on the board, or null
    public Piece getPiece(int i, int j){
        if (i < 0 || i > 7 || j < 0 ||j > 7){
            return null;
        }
        return board[i][j];
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
        board[location.getRow()][location.getCol()] = p;
    }

    public void removePiece(Piece p){
        if (p != null) {
            board[p.getLocation().getRow()][p.getLocation().getCol()] = null;
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

    private Piece findingKing(Owner owner) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[board.length].length; j++) {
                Piece p = getPiece(i, j);
                if (p instanceof King && p.getOwner() == true) {
                    return p;
                }
            }
        }
        return null;
    }
}