import game.Game;

/**
 * Main class that starts the chess game.
 */
public class Main {

    /**
     * Starts the chess game.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        gui.ChessBoardGUI.main(args);
        Game game = new Game();
        game.start();
    }
}