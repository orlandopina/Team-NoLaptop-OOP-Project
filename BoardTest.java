/**
 * Performs basic manual checks of the repaired board.
 */
public class BoardTest {

    /**
     * Displays the board and prints actual versus expected results.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();

        board.printBoard();

        System.out.println("E2 piece: " + board.getPiece(6, 4)
                + " (expected wp)");
        System.out.println("E7 piece: " + board.getPiece(1, 4)
                + " (expected bp)");

        System.out.println("Blocked rook A1-A4: "
                + board.movePiece(7, 0, 4, 0)
                + " (expected false)");

        System.out.println("White pawn E2-E4: "
                + board.movePiece(6, 4, 4, 4)
                + " (expected true)");

        System.out.println("E2 now empty: "
                + (board.getPiece(6, 4) == null)
                + " (expected true)");

        System.out.println("E4 piece: " + board.getPiece(4, 4)
                + " (expected wp)");

        System.out.println("Black pawn D7-D5: "
                + board.movePiece(1, 3, 3, 3)
                + " (expected true)");

        System.out.println("White pawn captures E4-D5: "
                + board.movePiece(4, 4, 3, 3)
                + " (expected true)");

        board.printBoard();
    }
}