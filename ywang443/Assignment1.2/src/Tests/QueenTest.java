package Tests;
import Pieces.*;
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Created by yutong on 9/7/16.
 */
public class QueenTest {
    private Board board;
    @Test
    public void test() {
        board = new Board();
        Queen q1 = new Queen(board, new Location(0, 3), true);
        Queen q2 = new Queen(board, new Location(7, 3), false);
        board.initPiece(q1);
        board.initPiece(q2);
        assertTrue(q1.equals(board.getPiece(0, 3)));
        assertTrue(q2.equals(board.getPiece(7, 3)));
    }

    @Test
    public void isValid() {
        board = new Board();
        Queen q1 = new Queen(board, new Location(5, 5), true);
        board.initPiece(q1);
        assertTrue("Move success", q1.validMove(6, 6));
        assertTrue("Move success", q1.validMove(5, 6));
        assertFalse("Move fail", q1.validMove(7, 8));
    }
}
