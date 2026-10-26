package Pieces;

/**
 * Represents a queen, which moves any number of squares horizontally,
 * vertically, or diagonally.
 */
public class Queen extends Piece {

    /**
     * Creates a queen at a board position.
     *
     * @param color piece color (0 is white, 1 is black)
     * @param row starting row
     * @param col starting column
     */
    public Queen(int color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Checks whether the queen can move to the destination. 
     *
     * @param rank destination row
     * @param file destination column
     * @param board current board
     * @return true if the move follows queen rules
     */
    @Override
    public boolean