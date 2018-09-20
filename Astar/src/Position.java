/**
 * Datatype for showing a position (x, y) on a 2d grid
 */
public class Position {
    int x;
    int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "Position: "+
                "X: " + x +
                " Y: " + y;
    }
}
