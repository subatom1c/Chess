package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class Queen extends Piece {

    // Constructor
    public Queen(int color) {
        super(false, false, color);

        // Setting capturing moves directions
        // directions: left, right, up, down, diagonals;
        List<MoveRule> moves = new ArrayList<>(List.of(
                new MoveRule(-1, 1, 8),
                new MoveRule(1, 1, 8),
                new MoveRule(-1, -1, 8),
                new MoveRule(1, -1, 8),
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
            return "\u2655"; // unicode for white queen
        } else if (color == WHITE) {
            return "\u265B";
        }
        return "";
    }
}
