package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public abstract class Piece {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    // atributtes
    protected List<MoveRule> movesCapture = new ArrayList<>();
    protected List<MoveRule> moves = new ArrayList<>();
    protected boolean jumpOverPieces;
    protected boolean hasMoved = false;
    protected int color; // -1 color simbolizes emptiness

    public Piece(int color){this.color = color;}

    public Piece (boolean jumpOverPieces, boolean hasMoved, int color) {
        this.jumpOverPieces = jumpOverPieces;
        this.hasMoved = hasMoved;
        this.color = color;
    }

    public void moves() {
        hasMoved = true;
    }

    public boolean isEmpty() {
        return color == -1;
    }

    // Getters and Setters
    public List<MoveRule> getMovesCapture() {
        return movesCapture;
    }

    public void setMovesCapture(List<MoveRule> movesCapture) {
        this.movesCapture = movesCapture;
    }

    public List<MoveRule> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveRule> moves) {
        this.moves = moves;
    }

    public boolean canJumpOverPieces() {
        return jumpOverPieces;
    }

    public void setJumpOverPieces(boolean jumpOverPieces) {
        this.jumpOverPieces = jumpOverPieces;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
