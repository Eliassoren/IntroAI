/**
 * Created by EliasBrattli on 05/10/2016.
 */
public class Prev {
    public int dist; // distance / weight
    public int time; // time nodeTo drive
    public int pri; // priority
    public Node prevNode;
    public int maxint = Integer.MAX_VALUE/3;
    public Prev(){
        dist = maxint;
    }
}
