package Model;

import java.util.List;

public interface Data_Import_Interface {
    /**
     * The method read_file() should read a file, and output the file data as a list of strings.
     * @return An array list of strings
     */
    List<String> read_file();

    /***
     * The method parse_node() will take in an array list of strings that represent nodes and edges
     * and will parse it into a list of nodes.
     * @param data ArrayList of strings which represents nodes and edges.
     * @return ArrayList of nodes.
     */
    List<Node> parse_node (List<String> data);

    /**
     * The method parse_edge() will take in an array list of strings that represent nodes and edges,
     * and will parse it into a list of edges.
     * @param data ArrayList of strings which represents nodes and edges.
     * @return ArrayList of edges.
     */
    List<Edge> parse_edge (List<String> data);

}
