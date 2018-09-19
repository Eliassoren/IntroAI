/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class WeightedEdge{
    int weight;
    WeightedEdge nextEdge;
    Node nodeTo;
    public WeightedEdge(WeightedEdge edge, Node node, int w){
        nextEdge = edge;
        nodeTo = node;
        weight = w;
    }
}

