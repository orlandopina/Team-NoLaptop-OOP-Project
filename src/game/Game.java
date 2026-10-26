package game;

import board.Board;
import Pieces.Piece;

import java.util.Scanner;

/**
 * Handles the chess game's console interface.
 */
public class Game {
    /** The board used by this game. */
    private final Board board;

    /** Current player: 0 is White, 1 is Black. */
    private int currentTurn;

    /** Creates a game with the pieces in their starting positions. */
    public Game() {
        board = new Board();
        currentTurn = 0;
    }
    /**
 * Starts the console game.
 */
public void start() {
    play();
}

    /**
     * Starts the application.
     * 
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    /**
     * Reads player input until the player quits.
     */
    public void play() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Chess!");
        System.out.println("Enter a move like E2 E4, or quit to exit.");

        while (true) {
            board.printBoard();
            String player = currentTurn == 0 ? "White" : "Black";
            System.out.print(player + " to move: ");

            if (!input.hasNextLine()) {
                break;
            }

            String move = input.nextLine().trim();

            if (move.equalsIgnoreCase("quit")) {
                break;
            }

            if (!isValidMoveFormat(move)) {
                System.out.println("Invalid format. Enter a move like E2 E4.");
                continue;
            }

            String[] squares = move.split("\\s+");

            int startRow = getRow(squares[0]);
            int startColumn = getColumn(squares[0]);
            int targetRow = getRow(squares[1]);
            int targetColumn = getColumn(squares[1]);

            Piece movingPiece = board.getPiece(startRow, startColumn);

            if (movingPiece == null) {
                System.out.println("There is no piece on that starting square.");
                continue;
            }

            if (movingPiece.getColor() != currentTurn) {
                System.out.println("Choose one of your own pieces.");
                continue;
            }

            Piece targetPiece = board.getPiece(targetRow, targetColumn);

            if (targetPiece != null
                    && targetPiece.getColor() == currentTurn) {
                System.out.println("You cannot move onto your own piece.");
                continue;
            }

            boolean moved = board.movePiece(
                    startRow, startColumn, targetRow, targetColumn);

            if (moved) {
                System.out.println("Move successful.");
                currentTurn = 1 - currentTurn;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        System.out.println("Game ended.");
    }

    /**
     * Checks whether input contains two valid chess coordinates.
     * 
     * @param move the player's input
     * @return true if the format is valid
     */
    private boolean isValidMoveFormat(String move) {
        String[] squares = move.trim().split("\\s+");

        if (squares.length != 2) {
            return false;
        }

        for (String square : squares) {
            if (square.length() != 2) {
                return false;
            }

            char file = Character.toUpperCase(square.charAt(0));
            char rank = square.charAt(1);

            if (file < 'A' || file > 'H' || rank < '1' || rank > '8') {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a chess rank to a board row.
     * 
     * @param square a validated coordinate, such as E2
     * @return the array row from 0 to 7
     */
    private int getRow(String square) {
        return '8' - square.charAt(1);
    }

    /**
     * Converts a chess file to a board column.
     * 
     * @param square a validated coordinate, such as E2
     * @return the array column from 0 to 7
     */
    private int getColumn(String square) {
        return Character.toUpperCase(square.charAt(0)) - 'A';
    }
}