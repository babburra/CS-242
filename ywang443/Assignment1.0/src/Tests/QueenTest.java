package Tests;
import Main.*;
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Created by yutong on 9/7/16.
 */
public class QueenTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Queen q1 = new Queen(0, 3, true);
        Queen q2 = new Queen(7, 3, false);
        assertTrue(q1.equals(board.getPiece(0, 3)));
        assertTrue(q2.equals(board.getPiece(7, 3)));
    }

    @Test
    public void isValid() {
        Queen k1 = new Queen(5, 5, true);
        assertTrue("Move success", k1.validMove(6, 6));
        assertTrue("Move success", k1.validMove(5, 4));
        assertFalse("Move fail", k1.validMove(7, 8));
    }
}
