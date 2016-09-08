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
        Knight k1 = new Knight(board, new Location(0, 1), true);
        Knight k2 = new Knight(board, new Location(0, 6), true);
        Knight k3 = new Knight(board, new Location(7, 1), false);
        Knight k4 = new Knight(board, new Location(7, 6), false);
        board.initPiece(k1);
        board.initPiece(k2);
        board.initPiece(k3);
        board.initPiece(k4);
        assertTrue(k1.equals(board.getPiece(0, 1)));
        assertTrue(k2.equals(board.getPiece(0, 6)));
        assertTrue(k3.equals(board.getPiece(7, 1)));
        assertTrue(k4.equals(board.getPiece(7, 6)));
    }

    @Test
    public void isValid() {
        board = new Board();
        Knight k1 = new Knight(board, new Location(3, 1), true);
        board.initPiece(k1);
        assertTrue("Move success", k1.validMove(4, 3));
        assertTrue("Move success", k1.validMove(5, 0));
        assertFalse("Move fail", k1.validMove(5, 5));
        assertFalse("Move fail", k1.validMove(7, 8));
    }
}
