package Tests;
import Main.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by yutong on 9/7/16.
 */
public class PawnTest {
    Board board;
    @Test
    public void test() {
        board = new Board();
        Pawn p1 = new Pawn(1, 0, true);
        Pawn p2 = new Pawn(6, 0, true);
        Pawn p3 = new Pawn(1, 7, false);
        Pawn p4 = new Pawn(6, 7, false);
        assertTrue(p1.equals(board.getPiece(1, 0)));
        assertTrue(p2.equals(board.getPiece(6, 0)));
        assertTrue(p3.equals(board.getPiece(1, 7)));
        assertTrue(p4.equals(board.getPiece(6, 7)));
    }

    @Test
    public void isValid() {
        Pawn k1 = new Pawn(5, 5, true);
        assertTrue("Move success", k1.validMove(6, 6));
        assertTrue("Move success", k1.validMove(6, 4));
        assertFalse("Move fail", k1.validMove(7, 8));
    }
}
