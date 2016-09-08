package Tests;
import Main.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by yutong on 9/7/16.
 */
public class BishopTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Bishop b1 = new Bishop(0, 2, true);
        Bishop b2 = new Bishop(0, 5, true);
        Bishop b3 = new Bishop(7, 2, false);
        Bishop b4 = new Bishop(7, 5, false);
        assertTrue(b1.equals(board.getPiece(0, 2)));
        assertTrue(b2.equals(board.getPiece(0, 5)));
        assertTrue(b3.equals(board.getPiece(7, 2)));
        assertTrue(b4.equals(board.getPiece(7, 5)));
    }

    @Test
    public void isValid() {
        Bishop b1 = new Bishop(4, 4, true);
        assertTrue("Move success", b1.validMove(5, 5));
        assertFalse("Move fail", b1.validMove(5, 6));
        assertFalse("Move fail", b1.validMove(9, 8));
    }
}
