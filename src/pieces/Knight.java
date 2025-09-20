package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class Knight extends Piece {

    public Knight(int color) {
        super(color);

        // Setting capturing moves directions
        List<Move> moves = new ArrayList<>(List.of(
                new Move(-1, 2, 1),
                new Move(-1, -2, 1),
                new Move(1, 2, 1),
                new Move(1, -2, 1),
                new Move(-2, 1, 1),
                new Move(-2, -1, 1),
                new Move(2, 1, 1),
                new Move(2, -1, 1)
        ));

        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            if (deformed) {
                return "\u001B[90mH\u001B[0m ";
            }
            return "\u2658"; // unicode for white knight
        } else if (color == WHITE) {
            if (deformed) {
                return "H ";
            }
            return "\u265E";
        }
        return "";
    }
}
