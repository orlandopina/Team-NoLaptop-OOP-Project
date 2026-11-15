package gui;

/**
 * Phase 2 (Parts 5 and 6): an immutable record of one move made on the board.
 *
 * Stores where a piece moved from and to, which piece moved, and which
 * piece (if any) was captured. The history panel, the undo feature, and the
 * save/load feature all work from a list of these records.
 */
public class MoveRecord {

    /** Row the piece moved from (0 = top / rank 8). */
    private final int fromRank;

    /** Column the piece moved from (0 = file a). */
    private final int fromFile;

    /** Row the piece moved to. */
    private final int toRank;

    /** Column the piece moved to. */
    private final int toFile;

    /** Type of the piece that moved, e.g. "Pawn". */
    private final String pieceName;

    /** Color of the piece that moved: 0 is White, 1 is Black. */
    private final int pieceColor;

    /** Type of the captured piece, or null if nothing was captured. */
    private final String capturedName;

    /**
     * Creates a record of a move.
     *
     * @param fromRank     starting row
     * @param fromFile     starting column
     * @param toRank       destination row
     * @param toFile       destination column
     * @param pieceName    type of the piece that moved
     * @param pieceColor   color of the piece that moved (0 White, 1 Black)
     * @param capturedName type of the captured piece, or null if none
     */
    public MoveRecord(int fromRank, int fromFile, int toRank, int toFile,
                      String pieceName, int pieceColor, String capturedName) {
        this.fromRank = fromRank;
        this.fromFile = fromFile;
        this.toRank = toRank;
        this.toFile = toFile;
        this.pieceName = pieceName;
        this.pieceColor = pieceColor;
        this.capturedName = capturedName;
    }

    /** @return starting row */
    public int getFromRank() {
        return fromRank;
    }

    /** @return starting column */
    public int getFromFile() {
        return fromFile;
    }

    /** @return destination row */
    public int getToRank() {
        return toRank;
    }

    /** @return destination column */
    public int getToFile() {
        return toFile;
    }

    /** @return type of the piece that moved */
    public String getPieceName() {
        return pieceName;
    }

    /** @return color of the piece that moved (0 White, 1 Black) */
    public int getPieceColor() {
        return pieceColor;
    }

    /** @return type of the captured piece, or null if nothing was captured */
    public String getCapturedName() {
        return capturedName;
    }

    /** @return color of the captured piece (always the opponent's color) */
    public int getCapturedColor() {
        return 1 - pieceColor;
    }

    /** @return true if this move captured the opponent's King */
    public boolean capturedKing() {
        return "King".equals(capturedName);
    }

    /**
     * Converts a board position to chess notation, e.g. row 6, column 4 to "e2".
     *
     * @param rank row index (0 = rank 8)
     * @param file column index (0 = file a)
     * @return the square name
     */
    public static String squareName(int rank, int file) {
        return "" + (char) ('a' + file) + (8 - rank);
    }

    /**
     * Returns the name of a color.
     *
     * @param color 0 for White, 1 for Black
     * @return "White" or "Black"
     */
    public static String colorName(int color) {
        return color == 0 ? "White" : "Black";
    }

    /**
     * Returns this move in the plain "e2 e4" format used by save files.
     *
     * @return the move's start and end squares separated by a space
     */
    public String toSaveString() {
        return squareName(fromRank, fromFile) + " " + squareName(toRank, toFile);
    }

    /**
     * Returns a readable description such as
     * "White Queen d1-d7, takes Pawn". The captured piece is always the
     * opponent's, so its color is not repeated.
     *
     * @return the description shown in the move history
     */
    @Override
    public String toString() {
        String text = colorName(pieceColor) + " " + pieceName + " "
                + squareName(fromRank, fromFile) + "-" + squareName(toRank, toFile);
        if (capturedName != null) {
            text += ", takes " + capturedName;
        }
        return text;
    }
}
