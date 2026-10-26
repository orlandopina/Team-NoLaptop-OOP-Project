package board;

import Pieces.*;

/**
 * Represents the chessboard for the game.
 * Stores chess pieces, initializes their positions,
 * and handles moving and displaying pieces.
 */
public class Board {

    /** Defines the size of the chessboard. */
    private static final int BOARD_SIZE = 8;

    /** Stores all chess pieces in an 8x8 array. */
    private final Piece[][] grid;

    /**
     * Creates a chessboard and places the pieces
     * in their starting positions.
     */
    public Board() {this.grid = new Piece[BOARD_SIZE][BOARD_SIZE];initializeBoard();}

    /**
     * Initializes the chessboard with black and white pieces.
     */
    private void initializeBoard() {

        // COLOR = 1 => BLACK
        // COLOR = 0 => WHITE

        // BLACK PIECES
        grid[0][0] = new Rook(1, 0, 0);
        grid[0][7] = new Rook(1, 0, 7);
        grid[0][1] = new Knight(1, 0, 1);
        grid[0][6] = new Knight(1, 0, 6);
        grid[0][2] = new Bishop(1, 0, 2);
        grid[0][5] = new Bishop(1, 0, 5);
        grid[0][3] = new Queen(1, 0, 3);
        grid[0][4] = new King(1, 0, 4);

        for (int c = 0; c < BOARD_SIZE; c++) {grid[1][c] = new Pawn(1, 1, c);}

        // WHITE PIECES
        grid[7][0] = new Rook(0, 7, 0);
        grid[7][7] = new Rook(0, 7, 7);
        grid[7][1] = new Knight(0, 7, 1);
        grid[7][6] = new Knight(0, 7, 6);
        grid[7][2] = new Bishop(0, 7, 2);
        grid[7][5] = new Bishop(0, 7, 5);
        grid[7][3] = new Queen(0, 7, 3);
        grid[7][4] = new King(0, 7, 4);

        for (int c = 0; c < BOARD_SIZE; c++) {grid[6][c] = new Pawn(0, 6, c);}
    }

    /**
     * Attempts to move a piece to a new position.
     *
     * @param startRank The starting row.
     * @param startFile The starting column.
     * @param targetRank The destination row.
     * @param targetFile The destination column.
     * @return true if the move succeeds, false otherwise.
     */
    public boolean movePiece(int startRank, int startFile,int targetRank, int targetFile) {

        if (isOutOfBounds(startRank, startFile) ||isOutOfBounds(targetRank, targetFile)) {
            return false;
        }

        Piece movingPiece = grid[startRank][startFile];

        if (movingPiece == null) {
            return false;
        }

        if (!movingPiece.isValidMove(targetRank, targetFile, grid)) {
            return false;
        }

        grid[targetRank][targetFile] = movingPiece;
        grid[startRank][startFile] = null;

        movingPiece.setPosition(targetRank, targetFile);

        return true;
    }

    /**
     * Returns the chess piece at a given position.
     *
     * @param rank The row index.
     * @param file The column index.
     * @return The piece at the position, or null if empty.
     */
    public Piece getPiece(int rank, int file) {
        if (isOutOfBounds(rank, file)) {
            return null;
        }

        return grid[rank][file];
    }

    /**
     * Checks whether a position is outside the chessboard.
     *
     * @param row The row index.
     * @param col The column index.
     * @return true if the position is outside the board.
     */
    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= BOARD_SIZE ||
               col < 0 || col >= BOARD_SIZE;
    }

    /**
     * Displays the current chessboard in the console.
     */
    public void printBoard() {

        System.out.println("   A  B  C  D  E  F  G  H");

        for (int r = 0; r < BOARD_SIZE; r++) {

            System.out.print(" " + (8 - r) + " ");

            for (int c = 0; c < BOARD_SIZE; c++) {

                Piece piece = grid[r][c];

                if (piece == null) {
                    System.out.print("## ");
                } else {
                    System.out.print(piece + " ");
                }
            }

            System.out.println();
        }
    }
}
