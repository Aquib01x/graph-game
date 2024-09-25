package Controller;

import org.jgrapht.graph.DefaultWeightedEdge;

/***
 * This class is used by Generate_Graph_Image to modify the edge weight in the jgraph.
 */
public class Weighted_Edge extends DefaultWeightedEdge {
    @Override
    public String toString() {
        return Integer.toString((int) getWeight());
    }
}
