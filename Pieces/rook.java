public class Rook extends Piece {
	
	public Rook(int color, int row, int col) { super(color,row,col); }
	
	@Override
	public boolean isValidMove(int rank, int file, Piece[][] board) {
		if (this.getRank() != rank && this.getFile() != file) { return false; }
		if (this.getRank() == rank && this.getFile() == file) { return false; }
		
		int rankStep = Integer.compare(rank, this.getRank());
		int fileStep = Integer.compare(file, this.getFile());
		
		int currentRank = this.getRank() + rankStep;
		int currentFile = this.getFile() + fileStep;
		
		while (currentRank != rank && currentFile != file) {
			if (board[currentFile][currentFile]) { return false; }
			currentRank += rankStep;
			currentFile += fileStep;
		}
		
		Piece targetPiece = board[rank][file];
		if (targetPiece && targetPiece.getColor() == this.getColor()) { return false; }
		
		return true;
	}
	
	@Override
	public String toString() { return getColor() ? "BR" : "WR"; }
	
	
}