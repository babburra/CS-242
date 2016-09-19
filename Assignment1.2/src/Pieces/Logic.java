package Pieces;

/**
 * Created by yutong on 9/8/16.
 */
public class Logic {
    private Board board = new Board();

    public Logic(){
        for(int i = 0; i < 8; i++){
            board.initPiece(new Pawn(board, new Location(1, i), true));
            board.initPiece(new Pawn(board, new Location(6, i), false));
        }
        //player
        board.initPiece(new Rook(board, new Location(0, 0), true));
        board.initPiece(new Rook(board, new Location(0, 7), true));
        board.initPiece(new Knight(board, new Location(0, 1), true));
        board.initPiece(new Knight(board, new Location(0, 6), true));
        board.initPiece(new Bishop(board, new Location(0, 2), true));
        board.initPiece(new Bishop(board, new Location(0, 5), true));
        board.initPiece(new Queen(board, new Location(0, 3), true));
        board.initPiece(new King(board, new Location(0, 4), true));

        //opponent
        board.initPiece(new Rook(board, new Location(7, 0), false));
        board.initPiece(new Rook(board, new Location(7, 7), false));
        board.initPiece(new Knight(board, new Location(7, 1), false));
        board.initPiece(new Knight(board, new Location(7, 6), false));
        board.initPiece(new Bishop(board, new Location(7, 2), false));
        board.initPiece(new Bishop(board, new Location(7, 5), false));
        board.initPiece(new Queen(board, new Location(7, 3), false));
        board.initPiece(new King(board, new Location(7, 4), false));
    }

    /**
     *
     * @param i coordinate x of destination tile
     * @param j coordinate y of destination tile
     * @return return the piece on the destination tile
     */
    public Piece getPiece(int i, int j){
        if (i < 0 || i > 7 || j < 0 || j > 7){
            return null;
        }
        return board.getPiece(i, j);
    }

    /**
     *
     * @param owner ownership of a piece
     * @return the king of one player
     */
    public Piece getKing(boolean owner) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Piece p = board.getPiece(i, j);
                if (p instanceof King && p.getOwner() == owner) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param p king
     * @return return the knight that's checking the king, null o/w
     */
    public Piece checkedByKnight(Piece p){
        int cur_x = p.getLocation().getRow();
        int cur_y = p.getLocation().getCol();
        boolean cur_owner = p.getOwner();
        Piece piece1 = board.getPiece(cur_x + 1,cur_y + 2);
        if (piece1 instanceof Knight && piece1.getOwner() != cur_owner)
            return piece1;
        Piece piece2 = board.getPiece(cur_x + 2,cur_y + 1);
        if (piece2 instanceof Knight && piece2.getOwner() != cur_owner)
            return piece2;
        Piece piece3 = board.getPiece(cur_x - 1,cur_y + 2);
        if (piece3 instanceof Knight && piece3.getOwner() != cur_owner)
            return piece3;
        Piece piece4 = board.getPiece(cur_x - 2,cur_y + 1);
        if (piece4 instanceof Knight && piece4.getOwner() != cur_owner)
            return piece4;
        Piece piece5 = board.getPiece(cur_x - 2,cur_y - 1);
        if (piece5 instanceof Knight && piece5.getOwner() != cur_owner)
            return piece5;
        Piece piece6 = board.getPiece(cur_x - 1,cur_y - 2);
        if (piece6 instanceof Knight && piece6.getOwner() != cur_owner)
            return piece6;
        Piece piece7 = board.getPiece(cur_x + 2,cur_y - 1);
        if (piece7 instanceof Knight && piece7.getOwner() != cur_owner)
            return piece7;
        Piece piece8 = board.getPiece(cur_x + 1,cur_y - 2);
        if (piece8 instanceof Knight && piece8.getOwner() != cur_owner)
            return piece8;
        return null;
    }

