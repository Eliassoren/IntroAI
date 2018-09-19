/**
 * Created by EliasBrattli on 15/11/2016.
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
