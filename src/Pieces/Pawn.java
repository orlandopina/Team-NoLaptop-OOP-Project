package Pieces;

public class Pawn extends Piece {

    public Pawn(int color, int row, int col) {
        super(color, row, col);
    }

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

    @Override
    public String toString() {
        return getColor() == 1 ? "bp" : "wp";
    }
}