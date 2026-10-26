package Pieces;

/**
 * Represents a bishop, which moves any number of squares diagonally.
 */
public class Bishop extends Piece {

    /**
     * Creates a bishop at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Bishop(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the bishop can move to the destination.
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows bishop rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        int rankDelta = Math.abs(rank - this.getRank());
        int fileDelta = Math.abs(file - this.getFile());

        // A diagonal move changes row and column by equal amounts.
        if (rankDelta != fileDelta || rankDelta == 0) {
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
     * @return "wB" for a white bishop or "bB" for a black bishop
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bB" : "wB";
    }
}
