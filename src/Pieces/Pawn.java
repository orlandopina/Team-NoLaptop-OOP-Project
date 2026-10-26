package Pieces;

/**
 * Represents a pawn, which moves forward and captures diagonally.
 */
public class Pawn extends Piece {

    /**
     * Creates a pawn at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Pawn(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the pawn can move to the destination. 
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows pawn rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        int rankDelta = rank - this.getRank();
        int fileDelta = Math.abs(file - this.getFile());

        // Black moves toward increasing row indexes; white moves upward.
        int direction = this.getColor() == 1 ? 1 : -1;

        Piece targetPiece = board[rank][file];

        if (fileDelta == 0) {
            // Move forward one square if it is empty.
            if (rankDelta == direction) {
                return targetPiece == null;
            }

            // On its first move, both squares must be empty.
            if (rankDelta == 2 * direction && !this.hasMoved()) {
                int midRow = this.getRank() + direction;

                return targetPiece == null
                        && board[midRow][this.getFile()] == null;
            }
        } else if (fileDelta == 1 && rankDelta == direction) {
            // Capture an opponent's piece diagonally.
            return targetPiece != null
                    && targetPiece.getColor() != this.getColor();
        }

        return false;
    }

    /**
     * Returns the board symbol for this piece.
     *
     * @return "wp" for a white pawn or "bp" for a black pawn
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bp" : "wp";
    }
}