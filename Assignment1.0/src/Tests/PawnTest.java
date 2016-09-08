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
        Pawn p1 = new Pawn(board, new Location(1, 0), true);
        Pawn p2 = new Pawn(board, new Location(1, 7), true);
        Pawn p3 = new Pawn(board, new Location(6, 0), false);
        Pawn p4 = new Pawn(board, new Location(6, 7), false);
        board.initPiece(p1);
        board.initPiece(p2);
        board.initPiece(p3);
        board.initPiece(p4);
        assertTrue(p1.equals(board.getPiece(1, 0)));
        assertTrue(p2.equals(board.getPiece(1, 7)));
        assertTrue(p3.equals(board.getPiece(6, 0)));
        assertTrue(p4.equals(board.getPiece(6, 7)));
    }

    @Test
    public void isValid() {
        Pawn k1 = new Pawn(board, new Location(3, 3), true);
        assertTrue("Move success", k1.validMove(4, 3));
        assertFalse("Move fail", k1.validMove(6, 4));
        assertFalse("Move fail", k1.validMove(7, 8));
    }
}
