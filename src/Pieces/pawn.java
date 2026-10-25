package Pieces;

public class Pawn extends Piece {
	
	public Pawn(int color, int row, int col) { super(color,row,col); }
	
	@Override
	public boolean isValidMove(int rank, int file, Piece[][] board) {
		int rankDelta = rank - this.getRank();
		int fileDelta = Math.abs(file - this.getFile());
		
		int direction = this.getColor() ? 1 : -1; //BLACK GOES DOWN (INCREASING INDEX)
		
		Piece targetPiece = board[rank][file];
		
		if (fileDelta == 0) { //Forward
			if (rankDelta == direction) { return targetPiece == null; }
			if (rankDelta == 2 * direction && !this.hasMoved()) {
				int midRow = this.getRank() + direction;
				return targetPiece == null && !board[midRow][this.getFile()];
			}
		} else if (fileDelta == 1 && rowDelta == direction) {
			return targetPiece != null && targetPiece.getColor() != this.getColor();
		}
		
		return false;
	}
	
	@Override
	public String toString() { return getColor() ? "BP" : "WP"; }
	
	
}