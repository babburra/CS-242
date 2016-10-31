package Tests;
import Pieces.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by yutong on 9/7/16.
 */
public class KingTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        King k1 = new King(board, new Location(0, 4), true);
        King k2 = new King(board, new Location(7, 4), false);
        board.initPiece(k1);
        board.initPiece(k2);
        assertTrue(k1.equals(board.getPiece(0, 4)));
        assertTrue(k2.equals(board.getPiece(7, 4)));
    }

    @Test
    public void isValid() {
        board = new Board();
        King k1 = new King(board, new Location(4, 4), true);
        board.initPiece(k1);
        assertTrue("Move success", k1.validMove(5, 5));
        assertFalse("Move fail", k1.validMove(5, 6));
        assertFalse("Move fail", k1.validMove(7, 7));
    }
}
