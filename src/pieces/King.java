package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class King extends Piece {

    // Constructor
    public King(int color) {
        super(false, false, color);

        // Setting capturing moves directions
        // directions: left, right, up, down, diagonals;
        List<MoveRule> moves = new ArrayList<>(List.of(
                new MoveRule(-1, 1, 1),
                new MoveRule(1, 1, 1),
                new MoveRule(-1, -1, 1),
                new MoveRule(1, -1, 1),
                new MoveRule(1,0,1),
                new MoveRule(-1, 0, 1),
                new MoveRule(0, 1, 1),
                new MoveRule(0, -1, 1)
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
