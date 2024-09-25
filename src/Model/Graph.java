package Model;

import java.util.*;


public class Graph implements Graph_ADT {
    List<Node> agenda = new ArrayList<>();
    List<Node> visited =new ArrayList<>();

    /**
     * The method get_Degree() will read the file, then look at every edge of the graph. If it finds the node provided
     * as a parameter, it will increase a degree variable, and at the end, return it.
     * @param node The node you are looking for.
     * @return The degree of the node.
     */
    public int get_Degree(Node node){
        Data_Import data_import = new Data_Import();
        List<Edge> edges = data_import.parse_edge(data_import.read_file());

        int degree = 0;

        for (Edge edge : edges){
            if(edge.get_Node_1() == node.get_ID() || edge.get_Node_2() == node.get_ID()){
                degree++;
            }
        }

        return degree;
    }

    /**
     * The method get_Adjacent() will read the file, then look at every edge of the graph. If it finds the node provided
     * it will add the opposite node to an array list, and return the list of adjacent nodes at the end.
     * @param node The node you are looking for.
     * @return The list of adjacent nodes
     */
    public List<Node> get_Adjacent(Node node){
        Data_Import data_import = new Data_Import();
        List<Edge> edges = data_import.parse_edge(data_import.read_file());
        List<Node> nodes = new ArrayList<>();

        for (Edge edge : edges){
            if(edge.get_Node_1() == node.get_ID()){
                nodes.add(new Node(edge.get_Node_2()));
            }
            else if(edge.get_Node_2() == node.get_ID()){
                nodes.add(new Node(edge.get_Node_1()));
            }
        }

        return nodes;
    }


    /** The method is_Adjacent_To() will read the file, then look at the edges. If it finds the two nodes in the same edge, it returns true.
     * Otherwise, it returns false.
     * @param node_1 The first node to look for
     * @param node_2 The second node to look for
     * @return Boolean, true if both nodes are adjacent, false if they aren't
     */
    public Boolean is_Adjacent_To(Node node_1, Node node_2){
        Data_Import data_import = new Data_Import();
        List<Edge> edges = data_import.parse_edge(data_import.read_file());

        for (Edge edge : edges){
            if((edge.get_Node_1() == node_1.get_ID() && edge.get_Node_2() == node_2.get_ID()) ||
               (edge.get_Node_1() == node_2.get_ID() && edge.get_Node_2() == node_1.get_ID())){
                return true;
            }
        }
        return false;
    }

    /**
     * The method get_Agenda() will return the agenda list.
     * @return The agenda list.
     */
    public List<Node> get_Agenda(){
        return agenda;
    }

    /**
     * The method get_Visited() will return the visited list.
     * @return The visited list.
     */
    public List<Node> get_Visited(){
        return visited;
    }

    /***
     * The method add_to_agenda() will take a node as input and add it to the ArrayList agenda.
     * @param node
     */
    public void add_to_agenda(Node node){
        agenda.add(node);
    }

    /***
     * The method visit_node() will take a node as input and add it to the ArrayList visited.
     * @param node
     */
    public void visit_node(Node node){
        visited.add(node);
    }


    /**
     * The method shortest_distance() will return the shortest distance between two nodes by using the result path from the
     * method shortest_path().
     * @param node_1 Start node.
     * @param node_2 Goal node.
     * @return shortest distance of the path.
     */
    public int shortest_distance(Node node_1, Node node_2){
        List<Node> path = shortest_path(node_1, node_2);

        int distance = 0;

        for (int counter = 0; counter + 1 < path.size(); counter++){
            distance += find_edge(path.get(counter), path.get(counter + 1)).get_Weight();
        }

        return distance;
    }


    /**
     * The method find_edge() will return an edge connecting two nodes.
     * @param node_1 The first connected node.
     * @param node_2 The second connected node.
     * @return The edge connecting the nodes. Returns null if the edge was not found.
     */
    public Edge find_edge(Node node_1, Node node_2){
        Data_Import data_import = new Data_Import();
        List<String> data = data_import.read_file();
        List<Edge> edge_list = data_import.parse_edge(data);

        for(Edge edge : edge_list){
            if ((edge.get_Node_1() == node_1.get_ID() && edge.get_Node_2() == node_2.get_ID()) ||
                (edge.get_Node_1() == node_2.get_ID() && edge.get_Node_2() == node_1.get_ID())){
                return edge;
            }
        }
        return null;
    }


