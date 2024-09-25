package Model;

import Model.Data_Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Graph_Testing {
    Data_Import data_import;
    List<Node> parsed_nodes;
    Graph graph;

    @BeforeEach
    void setUp(){
        data_import = new Data_Import();
        parsed_nodes = data_import.parse_node(data_import.read_file());
        graph = new Graph();
    }

    @Test
    @DisplayName("Testing method get_Degree()")
    void get_Degree_test() {
        List<Integer> expected_result = Arrays.asList(2,4,2,3,3);
        List<Integer> all_degrees = new ArrayList<>();
        for (Node n : parsed_nodes) {
            all_degrees.add(graph.get_Degree(n));
        }
        assertEquals(expected_result, all_degrees);
    }


    @Test
    @DisplayName("Testing method get_Adjacent()")
    void get_Adjacent_test() {
        List<Integer> expected_adjacent_nodes = Arrays.asList(1, 3, 0, 2, 3, 4, 1, 4, 0, 1, 4, 1, 2, 3);
        List<Node> nodes;
        List<Integer> actual_adjacent_nodes = new ArrayList<>();

        for (Node n : parsed_nodes) {
            nodes = graph.get_Adjacent(n);
            for (Node node : nodes) {
                actual_adjacent_nodes.add(node.get_ID());
            }
        }
        assertEquals(expected_adjacent_nodes, actual_adjacent_nodes);
    }


    @Test
    @DisplayName("Testing method is_Adjacent_To()")//For each node, it tests if it is adjacent to all other nodes in the file
    void is_Adjacent_To_test() {
        List<Boolean> actual_is_Adjacent_To = new ArrayList<>();
        List<Boolean> expected_is_Adjacent_To = Arrays.asList(false, true, false, true, false, true, false, true, true,
                true, false, true, false, false, true, true, true,
                false, false, true, false, true, true, true, false);

        for (int i = 0; i < parsed_nodes.size(); i++) {
            Node current_node = parsed_nodes.get(i);
            for (Node parsed_node : parsed_nodes) {
                actual_is_Adjacent_To.add(graph.is_Adjacent_To(current_node, parsed_node));
            }
        }
        assertEquals(expected_is_Adjacent_To, actual_is_Adjacent_To);
    }


    @Test
    @DisplayName("Testing method find_edge()")//For each node, it tests if it is adjacent to all other nodes in the file
    void find_edge_test() {
        List<Integer> actual_edges = new ArrayList<>();
        List<Integer> expected_edges = Arrays.asList(null, 6, null, 1, null, 6, null, 5,
                2, 2, null, 5, null, null, 5, 1, 2, null, null, 1, null, 2, 5, 1, null);

        for (int i = 0; i < parsed_nodes.size(); i++) {
            Node current_node = parsed_nodes.get(i);
            for (Node parsed_node : parsed_nodes) {
                if (graph.find_edge(current_node, parsed_node) == null) {
                    actual_edges.add(null);
                } else if (graph.find_edge(current_node, parsed_node) != null) {
                    actual_edges.add(graph.find_edge(current_node, parsed_node).get_Weight());
                }



            }
            }
        assertEquals(expected_edges, actual_edges);
    }


    @Test
    @DisplayName("Testing method generate_random_nodes()")
    void random_nodes_test() {
        List<Node> boundary_check = new ArrayList<>();

        //Test1:Assert that each random node is in the correct boundary(for the test file the boundary is between 0 and 4)
        for (int i = 0; i < 1000; i++) {//run random nodes 1000 times and check that each random node it produces is in the boundary between 0 and 4(test-file).
            boundary_check.addAll(graph.generate_random_nodes(parsed_nodes));
        }
        boolean withing_boundary = true;

        for (Node node : boundary_check) {
            if (!(node.get_ID() >= 0 && node.get_ID() <= 4)) {
                withing_boundary = false;
                break;
            }
        }
        assertTrue(withing_boundary);


        //Test2:Assert that the two random generated nodes are not the same/equal
        boolean startngoal_notequal = true;

        for(int i = 0;i<=1000;i++){ //execute random nodes 1000 times and if the starting node is equal to the goal node,the test will fail
            List<Integer> random_nodes_ints = new ArrayList<>();

            for(Node n:graph.generate_random_nodes(parsed_nodes)){
                random_nodes_ints.add(n.get_ID());
            }
            if(random_nodes_ints.get(0).equals(random_nodes_ints.get(1))) {
                startngoal_notequal = false;
                break;
            }
        }
        assertTrue(startngoal_notequal);
    }


    @Test
    @DisplayName("Testing method add_to_agenda()")
    void add_to_agenda_test() {
        Node test_node = new Node(0);
        graph.add_to_agenda(test_node);
        Node node_from_agenda = graph.get_Agenda().get(0);
        assertEquals(test_node,node_from_agenda);
    }


    @Test
    @DisplayName("Testing method visit_node()")
    void visit_node_test() {
        Node expected_node = new Node(0);
        graph.visit_node(expected_node);
        Node node_from_visited = graph.get_Visited().get(0);
        assertEquals(expected_node,node_from_visited);
    }


    @Test
    @DisplayName("Testing method get_Agenda()")
    void get_Agenda_test(){
        List<Integer> expected_agenda = Arrays.asList(0,1,2,3,4);
        List<Integer> actual_agenda = new ArrayList<>();
        for(Node n:parsed_nodes){
            graph.add_to_agenda(n);
        }
        for(Node n:graph.get_Agenda()){
            actual_agenda.add(n.get_ID());
        }
        assertEquals(expected_agenda,actual_agenda);
    }


    @Test
    @DisplayName("Testing method get_Visited()")
    void get_Visited_test(){
        List<Integer> expected_visited = Arrays.asList(0,1,2,3,4);
        List<Integer> actual_visited = new ArrayList<>();
        for(Node n:parsed_nodes){
            graph.visit_node(n);
        }
        for(Node n:graph.get_Visited()){
            actual_visited.add(n.get_ID());
        }
        assertEquals(expected_visited,actual_visited);
    }


    @Test
    @DisplayName("Testing method shortest_distance()")
    void shortest_distance_test(){
        List<Integer> actual_distances = new ArrayList<>();
        List<Integer> expected_distances =
                Arrays.asList(0,3,7,1,2,3,0,
                        5,2,2,7,5, 0,6,5,1,2,
                        6,0,1,2,2,5,1,0);//these are the shortest distances from each node to all other nodes in the test file
        for (int i = 0; i < parsed_nodes.size(); i++) {
            Node current_node = parsed_nodes.get(i);
            for (Node parsed_node : parsed_nodes) {
                actual_distances.add(graph.shortest_distance(current_node, parsed_node));
            }
        }
        assertEquals(expected_distances,actual_distances);
    }


    @Test
    @DisplayName("Testing method shortest_path()")
    void shortest_path_test(){
        List<Integer> actual_paths= new ArrayList<>();
        List<Integer> expected_paths =
                Arrays.asList(0, 0, 3, 1, 0, 3, 4,
                        2, 0, 3, 0, 3, 4, 1, 3, 0, 1,
                        1, 2, 1, 3, 1, 4, 2, 4, 3, 0,
                        2, 1, 2, 2, 4, 3, 2, 4, 3, 0,
                        3, 1, 3, 4, 2, 3, 3, 4, 4, 3,
                        0, 4, 1, 4, 2, 4, 3, 4);//these are the expected_paths from each node to all other nodes in the test file
        for (int i = 0; i < parsed_nodes.size(); i++) {

            Node current_node = parsed_nodes.get(i);
            for (Node parsed_node : parsed_nodes) {
                for(Node n : graph.shortest_path(current_node, parsed_node)){
                    actual_paths.add(n.get_ID());
                }

            }
        }
        assertEquals(expected_paths,actual_paths);
    }
    }





