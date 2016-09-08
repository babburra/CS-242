package Tests;
import Main.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by yutong on 9/7/16.
 */
public class BishopTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Bishop b1 = new Bishop(board, new Location(0, 2), true);
        Bishop b2 = new Bishop(board, new Location(0, 5), true);
        Bishop b3 = new Bishop(board, new Location(7, 2), false);
        Bishop b4 = new Bishop(board, new Location(7, 5), false);
        board.initPiece(b1);
        board.initPiece(b2);
        board.initPiece(b3);
        board.initPiece(b4);
        assertTrue(b1.equals(board.getPiece(0, 2)));
        assertTrue(b2.equals(board.getPiece(0, 5)));
        assertTrue(b3.equals(board.getPiece(7, 2)));
        assertTrue(b4.equals(board.getPiece(7, 5)));
    }

    @Test
    public void isValid() {
        board = new Board();
        Bishop b1 = new Bishop(board, new Location(4, 4), true);
        board.initPiece(b1);
        assertTrue("Move success", b1.validMove(5, 5));
        assertFalse("Move fail", b1.validMove(5, 6));
        assertFalse("Move fail", b1.validMove(9, 8));
    }
}
