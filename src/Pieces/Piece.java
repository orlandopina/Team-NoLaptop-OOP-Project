package Pieces;

/**
 * Defines the shared properties and behavior of chess pieces.
 */
public abstract class Piece {

    /** Piece color: 0 is white, 1 is black. */
    private int color;

    /** Current board row. */
    private int position_rank;

    /** Current board column. */
    private int position_file;

    /** Whether this piece has moved. */
    private boolean hasMoved;

    /**
     * Creates a piece at a board position.
     * @param color piece color
     * @param rank starting row
     * @param file starting column
     */
    public Piece(int color, int rank, int file) {
        this.color = color;
        this.position_rank = rank;
        this.position_file = file;
        this.hasMoved = false;
    }

    /** @return the piece's color */
    public int getColor() {
        return color;
    }

    /** @return the current row */
    public int getRank() {
        return position_rank;
    }

    /** @return the current column */
    public int getFile() {
        return position_file;
    }

    /** @return whether the piece has moved */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Checks the piece's movement rules.
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return whether the move meets this piece's rules
     */
    public abstract boolean isValidMove(
            int rank, int file, Piece[][] board);

    /**
     * Updates the position after a successful move.
     * @param rank new row
     * @param file new column
     */
    public void setPosition(int rank, int file) {
        position_rank = rank;
        position_file = file;
        hasMoved = true;
    }
}