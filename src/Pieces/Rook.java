package Pieces;

/**
 * Represents a rook, which moves any number of squares horizontally or
 * vertically.
 */
public class Rook extends Piece {

    /**
     * Creates a rook at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Rook(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the rook can move to the destination.
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows rook rules
     */
    @Override
    public boolean isValidMove(int rank, int file, Piece[][] board) {
        // A rook must stay in the same row or column.
        if (this.getRank() != rank && this.getFile() != file) {
            return false;
        }

        // The destination must differ from the starting square.
        if (this.getRank() == rank && this.getFile() == file) {
            return false;
        }

        int rankStep = Integer.compare(rank, this.getRank());
        int fileStep = Integer.compare(file, this.getFile());

        int currentRank = this.getRank() + rankStep;
        int currentFile = this.getFile() + fileStep;

        // Check every square between the start and destination.
        while (currentRank != rank || currentFile != file) {
            if (board[currentRank][currentFile] != null) {
                return false;
            }

            currentRank += rankStep;
            currentFile += fileStep;
        }

        Piece targetPiece = board[rank][file];

        // A rook cannot capture a piece of its own color.
        if (targetPiece != null
                && targetPiece.getColor() == this.getColor()) {
            return false;
        }

        return true;
    }

    /**
     * Returns the board symbol for this piece.
     *
     * @return "wR" for a white rook or "bR" for a black rook
     */
    @Override
    public String toString() {
        return getColor() == 1 ? "bR" : "wR";
    }
}