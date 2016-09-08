package Tests;
import Main.*;
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Created by yutong on 9/7/16.
 */
public class KnightTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Knight k1 = new Knight(0, 1, true);
        Knight k2 = new Knight(0, 6, true);
        Knight k3 = new Knight(7, 1, false);
        Knight k4 = new Knight(7, 6, false);
        assertTrue(k1.equals(board.getPiece(0, 1)));
        assertTrue(k2.equals(board.getPiece(0, 6)));
        assertTrue(k3.equals(board.getPiece(7, 1)));
        assertTrue(k4.equals(board.getPiece(7, 6)));
    }

    @Test
    public void isValid() {
        Knight k1 = new Knight(4, 4, true);
        assertTrue("Move success", k1.validMove(5, 6));
        assertTrue("Move success", k1.validMove(2, 3));
        assertFalse("Move fail", k1.validMove(5, 5));
        assertFalse("Move fail", k1.validMove(7, 8));
    }
}
