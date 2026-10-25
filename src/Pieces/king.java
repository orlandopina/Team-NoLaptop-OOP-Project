package Pieces;

public class King extends Piece {
	
	public King(int color, int row, int col) { super(color,row,col); }
	
	@Override
	public boolean isValidMove(int rank, int file, Piece[][] board) {
		int rankDelta = Math.abs(rank - this.getRank());
		int fileDelta = Math.abs(file - this.getFile());
		
		if (rankDelta > 1 || fileDelta > 1 || (rankDelta == 0 && fileDelta ==0) ) { return false; }
		
		int currentRank = this.getRank() + rankStep;
		int currentFile = this.getFile() + fileStep;

		Piece targetPiece = board[rank][file];
		if (targetPiece && targetPiece.getColor() == this.getColor()) { return false; }
		
		return true;
	}
	
	@Override
	public String toString() { return getColor() ? "BK" : "WK"; }
	
	
}