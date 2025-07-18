package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class Knight extends Piece {

    // Constructor
    public Knight(int color) {
        super(true, false, color);

        // Setting capturing moves directions
        List<MoveRule> moves = new ArrayList<>(List.of(
                new MoveRule(-1, 2, 1),
                new MoveRule(-1, -2, 1),
                new MoveRule(1, 2, 1),
                new MoveRule(1, -2, 1),
                new MoveRule(-2, 1, 1),
                new MoveRule(-2, -1, 1),
                new MoveRule(2, 1, 1),
                new MoveRule(2, -1, 1)
        ));

        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            return "\u2658"; // unicode for white knight
        } else if (color == WHITE) {
            return "\u265E";
        }
        return "";
    }
}
