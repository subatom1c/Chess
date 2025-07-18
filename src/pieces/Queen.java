package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class Queen extends Piece {

    public Queen(int color) {
        super(color);

        // Setting capturing moves directions
        // directions: left, right, up, down, diagonals;
        List<Move> moves = new ArrayList<>(List.of(
                new Move(-1, 1, 8),
                new Move(1, 1, 8),
                new Move(-1, -1, 8),
                new Move(1, -1, 8),
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
            return "\u2655"; // unicode for white queen
        } else if (color == WHITE) {
            return "\u265B";
        }
        return "";
    }
}
