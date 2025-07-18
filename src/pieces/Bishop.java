package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class Bishop extends Piece {

    public Bishop(int color) {
        super(color);

        // Setting capturing moves directions
        // directions: leftUpDiagonal, rightUpDiagonal, leftDownDiagonal, leftUpDiagonal
        List<Move> moves = new ArrayList<>(List.of(
            new Move(-1, 1, 8),
            new Move(1, 1, 8),
            new Move(-1, -1, 8),
            new Move(1, -1, 8)
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
