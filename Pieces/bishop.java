public class Bishop extends Piece {
	
	public Bishop(int color, int row, int col) { super(color,row,col); }
	
	@Override
	public boolean isValidMove(int rank, int file, Piece[][] board) {
		int rankDelta = Math.abs(rank - this.getRank());
		int fileDelta = Math.abs(file - this.getFile());
		if (rankDelta != fileDelta || rankDelta == 0) { return false; }
		
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
	public String toString() { return getColor() ? "BB" : "WB"; }
	
	
}