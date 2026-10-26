
package utils;

/**
 * Provides utility methods for the chess game.
 * These methods help validate player input and
 * convert chess coordinates into array indexes.
 */
public class Utils {

    /**
     * Checks whether the player entered a move
     * in the correct format, such as "E2 E4".
     *
     * @param move The move entered by the player.
     * @return true if the format is correct, false otherwise.
     */
    public static boolean isValidMoveFormat(String move) {
        if (move == null) {
            return false;
        }

        return move.trim().toUpperCase()
                   .matches("[A-H][1-8]\\s+[A-H][1-8]");
    }

    /**
     * Converts a chess column letter into an array index.
     * Example: A = 0, B = 1, C = 2, etc.
     *
     * @param file The chess column letter.
     * @return The column index from 0 to 7.
     */
    public static int getFile(char file) {
        return Character.toUpperCase(file) - 'A';
    }

    /**
     * Converts a chess rank number into an array index.
     * Example: 8 = 0, 7 = 1, 6 = 2, etc.
     *
     * @param rank The chess rank character.
     * @return The row index from 0 to 7.
     */
    public static int getRank(char rank) {
        return 8 - Character.getNumericValue(rank);
    }
}
