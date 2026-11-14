package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Utils class.
 */
public class UtilsTest {

    /** Checks that valid moves are accepted. */
    @Test
    void validMoveFormatsAreAccepted() {
        assertTrue(Utils.isValidMoveFormat("E2 E4"));
        assertTrue(Utils.isValidMoveFormat("e2 e4"));
    }

    /** Checks that invalid input is rejected. */
    @Test
    void invalidMoveFormatsAreRejected() {
        assertFalse(Utils.isValidMoveFormat("E9 E4"));
        assertFalse(Utils.isValidMoveFormat("hello"));
        assertFalse(Utils.isValidMoveFormat("E2"));
        assertFalse(Utils.isValidMoveFormat(null));
    }

    /** Checks file letter to column index conversion. */
    @Test
    void fileConvertsToColumnIndex() {
        assertEquals(0, Utils.getFile('A'));
        assertEquals(4, Utils.getFile('e'));
        assertEquals(7, Utils.getFile('H'));
    }

    /** Checks rank number to row index conversion. */
    @Test
    void rankConvertsToRowIndex() {
        assertEquals(0, Utils.getRank('8'));
        assertEquals(6, Utils.getRank('2'));
        assertEquals(7, Utils.getRank('1'));
    }
}