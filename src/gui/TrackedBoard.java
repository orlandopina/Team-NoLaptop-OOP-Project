package gui;

import Pieces.King;
import Pieces.Piece;
import board.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Phase 2 (Parts 5 and 6): a Board that remembers every move made on it.
 *
 * ChessBoardGUI moves pieces by calling board.movePiece(...).
 * Because this class overrides that method, giving the GUI a TrackedBoard
 * lets us record moves, notice captures, and detect when a King is taken
 * without changing the existing Board or ChessBoardGUI classes.
 */
public class TrackedBoard extends Board {

    /**
     * Receives a notification each time a move succeeds.
     */
    public interface MoveListener {

        /**
         * Called after a piece has moved.
         *
         * @param move the move that was just made
         */
        void moveMade(MoveRecord move);
    }

    /** Every successful move, oldest first. */
    private final List<MoveRecord> history = new ArrayList<>();

    /** Object to notify after each move, or null. */
    private MoveListener listener;

    /** Becomes true once a King has been captured; no moves are allowed after that. */
    private boolean gameOver;

    /**
     * Creates a board with all pieces in their starting positions.
     */
    public TrackedBoard() {
        super();
    }

    /**
     * Builds a new board by playing a list of moves from the starting position.
     * Used by Undo (play every move except the last) and by Load Game.
     *
     * @param moves the moves to play, in order
     * @return the board after the moves
     * @throws IllegalArgumentException if any move cannot be made
     */
    public static TrackedBoard replay(List<int[]> moves) {
        TrackedBoard board = new TrackedBoard();
        for (int[] m : moves) {
            if (!board.movePiece(m[0], m[1], m[2], m[3])) {
                throw new IllegalArgumentException("Move "
                        + MoveRecord.squareName(m[0], m[1]) + " "
                        + MoveRecord.squareName(m[2], m[3]) + " is not possible.");
            }
        }
        return board;
    }

    /**
     * Sets the object that is told about each move.
     *
     * @param listener the listener, or null for none
     */
    public void setMoveListener(MoveListener listener) {
        this.listener = listener;
    }

    /**
     * Moves a piece using the existing Board rules, then records the move.
     * If the move captures a King, the game is marked as over.
     *
     * @param startRank  the starting row
     * @param startFile  the starting column
     * @param targetRank the destination row
     * @param targetFile the destination column
     * @return true if the piece moved
     */
    @Override
    public boolean movePiece(int startRank, int startFile, int targetRank, int targetFile) {
        if (gameOver) {
            return false;
        }

        Piece moving = getPiece(startRank, startFile);
        Piece target = getPiece(targetRank, targetFile);

        if (!super.movePiece(startRank, startFile, targetRank, targetFile)) {
            return false;
        }

        MoveRecord move = new MoveRecord(startRank, startFile, targetRank, targetFile,
                moving.getClass().getSimpleName(), moving.getColor(),
                target == null ? null : target.getClass().getSimpleName());
        history.add(move);

        if (target instanceof King) {
            gameOver = true;
        }
        if (listener != null) {
            listener.moveMade(move);
        }
        return true;
    }

    /**
     * Returns the moves made so far.
     *
     * @return a read-only list of moves, oldest first
     */
    public List<MoveRecord> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns the moves made so far as {fromRank, fromFile, toRank, toFile} arrays,
     * in the form {@link #replay(List)} accepts.
     *
     * @return the moves, oldest first
     */
    public List<int[]> getMoveList() {
        List<int[]> moves = new ArrayList<>();
        for (MoveRecord m : history) {
            moves.add(new int[] {m.getFromRank(), m.getFromFile(), m.getToRank(), m.getToFile()});
        }
        return moves;
    }

    /** @return true once a King has been captured */
    public boolean isGameOver() {
        return gameOver;
    }
}
