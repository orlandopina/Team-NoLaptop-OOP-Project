public class Rook extends Piece {

    public Rook(int color, int row, int col) {
        super(color, row, col);
    }

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

    @Override
    public String toString() {
        return getColor() == 1 ? "bR" : "wR";
    }
}