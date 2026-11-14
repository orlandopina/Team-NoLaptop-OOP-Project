package Pieces;

/**
 * Represents a king, which moves one square in any direction.
 */
public class King extends Piece {

    /**
     * Creates a king at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public King(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the king can move to the destination.
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows king rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        int rankDelta = Math.abs(rank - this.getRank());
        int fileDelta = Math.abs(file - this.getFile());

        // Move exactly one square in any direction.
        if (rankDelta > 1 || fileDelta > 1
                || (rankDelta == 0 && fileDelta == 0)) {
            return false;
        }

        Piece targetPiece = board[rank][file];

        // Cannot capture a piece of the same color.
        if (targetPiece != null
                && targetPiece.getColor() == this.getColor()) {
            return false;
        }

        return true;
    }

    /**
     * Returns the board symbol for this piece.
     *
     * @return "wK" for a white king or "bK" for a black king
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bK" : "wK";
    }
}