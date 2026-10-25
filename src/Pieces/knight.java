package Pieces;

public class Knight extends Piece {
	
	public Knight(int color, int row, int col) { super(color,row,col); }
	
	@Override
	public boolean isValidMove(int rank, int file, Piece[][] board) {
		int rankDelta = Math.abs(rank - this.getRank());
		int fileDelta = Math.abs(file - this.getFile());
		
		if (!((rankDelta == 1 && fileDelta == 2) || (rankDelta == 2 && fileDelta == 1)) ) { return false; }
		
		Piece targetPiece = board[rank][file];
		if (targetPiece && targetPiece.getColor() == this.getColor()) { return false; }
		
		return true;
	}
	
	@Override
	public String toString() { return getColor() ? "BN" : "WN"; }
	
	
}