    /**
     *
     * @param p king
     * @return return the piece that's checking the king diagonally, null o/w
     */
    public Piece checkedDiagonal(Piece p){
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        boolean cur_owner = p.getOwner();
        int fixj = locj;
        for (int i = loci; i > 0; i--){
            Piece piece = board.getPiece(i, fixj + 1);
            Piece piece1 = board.getPiece(i, fixj - 1);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && piece.getOwner() != cur_owner){
                return piece;
            }
            if (piece1 != null && piece1 instanceof Bishop && piece1.getOwner() != cur_owner){
                return piece1;
            }
        }
        fixj = locj;
        for (int i = loci; i < 7; i++){
            Piece piece = board.getPiece(i, fixj + 1);
            Piece piece1 = board.getPiece(i, fixj - 1);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && piece.getOwner() != cur_owner){
                return piece;
            }
            if (piece1 != null && piece1 instanceof Bishop && piece1.getOwner() != cur_owner){
                return piece1;
            }
        }
        return null;
    }

    /**
     *
     * @param p king
     * @return return the piece that's checking the king vertically and horizontally, null o/w
     */
    public Piece checkedInLine(Piece p){
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        boolean cur_owner = p.getOwner();
        int fixi = loci;
        int fixj = locj;
        for (int i = loci; i > 0; i--){
            Piece piece = board.getPiece(i, fixj);
            Piece piece1 = board.getPiece(fixi++, fixj);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && piece.getOwner() != cur_owner){
                return piece;
            }
            if (piece1 != null && piece1 instanceof Bishop && piece1.getOwner() != cur_owner){
                return piece1;
            }
        }
        fixi = loci;
        fixj = locj;
        for (int j = locj; j < 0; j--){
            Piece piece = board.getPiece(fixi, j);
            Piece piece1 = board.getPiece(fixi, fixj++);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && piece.getOwner() != cur_owner){
                return piece;
            }
            if (piece1 != null && piece1 instanceof Bishop && piece1.getOwner() != cur_owner){
                return piece1;
            }
        }
        return null;
    }

    /**
     *
     * @param p king
     * @return return true if the king is checked, false o/w
     */
    public boolean isChecked(Piece p){
        if (p instanceof King) {
            if (checkedByKnight(p) != null || checkedDiagonal(p) != null || checkedInLine(p) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p king
     * @return return true if the king has no legal move, false o/w
     */
    public boolean noMoves(Piece p){
        int cur_x = p.getLocation().getRow();
        int cur_y = p.getLocation().getCol();
        Piece movePiece = new King(board, p.getLocation(), true);
        board.initPiece(movePiece);
        if (isChecked(p)){
            board.movePiece(movePiece, new Location(cur_x+1, cur_y));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x-1, cur_y));
            if (!isChecked(p)){
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x, cur_y+1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x, cur_y-1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x+1, cur_y+1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x+1, cur_y-1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x-1, cur_y+1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
            board.movePiece(movePiece, new Location(cur_x-1, cur_y-1));
            if (!isChecked(p)) {
                board.removePiece(movePiece);
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param p king
     * @return return true if the game is in checkmate, false o/w
     */
    public boolean isCheckMate(Piece p){
        Piece atk = checkedByKnight(p);
        if (atk == null)
            atk = checkedDiagonal(p);
        if (atk == null)
            atk = checkedInLine(p);
        if (!(p instanceof King) || atk == null)
            // no attacker
            return false;
        int atk_x = atk.getLocation().getRow();
        int atk_y = atk.getLocation().getCol();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getPiece(i, j).getOwner() == p.getOwner()) {
                    if (board.getPiece(i, j).validMove(atk_x, atk_y))
                        // can kill atk
                        return false;
                    // try to block the atk's path
                    Location []path = board.getPath(p.getLocation().getRow(), p.getLocation().getCol(), atk_x, atk_y);
                    for (int count = 0; count < path.length; count++){
                        if (board.getPiece(i, j).validMove(path[count].getRow(), path[count].getCol()))
                            return false;
                    }
                }
            }
        }
        // can't kill atk and can't block the path
        return true;
    }

    /**
     *
     * @return return true if the game is stalemate, false o/w
     */
    /*public boolean isStaleMate(){
        // other pieces can move
        // only two kings left
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = board.getPiece(i, j);
                if (!noMoves(p)){
                    return false;
                }
            }
        }

        return true;
    }*/
}