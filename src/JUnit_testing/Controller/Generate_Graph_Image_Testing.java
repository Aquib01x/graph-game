package Controller;

import Model.Graph;
import Model.Node;
import Model.Data_Import;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class Generate_Graph_Image_Testing {
    Data_Import data_import;
    List<Node> parsed_nodes;
    Generate_Graph_Image generate_graph_image;
    StringBuilder sb = new StringBuilder();
    String raw_jgraph_data;
    Graph graph;
    String startID;
    String goalID;
    JGraphXAdapter<String, Weighted_Edge> graph_adapter;
    mxGraphComponent graphComponent;
    mxGraphModel graph_model;
    DefaultUndirectedWeightedGraph<String, Weighted_Edge> jgraph;
    Collection<Object> jgraph_cells;
    @BeforeEach
    void setUp() throws IOException {
        data_import = new Data_Import();
        parsed_nodes = data_import.parse_node(data_import.read_file());
        generate_graph_image = new Generate_Graph_Image();
        sb = new StringBuilder();
        jgraph = new DefaultUndirectedWeightedGraph<>(Weighted_Edge.class);//initialize an empty j_graph
        sb.append(generate_graph_image.populate_jgraph(jgraph));
        raw_jgraph_data = sb.toString();
        graph = new Graph();
        graph_adapter = new JGraphXAdapter<>(jgraph);
        graphComponent = new mxGraphComponent(graph_adapter);
        graph_model = (mxGraphModel) graphComponent.getGraph().getModel();
        jgraph_cells = graph_model.getCells().values();
        startID = String.valueOf(generate_graph_image.get_start_node());
        goalID = String.valueOf(generate_graph_image.get_goal_node());

    }

    /***
     * The method parse_jgraph_nodes() parses the nodes in the raw jgraph data
     * The raw jgraph data for the test file looks like this:
     *
     *  [      nodes    ] [                            edges                           ]
     * ([0, 1, 2, 3, 4], [6={0,1}, 1={0,3}, 5={1,2}, 2={1,3}, 2={1,4}, 5={2,4}, 1={3,4}])
     *
     * The nodes are everything in the first square brackets[0,1,2,3,4]
     * parse_jgraph_nodes() takes all the nodes inside the first square brackets inserts
     * them into an ArrayList of integers
     * @return arraylist of integers containing parsed Jgraph nodes
     */
    public List<Integer> parse_jgraph_nodes() {
        String raw_nodes = raw_jgraph_data.substring(raw_jgraph_data.indexOf("[") + 1, raw_jgraph_data.indexOf("]"));//get the nodes from the raw data
        raw_nodes = raw_nodes.replace(" ", "");

        List<Integer> nodes = new ArrayList<>();
        int previous_node = -1;//starts from -1,to get the first elem
        for (int i = 0; i < raw_nodes.length(); i++) {//get each node in the raw data and add it to the nodes ArrayList.
            if (raw_nodes.charAt(i) == ',') {
                nodes.add(Integer.valueOf(raw_nodes.substring(previous_node + 1, i)));
                previous_node = i;
            }
            if (i == raw_nodes.length() - 1) {//add last elem
                nodes.add(Integer.valueOf(raw_nodes.substring(previous_node + 1)));
            }
        }

        return nodes;
    }


    /***
     * The method parse_jgraph_edges() parses the edges in the raw jgraph data
     * The raw jgraph data for the test file looks like this:
     *
     *  [   nodes     ]  [                          edges                              ]
     * ([0, 1, 2, 3, 4], [6={0,1}, 1={0,3}, 5={1,2}, 2={1,3}, 2={1,4}, 5={2,4}, 1={3,4}])
     *
     * The edges are everything in the second square brackets [6={0,1}, 1={0,3}, 5={1,2}, 2={1,3}, 2={1,4}, 5={2,4}, 1={3,4}]
     * parse_jgraph_edges() takes all the edges and nodes inside these square brackets inserts
     * them into an ArrayList of strings
     * @return arraylist of integers containing parsed Jgraph nodes
     */

    public List<String> parse_jgraph_edges() {
        String raw_nodes = raw_jgraph_data.substring(raw_jgraph_data.indexOf("[") + 1, raw_jgraph_data.indexOf("]"));
        raw_jgraph_data = raw_jgraph_data.replace(raw_nodes, "");//remove nodes from raw data
        raw_jgraph_data = raw_jgraph_data.replace("[]", "");//remove remaining brackets
        raw_jgraph_data = raw_jgraph_data.substring(1);//remove remaining comma

        String block_of_edges = raw_jgraph_data.substring(raw_jgraph_data.indexOf("[") + 1, raw_jgraph_data.indexOf("]"));//get the edges from the raw data

        List<String> split_up_edges = new ArrayList<>();
        int previous_counter = 0;
        for (int i = 0; i < block_of_edges.length(); i++) {//split up and parse the block of edges
            if ((block_of_edges.charAt(i) == ' ')) {
                split_up_edges.add((String) block_of_edges.subSequence(previous_counter, i - 1));//i-1 is to remove comma
                previous_counter = i;
            }
        }
        split_up_edges.add((String) block_of_edges.subSequence(previous_counter, block_of_edges.length())); //add the last element

        return split_up_edges;
    }


    @Test
    @DisplayName("Testing method populate_jgraph()")
    void populate_jgraph_test() throws IOException {

        //Assert that the jgraph nodes and the parsed/actual nodes are the same
        List<Integer> actual_nodes = new ArrayList<>();
        for (Node n : parsed_nodes) {
            actual_nodes.add(n.get_ID());
        }

        List<Integer> jgraph_nodes = parse_jgraph_nodes();

        assertEquals(actual_nodes, jgraph_nodes);

        //For each edge in the jgraph,verify the adjacency of its nodes and the correctness of its weight.

        List<String> edges = parse_jgraph_edges();

        for (int i = 0; i < edges.size(); i++) {
            Pattern p = Pattern.compile("\\{.*?\\,");
            Matcher m = p.matcher(edges.get(i));
            int node1_value = 0;
            if (m.find()) {
                node1_value = Integer.parseInt((String) m.group().subSequence(1, m.group().length() - 1));
            }

            Pattern x = Pattern.compile("\\,.*?\\}");
            Matcher z = x.matcher(edges.get(i));
            int node2_value = 0;
            if (z.find()) {
                node2_value = Integer.parseInt((String) z.group().subSequence(1, z.group().length() - 1));
            }

            String[] parts = edges.get(i).split("\\="); //get the weight of the edge,which is the number before the "=" sign.
            int actual_weight = Integer.parseInt(parts[0].replace(" ", ""));

            Node node1 = new Node(node1_value);
            Node node2 = new Node(node2_value);
            int expected_weight = graph.find_edge(node1, node2).get_Weight();

            assertEquals(expected_weight, actual_weight);
            assertTrue(graph.is_Adjacent_To(node1, node2));

        }


    }

    @Test
    @DisplayName("Testing method get_nodeID_from_jgraph()")
    void get_nodeID_from_jgraph_testing() {

        HashMap<Integer, Integer> value_and_id = new HashMap<Integer, Integer>();
        value_and_id.put(0, 2);//the jgraph id for node with value 0 is 2
        value_and_id.put(1, 3);//the jgraph id for node with value 1 is 3
        value_and_id.put(2, 4);//the jgraph id for node with value 2 is 4
        value_and_id.put(3, 5);//the jgraph id for node with value 3 is 5
        value_and_id.put(4, 6);//the jgraph id for node with value 4 is 6

        //for each parsed node in the test file,assert that the method "get_nodeID_from_jgraph()" returns the correct matching ID.
        for (Node n : parsed_nodes) {
            String actual_node_id = generate_graph_image.get_nodeID_from_jgraph(Integer.toString(n.get_ID()), jgraph_cells);
            String expected_node_id = Integer.toString(value_and_id.get(n.get_ID()));
            assertEquals(expected_node_id, actual_node_id);
        }

    }

    @Test
    @DisplayName("Testing method style_graph()")//this test checks the styling changes have been applied for the graph image
    void style_graph() throws IOException {
        HashMap<String,String> actual_graph_styling = generate_graph_image.get_graph_styling();

        String[] colours = new String[] {"black", "blue", "gray", "green", "magenta", "orange", "red"}; //edge colors are randomly chosen from this list

        //Assert that the node stroke color is black
        String expected_node_stroke_color="black";
        String actual_node_stroke_color = actual_graph_styling.get("node_stroke_color");
        assertEquals(expected_node_stroke_color,actual_node_stroke_color);

        //Assert that the start node is green
        String expected_start_node_color="#90EE90";
        String actual_start_node_color = actual_graph_styling.get("start_node_color");
        assertEquals(expected_start_node_color,actual_start_node_color);

        //Assert that the goal node is red
        String expected_goal_node_color="#ff615d";
        String actual_goal_node_color = actual_graph_styling.get("goal_node_color");
        assertEquals(expected_goal_node_color,actual_goal_node_color);

        //Assert that the node font size is 60
        String expected_node_font_size="60";
        String actual_node_font_size = actual_graph_styling.get("node_font_size");
        assertEquals(expected_node_font_size,actual_node_font_size);

        //Assert that the node height is 80.0
        String expected_node_height="80.0";
        String actual_node_height = actual_graph_styling.get("node_height");
        assertEquals(expected_node_height,actual_node_height);

        //Assert that the node width is 80.0
        String expected_node_width="80.0";
        String actual_node_width = actual_graph_styling.get("node_width");
        assertEquals(expected_node_width,actual_node_width);

        //Assert that the node shape is an ellipse
        String expected_node_shape="ellipse";
        String actual_node_shape = actual_graph_styling.get("node_shape");
        assertEquals(expected_node_shape,actual_node_shape);

        //Assert that the node y offset is set to 10.0
        String expected_y_offset="10.0";
        String actual_y_offset = actual_graph_styling.get("node_y_Offset");
        assertEquals(expected_y_offset,actual_y_offset);

        //Assert that the edge stroke width is 5
        String expected_edge_stroke_width="5";
        String actual_edge_stroke_width = actual_graph_styling.get("edge_stroke_width");
        assertEquals(expected_edge_stroke_width,actual_edge_stroke_width);

        //Assert that the edge font color is contained in list "colors"
        String actual_edge_font_color = actual_graph_styling.get("edge_font_color");
        assertTrue(Arrays.asList(colours).contains(actual_edge_font_color));

        //Assert that the edge  color is contained in list "colors"
        String actual_edge_color = actual_graph_styling.get("edge_color");
        assertTrue(Arrays.asList(colours).contains(actual_edge_color));

        //Assert that there is not an end arrow as it is an undirected graph
        String expected_end_arrow=null;
        String actual_end_arrow = actual_graph_styling.get("graph_end_arrow");
        assertEquals(expected_end_arrow,actual_end_arrow);

    }

    @Test
    @DisplayName("Testing method generate_graph_image()")
    void generate_graph_image() throws IOException {

        String test_image = "test.png";
        String image_path = "src/" + test_image;

        //assert that an image is created:
        generate_graph_image.generate_graph_image(graph_adapter, image_path);
        File imgFile = new File(image_path);
        assertTrue(imgFile.isFile());

    }

    @Test
    @DisplayName("Testing IOException for generate_graph_image()")
    public void generate_graph_image_exception() {
        //assert that IOException is thrown if incorrect file path is entered
        Throwable exception = assertThrows(IOException.class,
                ()->{
                    String test_image = "test.png";
                    String image_path = "null/" + test_image;//non existent-directory
                    generate_graph_image.generate_graph_image(graph_adapter, image_path);
                } );
    }

    @Test
    @DisplayName("Testing method style_correct_route_image_nodes()")//this test checks the styling changes have been applied for the nodes in the correct route image
    void style_correct_route_image_nodes() throws IOException {

        HashMap<String,String> actual_correct_route_node_styling= generate_graph_image.get_correct_route_node_styling();

        //Assert that the start node is green
        String expected_start_node_color="#90EE90";
        String actual_start_node_color = actual_correct_route_node_styling.get("start_node_color");
        assertEquals(expected_start_node_color,actual_start_node_color);

        //Assert that the goal node is red
        String expected_goal_node_color="#ff615d";
        String actual_goal_node_color = actual_correct_route_node_styling.get("goal_node_color");
        assertEquals(expected_goal_node_color,actual_goal_node_color);

        //Assert that the node fill color is white
        String expected_node_fill_color="white";
        String actual_node_fill_color = actual_correct_route_node_styling.get("node_fill_color");
        assertEquals(expected_node_fill_color,actual_node_fill_color);

        //if there are one or more nodes in the correct path,Assert that the styling of those nodes has been applied
        if(actual_correct_route_node_styling.containsKey("path_node_font_color")){

            //Assert that the path node font color is white
            String expected_path_node_font_color="white";
            String actual_path_node_font_color = actual_correct_route_node_styling.get("path_node_font_color");
            assertEquals(expected_path_node_font_color,actual_path_node_font_color);

            //Assert that the path node stroke color is red
            String expected_path_node_stroke_color="red";
            String actual_path_node_stroke_color = actual_correct_route_node_styling.get("path_node_stroke_color");
            assertEquals(expected_path_node_stroke_color,actual_path_node_stroke_color);

            //Assert that the path node fill color is black
            String expected_path_node_fill_color="black";
            String actual_path_node_fill_color = actual_correct_route_node_styling.get("path_node_fill_color");
            assertEquals(expected_path_node_fill_color,actual_path_node_fill_color);


        }

    }

    @Test
    @DisplayName("Testing method style_correct_route_image_edges()")//this test checks the styling changes have been applied for the nodes in the correct route image
    void style_correct_route_image_edges() throws IOException {

        HashMap<String, String> actual_correct_route_edge_styling = generate_graph_image.get_correct_route_edge_styling();

        //Assert that the edge stroke color is gray(#cbcbcb)
        String expected_edge_stroke_color="#cbcbcb";
        String actual_edge_stroke_color = actual_correct_route_edge_styling.get("edge_stroke_color");
        assertEquals(expected_edge_stroke_color,actual_edge_stroke_color);

        //Assert that the edge font color is gray(#cbcbcb)
        String expected_edge_font_color="#cbcbcb";
        String actual_edge_font_color = actual_correct_route_edge_styling.get("edge_font_color");
        assertEquals(expected_edge_font_color,actual_edge_font_color);

        //Assert that the edge stroke width is 5
        String expected_edge_stroke_width="5";
        String actual_edge_stroke_width = actual_correct_route_edge_styling.get("edge_stroke_width");
        assertEquals(expected_edge_stroke_width,actual_edge_stroke_width);


        //If the edges are in the correct path, Assert that their styling has been applied

        //Assert that the path_edge_stroke_width is 20
        String expected_path_edge_stroke_width="20";
        String actual_path_edge_stroke_width = actual_correct_route_edge_styling.get("path_edge_stroke_width");
        assertEquals(expected_path_edge_stroke_width,actual_path_edge_stroke_width);

        //Assert that the path_edge_stroke_color is green
        String expected_path_edge_stroke_color="green";
        String actual_path_edge_stroke_color = actual_correct_route_edge_styling.get("path_edge_stroke_color");
        assertEquals(expected_path_edge_stroke_color,actual_path_edge_stroke_color);

        //Assert that the path_edge_font_color is red
        String expected_path_edge_font_color="red";
        String actual_path_edge_font_color = actual_correct_route_edge_styling.get("path_edge_font_color");
        assertEquals(expected_path_edge_font_color,actual_path_edge_font_color);

    }

    @Test
    @DisplayName("Testing method generate_correct_route_image()")
    void generate_correct_route_image() throws IOException {

        String test2_image = "test2.png";
        String image_path = "src/" + test2_image;

        //assert that an image is created:
        generate_graph_image.generate_correct_route_image(graph_adapter, image_path);
        File imgFile = new File(image_path);
        assertTrue(imgFile.isFile());

    }

    @Test
    @DisplayName("Testing IOException for generate_correct_route_image_exception()")
    public void generate_correct_route_image_exception() {
        //assert that IOException is thrown if incorrect file path is entered
        Throwable exception = assertThrows(IOException.class,
                ()->{
                    String test_image = "test.png";
                    String image_path = "null/" + test_image;//non existent-directory
                    generate_graph_image.generate_correct_route_image(graph_adapter, image_path);
                } );
    }

}
