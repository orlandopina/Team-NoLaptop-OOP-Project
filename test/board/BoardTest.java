package board;

import Pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Board class.
 */
public class BoardTest {

    /** Board under test, recreated before each test. */
    private Board board;

    /** Creates a fresh board before each test. */
    @BeforeEach
    void setUp() {
        board = new Board();
    }

    /** Checks that starting pieces are in the correct squares. */
    @Test
    void initialPiecesArePlacedCorrectly() {
        assertEquals("wp", board.getPiece(6, 4).toString());
        assertEquals("bp", board.getPiece(1, 4).toString());
        assertEquals("wR", board.getPiece(7, 0).toString());
        assertEquals("bK", board.getPiece(0, 4).toString());
        assertNull(board.getPiece(4, 4));
    }

    /** Checks that the board starts with 32 pieces. */
    @Test
    void boardStartsWith32Pieces() {
        int count = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board.getPiece(r, c) != null) {
                    count++;
                }
            }
        }
        assertEquals(32, count);
    }

    /** Checks that a rook cannot jump over a pawn. */
    @Test
    void blockedRookCannotMove() {
        assertFalse(board.movePiece(7, 0, 4, 0));
    }

    /** Checks a legal pawn move and that the board updates. */
    @Test
    void whitePawnMovesE2ToE4() {
        assertTrue(board.movePiece(6, 4, 4, 4));
        assertNull(board.getPiece(6, 4));
        assertEquals("wp", board.getPiece(4, 4).toString());
    }

    /** Checks a diagonal pawn capture. */
    @Test
    void whitePawnCapturesDiagonally() {
        assertTrue(board.movePiece(6, 4, 4, 4));
        assertTrue(board.movePiece(1, 3, 3, 3));
        assertTrue(board.movePiece(4, 4, 3, 3));
        assertEquals("wp", board.getPiece(3, 3).toString());
    }

    /** Checks that moving from an empty square fails. */
    @Test
    void movingEmptySquareFails() {
        assertFalse(board.movePiece(4, 4, 3, 4));
    }

    /** Checks that out-of-bounds positions are handled safely. */
    @Test
    void outOfBoundsIsRejected() {
        assertNull(board.getPiece(8, 0));
        assertFalse(board.movePiece(6, 4, 9, 4));
    }
}