package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public class Pawn extends Piece {

    public Pawn(int color) {
        super(color);

        // Setting capturing moves directions
        Move leftDiagonal, rightDiagonal;

        if (color == WHITE) {
            leftDiagonal = new Move(-1, 1, 1);
            rightDiagonal = new Move(1, 1, 1);
        } else {
            leftDiagonal = new Move(-1, -1, 1);
            rightDiagonal = new Move(1, -1, 1);
        }
        setMovesCapture(new ArrayList<>(List.of(leftDiagonal, rightDiagonal)));

        // Setting moves directions
        Move forward;

        if (color == WHITE) {
            forward = new Move(0, 1, 2);
        } else {
            forward = new Move(0, -1, 2);
        }
        setMoves(new ArrayList<>(List.of(forward)));
    }

    @Override
    public void moves() {
        setHasMoved(true);
        // Update the moves
        Move forward;
        if (this.color == WHITE) {
            forward = new Move(0, 1, 1);
        } else {
            forward = new Move(0, -1, 1);
        }
        
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
