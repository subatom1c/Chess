package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class Rook extends Piece {

    public Rook(int color) {
        super(color);

        // Setting capturing moves directions
        // directions: left, right, up, down
        List<Move> moves = new ArrayList<>(List.of(
                new Move(1,0,8),
                new Move(-1, 0, 8),
                new Move(0, 1, 8),
                new Move(0, -1, 8)
        ));

        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            if (deformed) {
                return "\u001B[90mR\u001B[0m ";
            }
            return "\u2656"; // unicode for white bishop
        } else if (color == WHITE) {
            if (deformed) {
                return "R ";
            }
            return "\u265C";
        }
        return "";
    }
}
