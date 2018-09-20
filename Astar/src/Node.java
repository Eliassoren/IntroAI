/**
 * Datatype representing a cell in the 2d grid.
 */
public class Node implements Comparable<Node> {
    int id;
    boolean checked;
    String cellData; // The input from text file, used to draw board
    Node prevNode; // Reference to previous node in path, built as a linked list
    int pri; // The priority of a node, used in priority queue
    int pathCost; // The cost of traveling to this node
    Position position; // The x and y coordinates on a 2d grid
    int weight; // The weight of the node
    Node(){
        pathCost = Integer.MAX_VALUE/100; // Representing an infinite cost before evaluation
        checked = false; // Used to control whether the node has been evaluated before
    }
    // Compare priorities of paths
    @Override
    public int compareTo(Node node){
        return pri-node.pri;
    }
}
