import java.util.Scanner;

/**
 * Handles the chess game's console interface.
 */
public class Game {

    /**
     * Starts the application.
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
            System.out.print("Enter move: ");

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

System.out.println("Valid move format: " + move);
        }

        System.out.println("Game ended.");
    }
    /**
 * Checks whether input contains two valid chess coordinates.
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
}