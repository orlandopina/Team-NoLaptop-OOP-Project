package Pieces;

public abstract class Piece {
	private int color; //0 is White, 1 is Black
	private int position_rank; //Array Row Index
	private int position_file; //Array Column Index
	private boolean hasMoved; //for Castling
	
	public Piece(int color, int rank, int file){
		this.color = color;
		this.position_rank = rank;
		this.position_file = file;
		this.hasMoved = false;
	}
	
	public int getColor() {return this.color;}
	public int getRank() {return this.position_rank;}
	public int getFile() {return this.position_file;}

	
	public abstract boolean isValid(int rank, int file, Piece[][] board);
	
	public void setPosition(int rank, int file) {
		this.position_rank = rank;
		this.position_file = file;
		this.hasMoved = true;
	}
}