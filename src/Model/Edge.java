package Model;

import java.util.Objects;

public class Edge implements Edge_Interface {

    // Identification number of the edge.
    private final int edge_ID;

    // First node connected to the edge
    private final int node_1;

    // Second node connected to the edge
    private final int node_2;

    // Cost to go from one node to another (e.g.: node 2 to node 1)
    private final int weight;


    /** An Edge is represented by four parameters. It is a representation of a cost to move from node 1 to node 2, which
     * is equal to the weight of the edge.
     * <pre>
     *  |node 1|          weight         |node 2|
     *  |      |                         |      |
     *  |      |                         |      |
     *     ___                             ___
     *    |   |              5            |   |
     *    | 1 |===========================| 2 |
     *    |___|                           |___|
     *  </pre>
     * @param edge_ID Identification number of the edge.
     * @param node_1 First node connected to the edge
     * @param node_2 Second node connected to the edge
     * @param weight Cost to go from one node to another (e.g.: node 2 to node 1)
     */
    public Edge (int edge_ID, int node_1, int node_2, int weight){
        this.edge_ID = edge_ID;
        this.node_1 = node_1;
        this.node_2 = node_2;
        this.weight = weight;
    }

    /** get_ID() will return the ID of the edge
     *
     * @return ID of the edge
     */
    public int get_ID() {
        return edge_ID;
    }

    /** get_Node_1() will return the ID of node 1
     *
     * @return ID of node 1
     */
    public int get_Node_1(){
        return node_1;
    }

    /** get_Node_2() will return the ID of node 2
     *
     * @return ID of node 2
     */
    public int get_Node_2(){
        return node_2;
    }

    /** get_Weight() will return the weight of the edge
     *
     * @return weight of edge
     */
    public int get_Weight(){
        return weight;
    }


    /** equals() will compare two edges to determine of they are equal. This is an override of the default equals(), so
     * that it can look at all the components of an edge separately to determine equality
     *
     * @param object The edge you want to compare with
     * @return true if both edges have the same information inside (edge ID, weight, node 1 ID and node 2 ID)
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }

        if (object == null){
            return false;
        }

        if (object instanceof Edge test){

            return test.get_ID() == this.get_ID()
                    && test.get_Node_1() == this.get_Node_1()
                    && test.get_Node_2() == this.get_Node_2()
                    && test.get_Weight() == this.get_Weight();
        }
        return false;
    }

    /**
     * Returns a hash code value for the edge.<br><br>
     * The general contract of hashCode is:<br>
     * If two edges are equal according to the equals() method, then calling the hashCode method on each of the two
     * edges must produce the same integer result.
     *
     * @return a hash code value for this edge.
     */
    @Override
    public int hashCode(){
        int hash = 19;
        hash = 31 * hash + Objects.hashCode(this.get_ID());
        hash = 31 * hash + Objects.hashCode(this.get_Node_1());
        hash = 31 * hash + Objects.hashCode(this.get_Node_2());
        hash = 31 * hash + Objects.hashCode(this.get_Weight());
        return hash;
    }
}
