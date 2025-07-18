package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class Bishop extends Piece {

    // Constructor
    public Bishop(int color) {
        super(false, false, color);

        // Setting capturing moves directions
        // directions: leftUpDiagonal, rightUpDiagonal, leftDownDiagonal, leftUpDiagonal
        List<MoveRule> moves = new ArrayList<>(List.of(
            new MoveRule(-1, 1, 8),
            new MoveRule(1, 1, 8),
            new MoveRule(-1, -1, 8),
            new MoveRule(1, -1, 8)
        ));
        setMovesCapture(moves);
        setMoves(moves);
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            return "\u2657"; // unicode for white bishop
        } else if (color == WHITE) {
            return "\u265D";
        }
        return "";
    }
}
