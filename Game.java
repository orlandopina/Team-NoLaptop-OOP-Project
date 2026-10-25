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

            System.out.println("You entered: " + move);
        }

        System.out.println("Game ended.");
    }
}