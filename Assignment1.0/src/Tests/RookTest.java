package Tests;

import Main.Board;
import Main.Location;
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
        Rook r1 = new Rook(board, new Location(0, 0), true);
        Rook r2 = new Rook(board, new Location(0, 7), true);
        Rook r3 = new Rook(board, new Location(7, 0), false);
        Rook r4 = new Rook(board, new Location(7, 7), false);
        board.initPiece(r1);
        board.initPiece(r2);
        board.initPiece(r3);
        board.initPiece(r4);
        assertTrue(r1.equals(board.getPiece(0, 0)));
        assertTrue(r2.equals(board.getPiece(0, 7)));
        assertTrue(r3.equals(board.getPiece(7, 0)));
        assertTrue(r4.equals(board.getPiece(7, 7)));
    }

    @Test
    public void isValid() {
        board = new Board();
        Rook r1 = new Rook(board, new Location(4, 4), true);
        board.initPiece(r1);
        assertTrue("Move success", r1.validMove(4, 3));
        assertTrue("Move success", r1.validMove(4, 7));
        assertFalse("Move fail", r1.validMove(7, 8));
        assertFalse("Move fail", r1.validMove(1, 7));
    }
}
