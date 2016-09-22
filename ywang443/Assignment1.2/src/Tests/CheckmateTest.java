package Tests;
import Pieces.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by yutong on 9/8/16.
 */
public class CheckmateTest {
    @Test
    public void test(){
        Board board = new Board();
        King k1 = new King(board, new Location(0, 4), true);
        Rook r1 = new Rook(board, new Location(0, 0), false);
        Rook r2 = new Rook(board, new Location(1, 1), false);
        board.initPiece(k1);
        board.initPiece(r1);
        board.initPiece(r2);
        Piece p = board.getKing(true);
        assertTrue("Checkmate.", board.isCheckMate(p, true));
    }
}
