package Model;

import java.util.Objects;

public class Node implements Node_Interface {

    // Identification of the node
    private final int node_ID;
    public Node(int node_ID){
        this.node_ID = node_ID;
    }

    /** get_ID() will return the ID of the node
     *
     * @return ID of the node
     */
    public int get_ID(){
        return node_ID;
    }

    /** equals() will compare two nodes to determine of they are equal. This is an override of the default equals(), so
     * that it can look at all the components of a node separately to determine equality.
     *
     * @param object The node you want to compare with
     * @return true if both nodes have the same information inside (node ID)
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }

        if (object == null){
            return false;
        }

        if (object instanceof Node test){

            return test.get_ID() == this.get_ID();
        }
        return false;
    }

    /**
     * Returns a hash code value for the node.<br><br>
     * The general contract of hashCode is:<br>
     * If two nodes are equal according to the equals() method, then calling the hashCode method on each of the two
     * nodes must produce the same integer result.
     *
     * @return a hash code value for this node.
     */
    @Override
    public int hashCode(){
        int hash = 19;
        hash = 31 * hash + Objects.hashCode(this.get_ID());
        return hash;
    }
}