    /**
     * The method shortest_path will return a list of nodes containing the shortest path between two given nodes.
     * @param node_1 Start node.
     * @param node_2 Goal node.
     * @return A list of nodes containing the shortest path between the two nodes.
     */
    public List<Node> shortest_path(Node node_1, Node node_2){

        // Import the nodes
        Data_Import data_import = new Data_Import();//creating Data-Import obejct
        List<String> data = data_import.read_file();//Declaring "data" parameter
        List<Node> unvisited = data_import.parse_node(data);

        // Create a Map to store the distances between nodes, and the parent nodes
        Map<Node, Pair<Node, Integer>> distances_from_start = new HashMap<>();
        // distances_from_start = key:   child node
        //                        value: (key:   parent node)
        //                               (value: distance   )

        // For each node that has not yet been visited, which is every node at the moment, set the recorded distance
        // between them and the start node to be infinity.
        for (Node node : unvisited){
            distances_from_start.put(node, new Pair<>(null, (int) Double.POSITIVE_INFINITY));
        }

        // Set the recorded distance of the start node to be 0.
        distances_from_start.replace(node_1, new Pair<>(null, 0));


        // While the unvisited list still has some unexplored nodes, keep looping.
        while (!unvisited.isEmpty()){

            // Initialise the current node to null, and it's distance to the start to 0.
            Node current_node = null;
            int current_distance = 0;

            // We need to look for the unvisited node with the shortest distance start. For this, we look at every node
            // in the Map, and if it is unvisited, and it has a shorter distance than the shortest one so far, then
            // it is the closest node to explore.
            for (Node node : distances_from_start.keySet()){
                if (unvisited.contains(node)){
                    int buffer_distance = distances_from_start.get(node).getValue();

                    if (current_node == null || current_distance > buffer_distance){
                        current_node = node;
                        current_distance = buffer_distance;
                    }
                }
            }

            // If no node was found to be the closest (e.g.: there is no unvisited nodes left in the list) get out of the loop.
            if (current_node == null){
                break;
            }

            // Get the adjacent nodes.
            List<Node> adjacent_nodes = get_Adjacent(current_node);

            // For every adjacent nodes, if it is not a visited node, then update the recorded distance from the Map,
            // only if the new distance is shorter.
            // This replaces the distance and the parent node.
            for (Node adjacent_node : adjacent_nodes){
                if (!unvisited.contains(adjacent_node)){
                    continue;
                }

                int adjacent_weight = find_edge(adjacent_node, current_node).get_Weight();

                if (adjacent_weight + current_distance < distances_from_start.get(adjacent_node).getValue()){
                    distances_from_start.replace(adjacent_node, new Pair<>(current_node,adjacent_weight + current_distance));
                }
            }

            // We have finished exploring this node, so remove it from the list.
            unvisited.remove(current_node);
        }

        // Array List to store the path.
        List<Node> path = new ArrayList<>();

        // Initialise the path list with the goal node.
        path.add(node_2);

        // Initialise parent_node to be the goal node.
        Node parent_node = node_2;

        // Now that the Map is complete, we can for the path by climbing up the ancestry record.
        // In succession, a child node returns its parent node, which is added to the path list.
        // the parent node then becomes the child node, which will get its parent and so on until the parent node is the
        // start node, or if it is null.
        while (parent_node != node_1 && parent_node != null){
            Pair<Node, Integer> parent_pair = distances_from_start.get(parent_node);
            if (parent_pair != null){
                parent_node = parent_pair.getKey();
                if (parent_node != null){
                    path.add(0, parent_node);
                }
            }

        }
        return path;
    }


    /***
     * The method random_nodes will take as input a list of parsed unique nodes and return two random nodes from that list.
     * @param nodes Parsed list of nodes.
     * @return A list containing two random nodes.
     */
    public List<Node> generate_random_nodes(List<Node> nodes){//takes as input a list of parsed unique nodes and returns 2 random nodes in the file

        ArrayList<Node> generate_list = new ArrayList<>();//puts all nodes in a list and shuffles them
        for (Node elem:nodes){
            generate_list.add(elem);

            Collections.shuffle(generate_list);
        }

        ArrayList<Node> random_list = new ArrayList<>();//creates another list and add the first two indexes of the shuffled list
        random_list.add(generate_list.get(0));
        random_list.add(generate_list.get(1));

        return random_list;
    }


}




