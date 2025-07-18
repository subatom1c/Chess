package players;

import board.Board;
import moves.Move;

public class Player {
    private int color;
    private String name;

    public Player(int color, String name) {
        this.color = color;
        this.name = name;
    }

    public boolean makeMove(Board board, char oldLetter, int oldRow, char newLetter, int newRow) {
        int oldCol = (int)oldLetter - 97;
        oldRow--;
        int newCol = (int)newLetter - 97;
        newRow--;
        return board.move(new Move(oldCol, oldRow), new Move(newCol, newRow));
    }

    public String getName(){return this.name;}
    public int getColor(){return this.color;}

}
