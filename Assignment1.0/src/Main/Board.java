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
            board[1][i] = new Pawn(1, i, true);
            board[6][i] = new Pawn(6, i, false);
        }
        // player
        board[0][0] = new Rook(0, 0, true);
        board[0][7] = new Rook(0, 7, true);
        board[0][1] = new Knight(0, 1, true);
        board[0][6] = new Knight(0, 6, true);
        board[0][2] = new Bishop(0, 2, true);
        board[0][5] = new Bishop(0, 5, true);
        board[0][3] = new Queen(0, 3, true);
        board[0][4] = new King(0, 4, true);
        // opponent
        board[7][0] = new Rook(7, 0, false);
        board[7][7] = new Rook(7, 7, false);
        board[7][1] = new Knight(7, 1, false);
        board[7][6] = new Knight(7, 6, false);
        board[7][2] = new Bishop(7, 2, false);
        board[7][5] = new Bishop(7, 5, false);
        board[7][3] = new Queen(7, 3, false);
        board[7][4] = new King(7, 4, false);
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

    //check to see if there's a piece is blocking the move
    public boolean routeBlockedBishop(int desti, int destj, int locx, int locy){
        if (desti < locx){
            if (destj < locy){ //3rd quadrant
                for (; desti < locx; desti++) {
                    Piece p = getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ // 2nd quadrant
                for (; desti < locx; desti++) {
                    Piece p = getPiece(desti, destj--);
                    if (p != null) {
                        return true;
                    }
                }
            }
        }
        if (desti > locx){
            if (destj < locy){ // 4th quadrant
                for (; desti > locx; desti--) {
                    Piece p = getPiece(desti, destj++);
                    if (p != null) {
                        return true;
                    }
                }
            }
            if (destj > locy){ //1st quadrant
                for (; desti > locx; desti--) {
                    Piece p = getPiece(desti, destj--);
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
                Piece p = getPiece(desti,locy);
                if (p != null){
                    return true;
                }
            }
        }
        if (desti > locx){ //moving forward
            for ( ; locx < desti; locx++){
                Piece p = getPiece(locx,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj < locy){ //moving left
            for ( ; destj < locy; destj++){
                Piece p = getPiece(locx,destj);
                if (p != null){
                    return true;
                }
            }
        }
        if (destj > locy){ //moving right
            for ( ; locy < destj; locy++){
                Piece p = getPiece(desti,locy);
                if (p != null){
                    return true;
                }
            }
        }
        return false;
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