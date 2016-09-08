package Main;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;

import java.security.acl.Owner;

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

    private Piece getKing(boolean owner) {
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

    public boolean checkedByKnight(Piece p){
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        Piece piece1 = board.getPiece(loci + 1,locj + 2);
        if (piece1 instanceof Knight && !piece1.getOwner())
            return true;
        Piece piece2 = board.getPiece(loci + 2,locj + 1);
        if (piece2 instanceof Knight && !piece2.getOwner())
            return true;
        Piece piece3 = board.getPiece(loci - 1,locj + 2);
        if (piece3 instanceof Knight && !piece3.getOwner())
            return true;
        Piece piece4 = board.getPiece(loci - 2,locj + 1);
        if (piece4 instanceof Knight && !piece4.getOwner())
            return true;
        Piece piece5 = board.getPiece(loci - 2,locj - 1);
        if (piece5 instanceof Knight && !piece5.getOwner())
            return true;
        Piece piece6 = board.getPiece(loci - 1,locj - 2);
        if (piece6 instanceof Knight && !piece6.getOwner())
            return true;
        Piece piece7 = board.getPiece(loci + 2,locj - 1);
        if (piece7 instanceof Knight && !piece7.getOwner())
            return true;
        Piece piece8 = board.getPiece(loci + 1,locj - 2);
        if (piece8 instanceof Knight && !piece8.getOwner())
            return true;
        return false;
    }

    public boolean checkedDiagonal(Piece p){
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        int fixj = locj;
        for (int i = loci; i > 0; i--){
            Piece piece = board.getPiece(i, fixj + 1);
            Piece piece1 = board.getPiece(i, fixj - 1);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && !piece.getOwner()){
                return true;
            }
            if (piece1 != null && piece1 instanceof Bishop && !piece1.getOwner()){
                return true;
            }
        }
        fixj = locj;
        for (int i = loci; i < 7; i++){
            Piece piece = board.getPiece(i, fixj + 1);
            Piece piece1 = board.getPiece(i, fixj - 1);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && !piece.getOwner()){
                return true;
            }
            if (piece1 != null && piece1 instanceof Bishop && !piece1.getOwner()){
                return true;
            }
        }
        return false;
    }

    public boolean checkedInLine(Piece p){
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        int fixi = loci;
        int fixj = locj;
        for (int i = loci; i > 0; i--){
            Piece piece = board.getPiece(i, fixj);
            Piece piece1 = board.getPiece(fixi++, fixj);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && !piece.getOwner()){
                return true;
            }
            if (piece1 != null && piece1 instanceof Bishop && !piece1.getOwner()){
                return true;
            }
        }
        fixi = loci;
        fixj = locj;
        for (int j = locj; j < 0; j--){
            Piece piece = board.getPiece(fixi, j);
            Piece piece1 = board.getPiece(fixi, fixj++);
            if (piece == null && piece1 == null)
                break;
            if (piece != null && (piece instanceof Pawn || piece instanceof Bishop) && !piece.getOwner()){
                return true;
            }
            if (piece1 != null && piece1 instanceof Bishop && !piece1.getOwner()){
                return true;
            }
        }
        return false;
    }

    public boolean isChecked(){
        Piece p = getKing(true);
        if (checkedByKnight(p) || checkedDiagonal(p) || checkedInLine(p)){
            return true;
        }
        return false;
    }

    public boolean isCheckmate(){
        Piece p = getKing(true);
        int loci = p.getLocation().getRow();
        int locj = p.getLocation().getCol();
        Piece moveKing = new King(board, p.getLocation(), true);
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci+1, locj));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci-1, locj));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci, locj+1));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci, locj-1));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci+1, locj+1));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci+1, locj-1));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci-1, locj+1));
            if (!isChecked())
                return false;
        }
        if (isChecked()){
            board.movePiece(moveKing, new Location(loci-1, locj-1));
            if (!isChecked())
                return false;
        }
        return true;
    }


}
