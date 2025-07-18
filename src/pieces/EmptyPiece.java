package pieces;

import java.util.List;
import java.util.ArrayList;
import moves.MoveRule;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        super(-1);
    }
    @Override
    public String toString() {
        return "\u2008.\u2008";
    }
}
