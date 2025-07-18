package players;

import board.Board;
import board.Square;

public class Player {
    private final int color;
    private final String name;

    public Player(int color, String name) {
        this.color = color;
        this.name = name;
    }

    public boolean makeMove(Board board, Square originSquare, Square targetSquare) {
        return board.move(new Square(originSquare.getLetter(), originSquare.getNumber()), new Square(targetSquare.getLetter(), targetSquare.getNumber()));
    }

    public String getName(){return this.name;}
    public int getColor(){return this.color;}

}
