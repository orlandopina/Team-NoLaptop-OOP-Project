public class Board {
	private static final int BOARD_SIZE = 8;
	private final Piece[][] grid;
	
	public Board() {
		this.grid = new Piece[BOARD_SIZE][BOARD_SIZE];
		initializeBoard();
	}
	
	private void initializeBoard() {
		//COLOR = 1 => BLACK
		//COLOR = 0 => WHITE
		//BLACK PIECES
		grid[0][0] = new Rook(1, 0, 0); //COLOR RANK FILE
		grid[0][7] = new Rook(1, 0, 7);
		grid[0][1] = new Knight(1, 0, 1);
		grid[0][6] = new Knight(1, 0, 6);
		grid[0][2] = new Bishop(1, 0, 2);
		grid[0][5] = new Bishop(1, 0, 5);
		grid[0][3] = new Queen(1, 0, 3);
		grid[0][4] = new King(1, 0, 4);
		for (int c = 0; c < BOARD_SIZE; c++) { grid[1][c] = new Pawn (1, 1, c);
		
		
		//WHITE PIECES
		grid[7][0] = new Rook(7, 0, 0); //COLOR RANK FILE
		grid[7][7] = new Rook(7, 0, 7);
		grid[7][1] = new Knight(7, 0, 1);
		grid[7][6] = new Knight(7, 0, 6);
		grid[7][2] = new Bishop(7, 0, 2);
		grid[7][5] = new Bishop(7, 0, 5);
		grid[7][3] = new Queen(7, 0, 3);
		grid[7][4] = new King(7, 0, 4);
		for (int c = 0; c < BOARD_SIZE; c++) { grid[6][c] = new Pawn (0, 6, c);
	}
	
	public boolean movePiece(int startRank, int startFile, int targetRank, int targetFile){
		if (isOutOfBounds(startRank, startFile) || isOutOfBounds(targetRank, targetFile)) { return false; }
		
		Piece movingPiece = grid[startRank][startFile];
		
		if (!movingPiece) { return false; }
		if (!movingPiece.isValidMove(targetRank, targetFile, grid)) { return false; }
		
		grid[targetRank][targetFile] = movingPiece;
		grid[startRow][startCol] = null;
		
		movingPiece.setPosition(targetRank, targetFile);
		
		return true;
	}
	
	public Piece getPiece(int rank, int file) {
		if (isOutOfBounds(rank, file)) { return null; }
		return grid[rank][file];
	}
	
	private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE;
    }
	
	public void printBoard() {
		System.out.println("   AA BB CC DD EE FF GG HH");
		for (int r = 0; r < BOARD_SIZE; r++) {
			System.out.print(" " + (8-r) + " ");
			for (int c = 0; c < BOARD_SIZE; c++) {
				Piece piece = grid[r][c];
				if (piece == null) {
					System.out.print(".. ");
				} else {
					System.out.print(piece + " ");
				}
			}
		}
	}

	
}