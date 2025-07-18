package board;

public class Square {
    private final char letter;
    private final int number;
    
    public Square (char letter, int number) {
            this.letter = letter;
            this.number = number;
    }

    public Square (int letter, int number) {
        this.letter = (char)(letter + 'a');
        this.number = number + 1;
    }

    public int getX() {
        // convert from letter into index
        return (int)(this.letter) - 'a';
    }
    public int getY() {
        return this.number - 1;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return "(" + this.letter + "," + this.number + ")";
    }

    public Square normalize() {
        int dx = Integer.compare(this.getX(), 0); // -1, 0, or 1
        int dy = Integer.compare(this.getY(), 0);
        return new Square(dx, dy);
    }

    public Square invert(){
        return new Square(this.getX()*-1, this.getY()*-1);
    }

    public Square subtract(Square square) {
        return new Square(this.getX() - square.getX(), this.getY() - square.getY());
    }

    public boolean isDiagonalHit(Square square) {
        return Math.abs(square.getX() - this.getX()) == Math.abs(square.getY() - this.getY());
    }

    public boolean isVerticalHit(Square square) {
        return this.getX() == square.getX();
    }

    public boolean isHorizontalHit(Square square) {
        return this.getY() == square.getY();
    }
    @Override
    public boolean equals(Object square) {
        if (square instanceof Square test)  {
            return test.getLetter() == this.letter && test.getNumber() == this.number;
        }
        return false;
    }
}
