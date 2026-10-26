package Pieces;

/**
 * Represents a knight, which moves in an L shape and can jump over other
 * pieces.
 */
public class Knight extends Piece {

    /**
     * Creates a knight at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Knight(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the knight can move to the destination. 
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows knight rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        int rankDelta = Math.abs(rank - this.getRank());
        int fileDelta = Math.abs(file - this.getFile());

        // Move two squares along one axis and one along the other.
        if (!((rankDelta == 1 && fileDelta == 2)
                || (rankDelta == 2 && fileDelta == 1))) {
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
     * @return "wN" for a white knight or "bN" for a black knight
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bN" : "wN";
    }
}