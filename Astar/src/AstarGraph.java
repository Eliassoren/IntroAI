import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 */
public class AstarGraph {
    static ArrayList<Node> nodes;
    static ArrayList<Node> closedSet = new ArrayList<>();
    static Queue<Node> nodeQueue;
    static Node xstartNode;
    static Node xgoalNode;
    static int checkedCount;
    static boolean drawOpenSet;
    static boolean drawClosedSet;
   // static int startVal = 0;
   // static int goalVal;
    static int NODE_AMOUNT = 0, EDGE_AMOUNT = 0;


    private static String getPath(String board) {
        switch (board) {
            case "1-1": return "/data/boards/board-1-1.txt";
            case "1-2": return "/data/boards/board-1-2.txt";
            case "1-3": return "/data/boards/board-1-3.txt";
            case "1-4": return "/data/boards/board-1-4.txt";
            case "2-1": return "/data/boards/board-2-1.txt";
            case "2-2": return "/data/boards/board-2-2.txt";
            case "2-3": return "/data/boards/board-2-3.txt";
            case "2-4": return "/data/boards/board-2-4.txt";
            default: return "/data/boards/board-1-1.txt";
        }
    }
    // Finds manhattan distance between two nodes
    static int calcDistance (Node thisNode, Node goal) {
        return 5*Math.abs(goal.position.x-thisNode.position.x) +
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


    static void astar(Node startNode, Node goalNode, String algorithm){
        boolean dijkstra = algorithm.equals("d");
        boolean bfs = algorithm.equals("bfs");
        nodeQueue =  bfs ? new LinkedList<>() :  new PriorityQueue<>(); // BFS uses a stack, an open queue
        startNode.weight = 0;
        startNode.pathCost = 0;
        startNode.checked = true;
        nodeQueue.add(startNode);

        while (!nodeQueue.isEmpty()) {
            Node curr = nodeQueue.poll();
            checkedCount++;
            if(curr.id == goalNode.id) {
                reconstructPath(startNode,goalNode);
            }
            for(Node neighbor : findNeighbors(curr)) {
                if(curr.prevNode != null && neighbor != curr.prevNode || curr == startNode) {
                    // Check for each neighbor which is not the node we came from
                    updateCost(curr, neighbor, goalNode, dijkstra, bfs);
                }
            }
        }
    }
    static void reconstructPath(Node startNode, Node goalNode){
        System.out.println("Reconstructing path");
        System.out.println("Path: (, open nodes: 0, closed nodes: X");
        System.out.println("-----------------------------------------------------------------");
        Node n = goalNode;
        ArrayList<Node> path = new ArrayList<>();

        while (n != startNode && path.size() < 40) {
            path.add(n);
            n = n.prevNode;
        }

        for (int k = 0; k < BoardReader.idGrid[0].length; k++) {
            for (int j = 0; j < BoardReader.idGrid.length; j++) {
                Node node = nodes.get(BoardReader.idGrid[j][k]);
                String cell = node.cellData;
                if(nodeQueue.contains(node) && drawOpenSet) cell = "0";
                if(closedSet.contains(node) && drawClosedSet) cell = "X";
                if(path.contains(node)) cell = "(";
                if(node.id == startNode.id) cell = "A";
                if(node.id == goalNode.id) cell = "B";
                System.out.print(cell+ " ");
            }
            System.out.println();
        }
        System.out.println("Checked nodes: "+ checkedCount);
        System.out.println("Cost: "+(goalNode.pathCost));
    }



    public static void main(String[] args){
       try{
           // To run, click on the green "start" button in IDE
           // or javac AstarGraph.java && java AstarGraph in terminal
           String boardFilePath = getPath("2-1"); // 1-2 for board 1-2, write 2-1 for board 2-1 and so on.

           nodes = BoardReader.generateNodes(boardFilePath);
           drawClosedSet = false; // True for task 3
           drawOpenSet = false; // True for task 3
           BoardReader.printInitialBoards(nodes);
           String algorithm = ""; // bfs: bfs, dijkstra: d, A*: empty
           astar(BoardReader.startNode, BoardReader.goalNode, algorithm);
        } catch(Exception e){
           e.printStackTrace();
       }

    }
}
