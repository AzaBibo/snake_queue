
public class Pointer {
    private int x;
    private int y;
    private int steps;
    private int[][] desk;

    public Pointer(int y, int x, int steps, int[][] desk) {
        this.x = x;
        this.y = y;
        this.steps = steps;
        this.desk = desk;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSteps() { return steps; }
    public int[][] getDesk() { return desk; }
}
