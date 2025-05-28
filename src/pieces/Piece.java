package pieces;

import board.Board;
import java.util.ArrayList;

public abstract class Piece {
    // Attemps to move a piece in the board
    public abstract boolean move(Board board, int x, int y);
    public abstract ArrayList<int[]> getPossibleMoves(Board board, int x, int y);
}
