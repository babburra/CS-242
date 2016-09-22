package Pieces;

import java.util.Map;

/**
 * Created by yutong on 9/7/16.
 */
public class Board{
    private Piece [][] chessboard;
    private Piece removedPiece;
    private Location oldlocation;
    private Piece lastPiece;

    public enum State{
        normal, checked, checkmate, stalemate
    }

    private State state = State.normal;

    public void setState(State s){
        state = s;
    }

    public State getState(){
        return state;
    }

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
    public boolean canMove(int i, int j, Object O, boolean owner){
        if (O instanceof Pawn){
            Pawn pawn = (Pawn) O;
            return pawn.validMove(i, j, owner);
        }
        if (O instanceof Bishop){
            Bishop bishop = (Bishop) O;
            return bishop.validMove(i, j, owner);
        }
        if (O instanceof Knight){
            Knight knight = (Knight) O;
            return knight.validMove(i, j, owner);
        }
        if (O instanceof Rook){
            Rook rook = (Rook) O;
            return rook.validMove(i, j, owner);
        }
        if (O instanceof Queen){
            Queen queen = (Queen) O;
            return queen.validMove(i, j, owner);
        }
        if (O instanceof King){
            King king = (King) O;
            return king.validMove(i, j, owner);
        }
        if (O instanceof SpecialBishop){
            SpecialBishop sp = (SpecialBishop) O;
            return sp.validMove(i, j, owner);
        }
        if (O instanceof SpecialRook){
            SpecialRook sp = (SpecialRook) O;
            return sp.validMove(i, j, owner);
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
     * @return return true if piece p is moved, false o/w
     */
    public boolean movePiece(Piece p, Location location, boolean owner){
        // can not kill a king
        if (getPiece(location.getRow(), location.getCol()) instanceof King)
            return false;
        if (canMove(location.getRow(), location.getCol(), p, owner)){
            removedPiece = getPiece(location.getRow(), location.getCol());
            lastPiece = p;
            oldlocation = new Location(p.getLocation().getRow(), p.getLocation().getCol());
            removePiece(p);
            removePiece(getPiece(location.getRow(), location.getCol()));
            addPiece(p, location);
            if (p instanceof Pawn)
                ((Pawn) p).setFirst(false);
            p.setLocation(location);
            // move piece can result check
            if (isChecked(getKing(owner)) != null){
                state = State.checked;
                undo();
                //System.out.println("checked");
                return false;
            }
            if (isCheckMate(getKing(!owner), !owner))
                state = State.checkmate;
            else if (isStaleMate(!owner))
                state = State.stalemate;
            else
                state = State.normal;
            return true;
        }
        System.out.println("Illegal Move");
        return false;
    }

    /**
     * undo a move
     *
     */
    public void undo() {
        removePiece(lastPiece);
        addPiece(lastPiece, oldlocation);
        lastPiece.setLocation(oldlocation);
        if (lastPiece instanceof Pawn)
            ((Pawn) lastPiece).setFirst(true);
        //System.out.println(p.getLocation().getRow() + "," + p.getLocation().getCol());
        if (removedPiece != null)
            addPiece(removedPiece, removedPiece.getLocation());
    }

    /**
     *
     * @param owner ownership of a piece
     * @return the king of one player
     */
    public Piece getKing(boolean owner) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p instanceof King && p.getOwner() == owner) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param owner false for black, true for white
     * @return return true if the king has no legal move, false o/w
     */
    public boolean noMoves(boolean owner){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = getPiece(i, j);
                if (p != null && p instanceof King)
                    continue;
                for (int k = 0; k < 8; k++){
                    for (int h = 0; h < 8; h++){
                        if (canMove(h, k, p, owner))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param p king
     * @return return true if the king is checked, false o/w
     */
    public Piece isChecked(Piece p){
        if (!(p instanceof King))
            return null;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = getPiece(i, j);
                if (piece != null && canMove(p.getLocation().getRow(), p.getLocation().getCol(), piece, piece.getOwner())){
                    if (p.getOwner() != piece.getOwner()) {
                        state = State.checked;
                        return piece;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param p king
     * @param owner
     * @return return true if the game is in checkmate, false o/w
     */
    public boolean isCheckMate(Piece p, boolean owner){
        Piece atk = isChecked(p);
        if (atk == null)
            // no attacker
            return false;
        if (!kingNoMove(getKing(owner)))
            return false;
        int atk_x = atk.getLocation().getRow();
        int atk_y = atk.getLocation().getCol();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = getPiece(i, j);
                if (piece != null && getPiece(i, j).getOwner() == p.getOwner()) {
                    if (movePiece(piece,atk.getLocation(), owner)) {
                        // can kill atk
                        undo();
                        //System.out.print("can kill");
                        return false;
                    }
                    // try to block the atk's path
                    Location []path = getPath(p.getLocation().getRow(), p.getLocation().getCol(), atk_x, atk_y);
                    for (int count = 0; count < path.length; count++){
                        if (path[count] != null && movePiece(piece, path[count], owner) && !(piece instanceof King)) {
                            //System.out.print("can block");
                            undo();
                            return false;
                        }
                    }
                }
            }
        }
        //if (noMoves(owner))
        // can't kill atk and can't block the path
        state = State.checkmate;
        return true;
    }

    /**
     *
     * @param king
     * @return return true if the king has no move, false o/w
     */
    private boolean kingNoMove(Piece king) {
        int cur_x = king.getLocation().getRow();
        int cur_y = king.getLocation().getCol();
        for (int i = cur_x - 1; i <= cur_x + 1; i++){
            for (int j = cur_y - 1; j <= cur_y + 1; j++){
                //System.out.print("cant move ");
                if (movePiece(king, new Location(i, j), king.getOwner())) {
                    undo();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @return return true if the game is stalemate, false o/w
     */
    public boolean isStaleMate(boolean owner){
        boolean kingsLeft = true;
        // only two kings left
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = getPiece(i, j);
                if (p == null)
                    continue;
                if (!(p instanceof King) && p.getOwner() == owner) {
                    System.out.println(p);
                    kingsLeft = false;
                }
                if (!noMoves(owner)){
                    return false;
                }
            }
        }
        state = State.stalemate;
        return kingsLeft;
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