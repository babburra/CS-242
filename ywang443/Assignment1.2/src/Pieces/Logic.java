package Pieces;

/**
 * Created by yutong on 9/8/16.
 */
public class Logic {

    public Board board = new Board();

    public Logic(boolean cp){
        if (cp){
            for(int i = 1; i < 7; i++){
                board.initPiece(new Pawn(board, new Location(1, i), false));
                board.initPiece(new Pawn(board, new Location(6, i), true));
            }
            board.initPiece(new SpecialBishop(board, new Location(1, 0), false));
            board.initPiece(new SpecialBishop(board, new Location(6, 0), true));
            board.initPiece(new SpecialRook(board, new Location(1, 7), false));
            board.initPiece(new SpecialRook(board, new Location(6, 7), true));
        }
        else {
            for (int i = 0; i < 8; i++) {
                board.initPiece(new Pawn(board, new Location(1, i), false));
                board.initPiece(new Pawn(board, new Location(6, i), true));
            }
        }
        //player
        board.initPiece(new Rook(board, new Location(0, 0), false));
        board.initPiece(new Rook(board, new Location(0, 7), false));
        board.initPiece(new Knight(board, new Location(0, 1), false));
        board.initPiece(new Knight(board, new Location(0, 6), false));
        board.initPiece(new Bishop(board, new Location(0, 2), false));
        board.initPiece(new Bishop(board, new Location(0, 5), false));
        board.initPiece(new Queen(board, new Location(0, 3), false));
        board.initPiece(new King(board, new Location(0, 4), false));

        //opponent
        board.initPiece(new Rook(board, new Location(7, 0), true));
        board.initPiece(new Rook(board, new Location(7, 7), true));
        board.initPiece(new Knight(board, new Location(7, 1), true));
        board.initPiece(new Knight(board, new Location(7, 6), true));
        board.initPiece(new Bishop(board, new Location(7, 2), true));
        board.initPiece(new Bishop(board, new Location(7, 5), true));
        board.initPiece(new Queen(board, new Location(7, 3), true));
        board.initPiece(new King(board, new Location(7, 4), true));
    }

    public Board getBoard(){
        return board;
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


}