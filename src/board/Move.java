package board;

public class Move extends Square {
    
    int numberOfTimes; // Number of times a piece can move in a certain direction

    public Move(int xDirection, int yDirection, int numberOfTimes) {
        super(xDirection, yDirection);
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public Move invert(){
        return new Move(this.getX()*-1, this.getY()*-1, this.getNumberOfTimes());
    }

    public int getNumberOfTimes(){return this.numberOfTimes;}

    @Override
    public String toString() {
        return "[" + super.getX() + "," + super.getY() + "]x" + numberOfTimes;
    }

    @Override
    public boolean equals(Object square) {
        if (square instanceof Move test)  {
            return test.getLetter() == super.getLetter() && test.getNumber() == super.getNumber() && test.getNumberOfTimes() == this.numberOfTimes;
        }
        return false;
    }

}