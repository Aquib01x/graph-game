package Model;

import java.util.List;


public interface Graph_ADT {

    /**
     * The method get_Degree() will return the degree of the specific node.
     * @param node The node you are looking for.
     * @return The degree of the node.
     */
    int get_Degree(Node node);

    /**
     * The method get_Adjacent() will return a list of nodes adjacent to the one provided.
     * @param node The node you are looking for.
     * @return The list of adjacent nodes
     */
    List<Node> get_Adjacent(Node node);

    /** The method is_Adjacent_To() will determine of two nodes are adjacent (i.e.: if there is an edge between them or not).
     * @param node_1 The first node to look for
     * @param node_2 The second node to look for
     * @return Boolean, true if both nodes are adjacent, false if they aren't
     */
    Boolean is_Adjacent_To(Node node_1, Node node_2);

    /**
     * The method get_Agenda() will return the agenda list.
     * @return The agenda list.
     */
    List<Node> get_Agenda();

    /**
     * The method get_Visited() will return the visited list.
     * @return The visited list.
     */
    List<Node> get_Visited();

    /***
     * The method add_to_agenda() will take a node as input and add it to the ArrayList agenda.
     * @param node
     */
    void add_to_agenda(Node node);

    /***
     * The method visit_node() will take a node as input and add it to the ArrayList visited.
     * @param node
     */
    void visit_node(Node node);

    /**
     * The method find_edge() will return an edge connecting two nodes.
     * @param node_1 The first connected node.
     * @param node_2 The second connected node.
     * @return The edge connecting the nodes. Returns null if the edge was not found.
     */
    Edge find_edge(Node node_1, Node node_2);

    /**
     * The method shortest_distance() will return the shortest distance between two nodes.
     * @param node_1 Start node.
     * @param node_2 Goal node.
     * @return shortest distance of the path.
     */
    int shortest_distance(Node node_1, Node node_2);

    /**
     * The method shortest_path will return a list of nodes containing the shortest path between two given nodes.
     * @param node_1 Start node.
     * @param node_2 Goal node.
     * @return A list of nodes containing the shortest path between the two nodes.
     */
    List<Node> shortest_path(Node node_1, Node node_2);

    /***
     * The method random_nodes will take as input a list of parsed unique nodes and return two random nodes from that list.
     * @param nodes Parsed list of nodes.
     * @return A list containing two random nodes.
     */
    List<Node> generate_random_nodes(List<Node> nodes);
}
