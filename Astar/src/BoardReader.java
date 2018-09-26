import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class BoardReader {
    public static Node startNode;
    public static Node goalNode;
    static int[][] idGrid;
    static int width, height;
    static int minimumWeight;
    // Can generate a board from a grid
    public static ArrayList<Node> generateNodes(String path) {
        String filePath = new File("").getAbsolutePath().concat(path);
        System.out.println(filePath);
        String line;
        System.out.println(path);
        ArrayList<Node> nodes = new ArrayList<>(); // A flattened grid
        int y = 0, id = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            while((line = bufferedReader.readLine()) != null){
                String[] cells = line.split("");
                width = cells.length;

                int x=0;
                for(String cell : cells) {
                    Node n = getNode(cell, x++, y, id++);
                    if(cell.equals("A")){
                        startNode = n;
                    } else if(cell.equals("B")) {
                        goalNode = n;
                    }
                    nodes.add(n);
                }
                y++;
            }
            height = y;
            idGrid = new int[width][height]; // A grid of indexes in arraylist
            int i = 0, j = 0;
            for(Node n : nodes) {
                idGrid[i++][j] = n.id;
                if(i == width) {
                    i = 0;
                    j++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    private static Node getNode(String cell, int x, int y, int id) {
        Node node = new Node();
        node.cellData = cell;
        node.position = new Position(x, y);
        node.id = id;
        switch (cell) {
            case ".":
                node.weight = 1;
                minimumWeight = 1;
                break;
            case "#":
                node.weight = Integer.MAX_VALUE/10;
                break;
            case "w":
                node.weight = 100;
                break;
            case "m":
                node.weight = 50;
                break;
            case "f":
                node.weight = 10;
                break;
            case "g":
                minimumWeight = 5;
                node.weight = 5;
                break;
            case "r":
                node.weight = 1;
                break;
            case "A":
                node.weight = 0;

            case "B":
                node.weight = 0;
        }
        return node;
    }

    public static void printInitialBoards(ArrayList<Node> nodes) {
        System.out.println("Startnode "+startNode.id);
        System.out.println("Goalnode "+goalNode.id);
        System.out.println("--------ID GRID ----------------");
        String padding;
        for (int k = 0; k < idGrid[0].length; k++) {
            for (int j = 0; j < idGrid.length; j++) {
                int id = idGrid[j][k];
                if(id < 10) padding = "   ";
                else if(id < 100) padding = "  ";
                else padding = " ";
                System.out.print(padding+id);
            }
            System.out.println();
        }
        System.out.println("-------------INITIAL BOARD-------------");
        for (int k = 0; k < idGrid[0].length; k++) {
            for (int j = 0; j < idGrid.length; j++) {

                System.out.print(nodes.get(idGrid[j][k]).cellData+" ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }
}
