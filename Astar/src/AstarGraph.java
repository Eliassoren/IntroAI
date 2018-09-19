import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class AstarGraph {
    static ArrayList<Node> nodes;
    static ArrayList<Node> closedSet = new ArrayList<>();
    static Queue<Node> nodeQueue;
    static Node xstartNode;
    static Node xgoalNode;
    static int checkedCount;
   // static int startVal = 0;
   // static int goalVal;
    static int NODE_AMOUNT = 0, EDGE_AMOUNT = 0;



    // Finds manhattan distance between two nodes
    static int calcDistance (Node thisNode, Node goal) {
        return Math.abs(goal.position.x-thisNode.position.x) +
                Math.abs(goal.position.y-thisNode.position.y);
    }
    // Used nodeTo update priorities.
    static void updateCost(Node node, Node nodeTo, Node goal, boolean dijkstra, boolean bfs){

        if(nodeTo.pathCost > node.pathCost + nodeTo.weight){
            nodeTo.pathCost = node.pathCost + nodeTo.weight;
            nodeTo.prevNode = node;
            nodeTo.pri = node.pathCost + (!bfs? nodeTo.weight:0) + (!dijkstra && !bfs? calcDistance(nodeTo, goal) : 0);
            // If we've already found the node, remove and add it again nodeTo see if we will change its priority.
            if(nodeTo.checked) {
                nodeQueue.remove(nodeTo);
                nodeQueue.add(nodeTo);
            }
            else{
                // We've discovered a new node, add it nodeTo find priority.
                nodeQueue.add(nodeTo);
                nodeTo.checked = true;
            }
        } else {
            closedSet.add(nodeTo);
        }
    }
    // Can find up to four neighbors as moving directions are north, south, east, west
    static ArrayList<Node> findNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[][] idGrid = BoardReader.idGrid;
        int col = n.position.x;
        int row = n.position.y;
        if(col > 0) neighbors.add(nodes.get(idGrid[col - 1][row]));
        if(col + 1 < idGrid.length) neighbors.add(nodes.get(idGrid[col + 1][row]));
        if(row > 0) neighbors.add(nodes.get(idGrid[col][row - 1]));
        if(row + 1 < idGrid[0].length) neighbors.add(nodes.get(idGrid[col][row + 1]));

        return neighbors;
    }


    static void astar(Node startNode, Node goalNode, int width, int height, String dijkstraOrBfs){
        boolean dijkstra = dijkstraOrBfs.equals("dijkstra");
        boolean bfs = dijkstraOrBfs.equals("bfs");
        nodeQueue =  !bfs ? new PriorityQueue<>() : new LinkedList<>();
        startNode.weight = 0;
        startNode.pathCost = 0;
        startNode.checked = true;
        nodeQueue.add(startNode);


        while (!nodeQueue.isEmpty()){
            Node curr = nodeQueue.poll();
            checkedCount++;
            if(curr.id == goalNode.id)reconstructPath(startNode,goalNode);
            ArrayList<Node> neighbors = findNeighbors(curr);
            for(Node neighbor : neighbors) {
                if(curr.prevNode != null && neighbor.id != curr.prevNode.id || curr.id == startNode.id)
                updateCost(curr, neighbor, goalNode, dijkstra, bfs);
            }
        }

    }
    static void reconstructPath(Node startNode, Node goalNode){
        System.out.println("Reconstructing path");
        System.out.println("Path: (, open nodes: 0, closed nodes: X");
        System.out.println("-----------------------------------------------------------------");
        Node n = goalNode;
        ArrayList<Node> path = new ArrayList<>();

        while (n.id != startNode.id && path.size() < 40) {
            path.add(n);
            n = n.prevNode;
        }

        for (int k = 0; k < BoardReader.idGrid[0].length; k++) {
            for (int j = 0; j < BoardReader.idGrid.length; j++) {
                Node node = nodes.get(BoardReader.idGrid[j][k]);
                String cell = node.cellData;
                if(nodeQueue.contains(node)) cell = "0";
                if(closedSet.contains(node)) cell = "X";
                if(path.contains(node)) cell = "(";
                if(node.id == startNode.id) cell = "A";
                if(node.id == goalNode.id) cell = "B";
                System.out.print(cell+ " ");
            }
            System.out.println();
        }
        System.out.println(startNode.position.x+","+startNode.position.y);
        System.out.println("Checked nodes: "+ checkedCount);
        System.out.println("Cost: "+(goalNode.pathCost));
    }

    private static void printInitialBoards() {
        System.out.println("Startnode "+BoardReader.startNode.id);
        System.out.println("Goalnode "+BoardReader.goalNode.id);
        System.out.println("--------ID GRID ----------------");
        String padding;
        for (int k = 0; k < BoardReader.idGrid[0].length; k++) {
            for (int j = 0; j < BoardReader.idGrid.length; j++) {
                int id = BoardReader.idGrid[j][k];
                if(id < 10) padding = "   ";
                else if(id < 100) padding = "  ";
                else padding = " ";
                System.out.print(padding+id);
            }
            System.out.println();
        }
        System.out.println("-------------INITIAL BOARD-------------");
        for (int k = 0; k < BoardReader.idGrid[0].length; k++) {
            for (int j = 0; j < BoardReader.idGrid.length; j++) {

                System.out.print(nodes.get(BoardReader.idGrid[j][k]).cellData+" ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }

    public static void main(String[] args){
       try{
           // Uncomment a file path to test it
         // String path = "/data/boards/board-1-1.txt";
          //String path = "/data/boards/board-1-2.txt";
       //   String path = "/data/boards/board-1-3.txt";
          //String path = "/data/boards/board-1-4.txt";
        String path = "/data/boards/board-2-1.txt";
     // String path = "/data/boards/board-2-2.txt";
//          String path = "/data/boards/board-2-3.txt";
//          String path = "/data/boards/board-2-4.txt";
           nodes = BoardReader.generateNodes(path);

           printInitialBoards();
           String dijkstraOrBfs = "dijkstra"; // bfs: bfs, dijkstra: dijkstra, astar: empty
           astar(BoardReader.startNode, BoardReader.goalNode, BoardReader.width, BoardReader.height, dijkstraOrBfs);
            //calculateRuntime(0,25);
        } catch(Exception ne){
           ne.printStackTrace();
       }

    }
}
