package moves;

public class MoveRule {
    private int x, y;
    private int numberOfTimes;
    public MoveRule(int x, int y, int numberOfTimes) {
        this.x = x;
        this.y = y;
        this.numberOfTimes = numberOfTimes;
    }
    public int getNumberOfTimes() {
        return this.numberOfTimes;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public String toString() {
        return "x = " + x + " " + "y" + "= " + y;
    }

    @Override
    public boolean equals(Object move) {

        if (move instanceof MoveRule test)  {
            return test.getX() == this.x && test.getY() == this.y;
        }
        return false;
    }
}
