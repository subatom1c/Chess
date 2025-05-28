package pieces;

import board.Board;

import java.util.ArrayList;

public class Pawn extends Piece {

    private int x,y;

    public Pawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean move(Board board, int x, int y) {
        
    }

    @Override
    public ArrayList<int[]> getPossibleMoves(Board board) {
        // Pawn is in the first rank
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        if (board.)
    }

}
