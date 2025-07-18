package moves;

public class Move {
    private int x, y;
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object move) {

        if (move instanceof Move test)  {
            return test.getX() == this.x && test.getY() == this.y;
        }
        return false;
    }
}
