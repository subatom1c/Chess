package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class Rook extends Piece {

    // Constructor
    public Rook(int color) {
        super(false, false, color);

        // Setting capturing moves directions
        // directions: left, right, up, down
        List<MoveRule> moves = new ArrayList<>(List.of(
                new MoveRule(1,0,8),
                new MoveRule(-1, 0, 8),
                new MoveRule(0, 1, 8),
                new MoveRule(0, -1, 8)
        ));

        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            return "\u2656"; // unicode for white bishop
        } else if (color == WHITE) {
            return "\u265C";
        }
        return "";
    }
}
