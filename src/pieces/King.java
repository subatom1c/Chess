package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class King extends Piece {

    public King(int color) {
        super(color);

        // Setting capturing moves directions
        // directions: left, right, up, down, diagonals;
        List<Move> moves = new ArrayList<>(List.of(
                new Move(-1, 1, 1),
                new Move(1, 1, 1),
                new Move(-1, -1, 1),
                new Move(1, -1, 1),
                new Move(1,0,1),
                new Move(-1, 0, 1),
                new Move(0, 1, 1),
                new Move(0, -1, 1)
        ));
        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            return "\u2654"; // unicode for white king
        } else if (color == WHITE) {
            return "\u265A";
        }
        return "";
    }
}
