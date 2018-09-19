/**
 * Created by EliasBrattli on 05/10/2016.
 */
public class Node implements Comparable<Node>{
    public int id;
    public boolean checked;
    String cellData;
    Node prevNode;
    int pri;
    int pathCost;
    public Position position;
    public int weight;

    public Node(){
        pathCost = Integer.MAX_VALUE/100;
        checked = false;
    }
    // Compare priorities of paths
    @Override
    public int compareTo(Node node){
        return pri-node.pri;
    }
}
