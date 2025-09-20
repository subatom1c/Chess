package pieces;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        super(-1);
    }
    @Override
    public String toString() {
        if (deformed) {
            return "- ";
        }
        return "\u2008-\u2008";
    }
}
