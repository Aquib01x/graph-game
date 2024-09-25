package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Data_Import_Testing {

    List<String> test_data;
    Data_Import data_import;

    @BeforeEach
    void setUp(){
        test_data = Arrays.asList("0 1 {'weight': 6}", "0 3 {'weight': 1}", "1 2 {'weight': 5}", "1 3 {'weight': 2}", "1 4 {'weight': 2}", "2 4 {'weight': 5}", "3 4 {'weight': 1}");
        data_import = new Data_Import();
    }


    @Test
    @DisplayName("Testing method read_file()")
    void read_file_test() {
        List<String> imported_data = data_import.read_file();
        assertEquals(test_data, imported_data);
    }
    @Test
    @DisplayName("Testing method parse_node()")
    void parse_node_test() {
        List<Node> parsed_nodes = data_import.parse_node(test_data);
        List<Integer> expected_result =  Arrays.asList(0,1,2,3,4);
        List<Integer> actual_result = new ArrayList<>();
        for(Node n: parsed_nodes){
            actual_result.add(n.get_ID());
        }
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("Testing method parse_edge()")
    void parse_edge_test() {
        List<Edge> parsed_edges = data_import.parse_edge(test_data);

        List<Integer> expected_id =  Arrays.asList(0,1,2,3,4,5,6);
        List<Integer> actual_id = new ArrayList<>();
        for(Edge n: parsed_edges){
            actual_id.add(n.get_ID());
        }
        assertEquals(expected_id, actual_id);


        List<Integer> actual_node1 = new ArrayList<>();
        List<Integer> expected_node1 =  Arrays.asList(0, 0, 1, 1, 1, 2, 3);
        for(Edge n: parsed_edges){
            actual_node1.add(n.get_Node_1());
        }
        assertEquals(expected_node1, actual_node1);


        List<Integer> actual_node2 = new ArrayList<>();
        List<Integer> expected_node2 =  Arrays.asList(1, 3, 2, 3, 4, 4, 4);
        for(Edge n: parsed_edges){
            actual_node2.add(n.get_Node_2());
        }
        assertEquals(expected_node2, actual_node2);


        List<Integer> actual_weight = new ArrayList<>();
        List<Integer> expected_weight =  Arrays.asList(6, 1, 5, 2, 2, 5, 1);
        for(Edge n: parsed_edges){
            actual_weight.add(n.get_Weight());
        }
        assertEquals(expected_weight, actual_weight);
    }

}
