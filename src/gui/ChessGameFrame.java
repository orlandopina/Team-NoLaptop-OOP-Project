package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Phase 2, Parts 5 and 6: the main game window.
 *
 * Holds the existing ChessBoardGUI board panel and adds:
 * 
 *   Part 5, Endgame Notification: when a King is captured, a pop-up
 *       declares the winner and the game ends.
 *   Part 6, Feature 1, Menu Bar: New Game, Save Game, Load Game.
 *   Part 6, Feature 3, Game History: a HistoryPanel with the
 *       move list, captured pieces, and an Undo button.
 * 
 *
 * Run this class to play the full Phase 2 game.
 */
public class ChessGameFrame extends JFrame {

    /** Line at the top of every save file, used to recognize our files. */
    private static final String SAVE_HEADER = "# Team NoLaptop chess save";

    /** The board currently being played. */
    private TrackedBoard board;

    /** The panel that draws the board (written by teammates, reused unchanged). */
    private ChessBoardGUI boardPanel;

    /** Side panel with move history, captured pieces, and Undo. */
    private final HistoryPanel historyPanel;

    /** Shared file chooser so it remembers the last folder used. */
    private final JFileChooser fileChooser = new JFileChooser();

    /**
     * Creates the game window and starts a new game.
     */
    public ChessGameFrame() {
        super("Chess - Team NoLaptop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        fileChooser.setFileFilter(new FileNameExtensionFilter("Chess save (*.chess)", "chess"));

        historyPanel = new HistoryPanel(this::undo);
        add(historyPanel, BorderLayout.EAST);
        setJMenuBar(createMenuBar());

        startGame(new TrackedBoard());
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Builds the menu bar with the Game and Edit menus.
     *
     * @return the menu bar
     */
    private JMenuBar createMenuBar() {
        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.add(menuItem("New Game", KeyEvent.VK_N, shortcut, this::newGame));
        gameMenu.add(menuItem("Save Game...", KeyEvent.VK_S, shortcut, this::saveGame));
        gameMenu.add(menuItem("Load Game...", KeyEvent.VK_O, shortcut, this::loadGame));
        gameMenu.addSeparator();
        gameMenu.add(menuItem("Exit", KeyEvent.VK_Q, shortcut, () -> System.exit(0)));

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(menuItem("Undo Move", KeyEvent.VK_Z, shortcut, this::undo));

        JMenuBar bar = new JMenuBar();
        bar.add(gameMenu);
        bar.add(editMenu);
        return bar;
    }

    /**
     * Creates a menu item with a keyboard shortcut.
     *
     * @param text      the item's label
     * @param key       the shortcut key
     * @param modifiers the shortcut modifier keys (Ctrl, or Cmd on a Mac)
     * @param action    what the item does
     * @return the menu item
     */
    private static JMenuItem menuItem(String text, int key, int modifiers, Runnable action) {
        JMenuItem item = new JMenuItem(text);
        item.setAccelerator(KeyStroke.getKeyStroke(key, modifiers));
        item.addActionListener(e -> action.run());
        return item;
    }

    /**
     * Shows a board in the window, replacing the previous one.
     * Used for a new game, after Undo, and after Load Game.
     *
     * @param newBoard the board to play on
     */
    private void startGame(TrackedBoard newBoard) {
        if (boardPanel != null) {
            remove(boardPanel);
        }
        board = newBoard;
        board.setMoveListener(this::onMove);
        boardPanel = new ChessBoardGUI(board);
        add(boardPanel, BorderLayout.CENTER);
        historyPanel.update(board.getHistory());
        revalidate();
        repaint();
    }

    /**
     * Called after every move: updates the history panel and checks for
     * the end of the game.
     *
     * @param move the move that was just made
     */
    private void onMove(MoveRecord move) {
        historyPanel.update(board.getHistory());
        if (move.capturedKing()) {
            // Let the board finish redrawing first, so the King is shown as gone
            // behind the pop-up.
            SwingUtilities.invokeLater(() -> declareWinner(move));
        }
    }

    /**
     * Part 5: shows a pop-up naming the winner, then ends the game by
     * closing the program.
     *
     * @param move the move that captured the King
     */
    private void declareWinner(MoveRecord move) {
        String winner = MoveRecord.colorName(move.getPieceColor());
        String loser = MoveRecord.colorName(move.getCapturedColor());
        JOptionPane.showMessageDialog(this,
                winner + " captured the " + loser + " King!\n\n"
                        + winner + " wins the game.",
                "Game Over - " + winner + " Wins",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
        System.exit(0);
    }

    /**
     * Feature 1: starts a new game, putting every piece back in its
     * starting position and clearing the history.
     */
    private void newGame() {
        if (!board.getHistory().isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Start a new game? The current game will be lost unless you saved it.",
                    "New Game", JOptionPane.OK_CANCEL_OPTION);
            if (choice != JOptionPane.OK_OPTION) {
                return;
            }
        }
        startGame(new TrackedBoard());
    }

    /**
     * Feature 3: takes back the last move, putting back any piece it captured.
     * The board is rebuilt by replaying every move except the last one.
     */
    private void undo() {
        List<int[]> moves = board.getMoveList();
        if (moves.isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        moves.remove(moves.size() - 1);
        startGame(TrackedBoard.replay(moves));
    }

    /**
     * Feature 1: saves the game to a text file, one move per line (e.g. "e2 e4").
     */
    private void saveGame() {
        fileChooser.setSelectedFile(new File("my-game.chess"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();
        if (!file.getName().contains(".")) {
            file = new File(file.getPath() + ".chess");
        }

        List<String> lines = new ArrayList<>();
        lines.add(SAVE_HEADER);
        for (MoveRecord move : board.getHistory()) {
            lines.add(move.toSaveString());
        }
        try {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(this,
                    "Game saved (" + board.getHistory().size() + " moves) to:\n" + file,
                    "Save Game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showError("Could not save the game:\n" + ex.getMessage());
        }
    }

    /**
     * Feature 1: loads a saved game and restores the board exactly as it was.
     */
    private void loadGame() {
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();
        try {
            List<int[]> moves = readSaveFile(file);
            startGame(TrackedBoard.replay(moves));
            if (board.isGameOver()) {
                showError("This saved game had already ended. Starting a new game instead.");
                startGame(new TrackedBoard());
            }
        } catch (IOException ex) {
            showError("Could not read the file:\n" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showError("This is not a valid saved game:\n" + ex.getMessage());
        }
    }

    /**
     * Reads the moves from a save file.
     *
     * @param file the file to read
     * @return the moves as {fromRank, fromFile, toRank, toFile} arrays
     * @throws IOException              if the file cannot be read
     * @throws IllegalArgumentException if the file is not in the save format
     */
    private static List<int[]> readSaveFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        if (lines.isEmpty() || !lines.get(0).trim().equals(SAVE_HEADER)) {
            throw new IllegalArgumentException("Missing \"" + SAVE_HEADER + "\" header.");
        }
        List<int[]> moves = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim().toLowerCase();
            if (line.isEmpty()) {
                continue;
            }
            if (!line.matches("[a-h][1-8] [a-h][1-8]")) {
                throw new IllegalArgumentException("Line " + (i + 1) + ": \"" + line + "\"");
            }
            moves.add(new int[] {
                '8' - line.charAt(1), line.charAt(0) - 'a',
                '8' - line.charAt(4), line.charAt(3) - 'a'
            });
        }
        return moves;
    }

    /**
     * Shows an error message.
     *
     * @param message the message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Opens the Phase 2 chess game window.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGameFrame().setVisible(true));
    }
}
