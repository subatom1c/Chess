package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class Pawn extends Piece {

    // Constructor
    public Pawn(int color) {
        super(false, false, color);

        // Setting capturing moves directions
        MoveRule leftDiagonal, rightDiagonal;

        if (color == WHITE) {
            leftDiagonal = new MoveRule(-1, 1, 1);
            rightDiagonal = new MoveRule(1, 1, 1);
        } else {
            leftDiagonal = new MoveRule(-1, -1, 1);
            rightDiagonal = new MoveRule(1, -1, 1);
        }
        setMovesCapture(new ArrayList<>(List.of(leftDiagonal, rightDiagonal)));

        // Setting moves directions
        MoveRule forward;

        if (color == WHITE) {
            forward = new MoveRule(0, 1, 2);
        } else {
            forward = new MoveRule(0, -1, 2);
        }
        setMoves(new ArrayList<>(List.of(forward)));
    }

    @Override
    public void moves() {
        setHasMoved(true);
        // Update the moves
        MoveRule forward = new MoveRule(0, 1, 1);
        setMoves(new ArrayList<>(List.of(forward)));
    }

    @Override
    public String toString() {
        if (color == BLACK) {
            return "\u2659"; // unicode for white pawn
        } else if (color == WHITE) {
            return "\u265F";
        }
        return "";
    }
}
