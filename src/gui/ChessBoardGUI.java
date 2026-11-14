package gui;

import board.Board;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * Swing GUI that displays an 8x8 chessboard with alternating light and dark
 * squares, and draws the pieces stored in a {@link Board} in their positions.
 */
public class ChessBoardGUI extends JPanel {

    /** Number of squares along each side of the board. */
    private static final int BOARD_SIZE = 8;

    /** Color of the light squares. */
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);

    /** Color of the dark squares. */
    private static final Color DARK_SQUARE = new Color(181, 136, 99);

    /** Fill color for white pieces. */
    private static final Color WHITE_PIECE = Color.WHITE;

    /** Fill color for black pieces. */
    private static final Color BLACK_PIECE = new Color(30, 30, 30);

    /** The board whose pieces are displayed. */
    private final Board board;

    /** Name of an installed font that can draw the chess symbols. */
    private final String pieceFontName;

    /**
     * Creates a GUI panel that displays the given board.
     *
     * @param board the board to display
     */
    public ChessBoardGUI(Board board) {
        this.board = board;
        this.pieceFontName = findChessFont();
        setPreferredSize(new Dimension(640, 640));
    }

    /**
     * Finds an installed font that can display the Unicode chess symbols.
     *
     * @return the font name
     */
    private static String findChessFont() {
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

    /**
     * Returns the Unicode chess symbol for a piece.
     *
     * @param piece the piece
     * @return the symbol representing the piece type
     */
    private static char symbolFor(Piece piece) {
        if (piece instanceof King) {
            return '♚';
        } else if (piece instanceof Queen) {
            return '♛';
        } else if (piece instanceof Rook) {
            return '♜';
        } else if (piece instanceof Bishop) {
            return '♝';
        } else if (piece instanceof Knight) {
            return '♞';
        }
        return '♟'; // Pawn
    }

    /**
     * Draws the squares, coordinate labels, and pieces.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int square = Math.min(getWidth(), getHeight()) / BOARD_SIZE;
        int offsetX = (getWidth() - square * BOARD_SIZE) / 2;
        int offsetY = (getHeight() - square * BOARD_SIZE) / 2;
        Font pieceFont = new Font(pieceFontName, Font.PLAIN, (int) (square * 0.8));
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, Math.max(10, square / 7));

        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                int x = offsetX + file * square;
                int y = offsetY + rank * square;
                boolean light = (rank + file) % 2 == 0;

                // Alternating light and dark squares.
                g2.setColor(light ? LIGHT_SQUARE : DARK_SQUARE);
                g2.fillRect(x, y, square, square);

                // Rank numbers on the left edge, file letters on the bottom edge.
                g2.setFont(labelFont);
                g2.setColor(light ? DARK_SQUARE : LIGHT_SQUARE);
                if (file == 0) {
                    g2.drawString(String.valueOf(BOARD_SIZE - rank), x + 3,
                            y + labelFont.getSize() + 2);
                }
                if (rank == BOARD_SIZE - 1) {
                    g2.drawString(String.valueOf((char) ('a' + file)),
                            x + square - labelFont.getSize(), y + square - 4);
                }

                Piece piece = board.getPiece(rank, file);
                if (piece != null) {
                    drawPiece(g2, piece, pieceFont, x, y, square);
                }
            }
        }
    }

    /**
     * Draws a piece centered in its square, filled with its side's color
     * and outlined so it stands out on both square colors.
     *
     * @param g2 the graphics context
     * @param piece the piece to draw
     * @param font the font used for the piece symbol
     * @param x left edge of the square
     * @param y top edge of the square
     * @param square size of the square in pixels
     */
    private void drawPiece(Graphics2D g2, Piece piece, Font font, int x, int y, int square) {
        GlyphVector glyph = font.createGlyphVector(g2.getFontRenderContext(),
                String.valueOf(symbolFor(piece)));
        Rectangle2D bounds = glyph.getVisualBounds();
        float drawX = (float) (x + (square - bounds.getWidth()) / 2 - bounds.getX());
        float drawY = (float) (y + (square - bounds.getHeight()) / 2 - bounds.getY());
        Shape shape = glyph.getOutline(drawX, drawY);

        boolean white = piece.getColor() == 0;
        g2.setColor(white ? WHITE_PIECE : BLACK_PIECE);
        g2.fill(shape);
        g2.setColor(white ? BLACK_PIECE : Color.GRAY);
        g2.setStroke(new BasicStroke(Math.max(1f, square / 40f)));
        g2.draw(shape);
    }

    /**
     * Opens a window showing the chessboard in its starting position.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChessBoardGUI(new Board()));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
