package pieces;

import java.util.List;
import java.util.ArrayList;
import board.Move;

public abstract class Piece {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    protected List<Move> movesCapture = new ArrayList<>();
    protected List<Move> moves = new ArrayList<>();
    protected boolean hasMoved = false;
    protected int color; // -1 color simbolizes emptiness
    protected boolean deformed = false;
    public static final String BRIGHT_BLACK = "\u001B"; // better visibility

    public Piece(int color){this.color = color;}

    public boolean hasDirection(Move testingMove) {
        for (Move move: this.moves) {
            if (move.equals(testingMove)) {
                return true;
            }
        }
        for (Move move: this.movesCapture) {
            if (move.equals(testingMove)) {
                return true;
            }
        }
        return false;
    }

    public void moves() {
        hasMoved = true;
    }

    public boolean isEmpty() {
        return color == -1;
    }

    // Getters and Setters
    public List<Move> getMovesCapture() {
        return movesCapture;
    }

    public void setMovesCapture(List<Move> movesCapture) {
        this.movesCapture = movesCapture;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {this.hasMoved = hasMoved;}

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void isDeformed(){this.deformed = true;}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
