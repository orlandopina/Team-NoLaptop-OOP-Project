package Pieces;

/**
 * Represents a queen, which moves any number of squares horizontally,
 * vertically, or diagonally.
 */
public class Queen extends Piece {

    /**
     * Creates a queen at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Queen(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the queen can move to the destination. 
     * 
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows queen rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        int rankDelta = Math.abs(rank - this.getRank());
        int fileDelta = Math.abs(file - this.getFile());

        boolean isStraight =
                this.getRank() == rank || this.getFile() == file;
        boolean isDiagonal = rankDelta == fileDelta;

        // A queen moves horizontally, vertically, or diagonally.
        if (!isStraight && !isDiagonal) {
            return false;
        }

        if (rankDelta == 0 && fileDelta == 0) {
            return false;
        }

        int rankStep = Integer.compare(rank, this.getRank());
        int fileStep = Integer.compare(file, this.getFile());

        int currentRank = this.getRank() + rankStep;
        int currentFile = this.getFile() + fileStep;

        // Check every square before the destination.
        while (currentRank != rank || currentFile != file) {
            if (board[currentRank][currentFile] != null) {
                return false;
            }

            currentRank += rankStep;
            currentFile += fileStep;
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
     * @return "wQ" for a white queen or "bQ" for a black queen
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bQ" : "wQ";
    }
}