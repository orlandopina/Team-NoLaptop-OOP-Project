public class King extends Piece {

    public King(int color, int row, int col) {
        super(color, row, col);
    }

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

    @Override
    public String toString() {
        return getColor() == 1 ? "bK" : "wK";
    }
}