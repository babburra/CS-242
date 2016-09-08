package Tests;

import Main.Board;
import Main.Queen;
import Main.Rook;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yutong on 9/7/16.
 */
public class RookTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Rook r1 = new Rook(0, 0, true);
        Rook r2 = new Rook(0, 7, true);
        Rook r3 = new Rook(7, 0, false);
        Rook r4 = new Rook(7, 7, false);
        assertTrue(r1.equals(board.getPiece(0, 0)));
        assertTrue(r2.equals(board.getPiece(0, 7)));
        assertTrue(r2.equals(board.getPiece(7, 0)));
        assertTrue(r2.equals(board.getPiece(7, 7)));
    }

    @Test
    public void isValid() {
        Rook k1 = new Rook(5, 5, true);
        assertTrue("Move success", k1.validMove(6, 5));
        assertTrue("Move success", k1.validMove(5, 7));
        assertFalse("Move fail", k1.validMove(7, 8));
        assertFalse("Move fail", k1.validMove(1, 7));
    }
}
