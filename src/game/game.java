package game;

import board.Board;
import Pieces.Piece;
import utils.Utils;
import java.util.Scanner;

/**
 * Represents the chess game.
 * Handles player turns, user input, and board updates.
 */
public class Game {

    /** Stores the chessboard. */
    private Board board;

    /** Tracks the current player (0 = white, 1 = black). */
    private int currentTurn;

    /** Reads input from the player. */
    private Scanner scanner;

    /**
     * Creates a new chess game.
     */
    public Game() {
        board = new Board();
        currentTurn = 0;
        scanner = new Scanner(System.in);
    }

    // Game methods go here
}