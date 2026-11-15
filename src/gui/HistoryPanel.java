package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Phase 2, Part 6 (Extra GUI Feature 3): side panel with the game history.
 *
 * Shows the numbered list of moves, the pieces each player has captured,
 * and an Undo button that takes back the last move.
 */
public class HistoryPanel extends JPanel {

    /** Unicode symbols for white pieces, by piece type. */
    private static final String WHITE_SYMBOLS = "♔♕♖♗♘♙";

    /** Unicode symbols for black pieces, in the same order as WHITE_SYMBOLS. */
    private static final String BLACK_SYMBOLS = "♚♛♜♝♞♟";

    /** Piece type names, in the same order as the symbol strings. */
    private static final String[] PIECE_NAMES = {"King", "Queen", "Rook", "Bishop", "Knight", "Pawn"};

    /** Entries shown in the move list. */
    private final DefaultListModel<String> moveListModel = new DefaultListModel<>();

    /** The list of moves. */
    private final JList<String> moveList = new JList<>(moveListModel);

    /** Pieces White has captured (so these are Black pieces). */
    private final JLabel capturedByWhite = new JLabel(" ");

    /** Pieces Black has captured (so these are White pieces). */
    private final JLabel capturedByBlack = new JLabel(" ");

    /** Button that takes back the last move. */
    private final JButton undoButton = new JButton("Undo");

    /**
     * Creates the history panel.
     *
     * @param onUndo action to run when the Undo button is pressed
     */
    public HistoryPanel(Runnable onUndo) {
        super(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(320, 640));

        JLabel title = new JLabel("Move History");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        add(title, BorderLayout.NORTH);

        moveList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(moveList), BorderLayout.CENTER);

        Font symbolFont = new Font(findSymbolFont(), Font.PLAIN, 22);
        capturedByWhite.setFont(symbolFont);
        capturedByBlack.setFont(symbolFont);

        JPanel captured = new JPanel(new GridLayout(4, 1));
        captured.setBorder(BorderFactory.createTitledBorder("Captured Pieces"));
        captured.add(new JLabel("Captured by White:"));
        captured.add(capturedByWhite);
        captured.add(new JLabel("Captured by Black:"));
        captured.add(capturedByBlack);

        undoButton.addActionListener(e -> onUndo.run());
        undoButton.setAlignmentX(CENTER_ALIGNMENT);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(captured);
        bottom.add(undoButton);
        add(bottom, BorderLayout.SOUTH);

        update(List.of());
    }

    /**
     * Redraws the panel from the given move history.
     *
     * @param history the moves made so far, oldest first
     */
    public void update(List<MoveRecord> history) {
        moveListModel.clear();
        StringBuilder byWhite = new StringBuilder();
        StringBuilder byBlack = new StringBuilder();

        for (int i = 0; i < history.size(); i++) {
            MoveRecord move = history.get(i);
            moveListModel.addElement(String.format("%2d. %s", i + 1, move));
            if (move.getCapturedName() != null) {
                String symbol = symbolFor(move.getCapturedName(), move.getCapturedColor());
                (move.getPieceColor() == 0 ? byWhite : byBlack).append(symbol);
            }
        }

        capturedByWhite.setText(byWhite.length() == 0 ? " " : byWhite.toString());
        capturedByBlack.setText(byBlack.length() == 0 ? " " : byBlack.toString());
        undoButton.setEnabled(!history.isEmpty());
        if (!history.isEmpty()) {
            moveList.ensureIndexIsVisible(history.size() - 1);
        }
    }

    /**
     * Returns the Unicode symbol for a piece.
     *
     * @param pieceName piece type, e.g. "Knight"
     * @param color     0 for White, 1 for Black
     * @return the chess symbol
     */
    private static String symbolFor(String pieceName, int color) {
        for (int i = 0; i < PIECE_NAMES.length; i++) {
            if (PIECE_NAMES[i].equals(pieceName)) {
                String symbols = color == 0 ? WHITE_SYMBOLS : BLACK_SYMBOLS;
                return String.valueOf(symbols.charAt(i));
            }
        }
        return "?";
    }

    /**
     * Finds an installed font that can display chess symbols.
     *
     * @return the font name
     */
    private static String findSymbolFont() {
        String[] candidates = {"Segoe UI Symbol", "DejaVu Sans", "Arial Unicode MS",
                "Noto Sans Symbols2", "Apple Symbols"};
        for (String name : candidates) {
            Font font = new Font(name, Font.PLAIN, 12);
            if (font.getFamily().equals(name) && font.canDisplay('♚')) {
                return name;
            }
        }
        return Font.DIALOG;
    }
}
