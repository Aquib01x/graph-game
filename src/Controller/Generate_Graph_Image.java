package Controller;

import Model.Graph;
import Model.Node;
import Model.Data_Import;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxStyleUtils;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.mxgraph.util.mxConstants.*;

public class Generate_Graph_Image {

    /***
     * Instance of graph class from Model package, it used to:
     * find if two nodes are adjacent or not
     * get the weight of edges connecting two adjacent nodes
     * generating two random nodes
     * finding the shortest path
     */
    Graph graph = new Graph();
    /***
     * string value which contains the
     * randomly generated start node
     */
    String start;
    /***
     * string value which contains the
     * randomly generated goal node
     */
    String goal;
    /***
     * Instance of Data Import class from Model package, it used to:
     * parse the nodes in the data file
     */
    Data_Import data_import = new Data_Import();
    /***
     * ArrayList of parse nodes from data file
     * used to add parsed nodes and edges to the Jgraph
     */
    List<Node> nodes = data_import.parse_node(data_import.read_file());
    /***
     * ArrayList of strings which contains all the nodes in the shortest path
     * used to change the color of the nodes in the correct path before generating
     * the correct route image.
     */
    List<String> path_nodes = new ArrayList<>();

    /***
     * HashMap containing the styling for the graph image
     * e.g. graph_styling{key:"node_font_size",value:"60"}
     */
    HashMap<String,String> graph_styling = new HashMap<>();
    /***
     * HashMap containing the styling for the nodes in the correct route image
     * e.g. correct_route_node_styling{key:"node_fill_color",value:"white"}
     */
    HashMap<String,String>correct_route_node_styling = new HashMap<>(); //HashMap containing the styling for the correct route image nodes
    /***
     * HashMap containing the styling for the edges in the correct route image
     * e.g. correct_route_edge_styling{key:"edge_stroke_color",value:"#cbcbcb"}
     */
    HashMap<String,String>correct_route_edge_styling = new HashMap<>(); //HashMap containing the styling for the correct route image edges


    /***
     * Constructor for Generate_Graph_Image
     * It calls all the methods in the correct order and generates two png images:"graph.png" and "correct_route.png"
     * @throws IOException  if path in invalid.
     */
    public Generate_Graph_Image() throws IOException {
        DefaultUndirectedWeightedGraph<String, Weighted_Edge> jgraph =
                new DefaultUndirectedWeightedGraph<>(Weighted_Edge.class);//initialize an empty j_graph.

        jgraph = populate_jgraph(jgraph);//populate it with parsed nodes and edges from data file.

        JGraphXAdapter<String, Weighted_Edge> graph_adapter = new JGraphXAdapter<>(jgraph);//create a graph adapter for the jgraph.

        mxFastOrganicLayout layout = new mxFastOrganicLayout(graph_adapter);//set the layout for the graph adapter.
        layout.setForceConstant(2000);//average radius there is of free space around each node.
        layout.setMinDistanceLimit(200);//distance between nodes.
        layout.setMaxIterations(5000);//number of iterations the layout will run to get the best possible output graph.

        layout.execute(graph_adapter.getDefaultParent());//execute the selected layout.

        mxGraphComponent graphComponent = new mxGraphComponent(graph_adapter);//create a graph component to access jgraph data.
        mxGraphModel graph_model = (mxGraphModel) graphComponent.getGraph().getModel();//create a graph model to modify the graphs styling.

        Collection<Object> jgraph_cells =  graph_model.getCells().values();//the cells represent all the components in a jgraph(nodes/edges).

        List<String> random_generated_nodes = get_random_nodes();//generate 2 random nodes:start and goal.

        start = random_generated_nodes.get(0);
        goal = random_generated_nodes.get(1);
        //get the id of the start node and the goal node from the ArrayList random_generated_nodes.
        String startID = get_nodeID_from_jgraph(start,jgraph_cells);
        String goalID = get_nodeID_from_jgraph(goal,jgraph_cells);

        //Code to generate graph image
        String graph_image ="graph.png";//set the image name.
//        String graph_image_path ="src/" + graph_image;//set the path.
        String graph_image_path ="./" + graph_image;//set the path.

        style_graph(graphComponent,graph_model,jgraph_cells,startID,goalID);//call the style_graph method to style the graph.
        generate_graph_image(graph_adapter,graph_image_path);//create the image of the graph.

        //Code to generate correct_route_image
        String correct_route_image = "correct_route.png";//set the image name.
//        String correct_route_image_path ="src/" + correct_route_image;//set the path.
        String correct_route_image_path ="./" + correct_route_image;//set the path.

        style_correct_route_image_nodes(graphComponent,graph_model,jgraph_cells,startID,goalID);//call the method style_correct_route_image_nodes to style the nodes in the correct_route image.
        style_correct_route_image_edges(graphComponent,graph_model,jgraph_cells);//call the method style_correct_route_image_edges to style the edges in the correct_route image.
        generate_correct_route_image(graph_adapter,correct_route_image_path);//create the image of the correct route.


    }


    /***
     * The method populate_jgraph() will take an empty jgraph and populate it with the parsed nodes and edges from the input file
     * @param jgraph empty jgraph object
     * @return a populated jgraph
     */
    public  DefaultUndirectedWeightedGraph<String, Weighted_Edge> populate_jgraph(DefaultUndirectedWeightedGraph<String, Weighted_Edge> jgraph) {

        //Add all parsed nodes as vertices to the jgraph.
        for (Node n : nodes) {
            jgraph.addVertex(Integer.toString(n.get_ID()));
        }

        //Compare all parsed nodes and if they are adjacent, add their edges to the jgraph.
        for (int i = 0; i < nodes.size(); i++) {
            Node current_node = nodes.get(i);
            for (Node parsed_node : nodes) {
                if (graph.is_Adjacent_To(current_node, parsed_node)) {
                    jgraph.addEdge(Integer.toString(current_node.get_ID()), Integer.toString(parsed_node.get_ID()));

                    int weight = graph.find_edge(parsed_node, current_node).get_Weight();
                    jgraph.setEdgeWeight(Integer.toString(current_node.get_ID()), Integer.toString(parsed_node.get_ID()), weight);//set the weight for each edge.
                }
            }
        }
        return jgraph;
    }


    /***
     *
     * @param node any node present in the file
     * @param jgraph_cells An arraylist of objects containing all the objects in the jgraph
     * @return The jgraph id of the input node
     */

    public String get_nodeID_from_jgraph(String node,Collection<Object> jgraph_cells) {//takes the randomly generated nodes and finds them in the jgraph data

        String nodeID = null;

        //Get the ID of the start node from the jgraph.
        String[] parts = jgraph_cells.toString().split("mxCell");//split the jgraph data to access the details of each cell/component.

        for (String part : parts) {
            if (part.contains("value=" + node+",") && part.contains("height=20.0")) {//if the value is equal to the start and the height of the cell is 20:
                String p = part.substring(0, part.indexOf(","));
                nodeID = p.substring(p.indexOf("=") + 1);//set the startID to everything after the equal sign.
            }
        }

        return nodeID;
    }


    /***
     * The method style_graph() will set the styling for the main graph image
     * @param graph_component mxGraphComponent
     * @param graph_model mxGraphModel
     * @param jgraph_cells An arraylist of objects containing all the objects in the jgraph
     * @param startID jgraph ID of the start node
     * @param goalID jgraph ID of the goal node
     * @return HashMap containing the styles for each component in the graph image
     * e.g. graph_styling{key:"node_font_size",value:"60"}
     */

    public HashMap<String,String> style_graph(mxGraphComponent graph_component,mxGraphModel graph_model,Collection<Object> jgraph_cells,String startID,String goalID){


        String[] colours = new String[] {"black", "blue", "gray", "green", "magenta", "orange", "red"}; //ArrayList of strings used to assign random color to nodes and edges.


        for (Object c : jgraph_cells) {//For all the cells/components in the jgraph:
            mxCell cell = (mxCell) c;

            mxGeometry geometry = cell.getGeometry();

            if (cell.isVertex()) {//if the cell is a node:
                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        jgraph_cells.toArray(), String.valueOf(mxConstants.STYLE_FONTSIZE), "60");//change font size for nodes to 60.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{graph_model.getCell(cell.getId())}, STYLE_STROKECOLOR, "black");//set stroke color to black.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        jgraph_cells.toArray(), mxConstants.STYLE_ENDARROW, mxConstants.NONE);//remove arrows as it is an undirected graph.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{graph_model.getCell(startID)}, STYLE_FILLCOLOR, "#90EE90");//set color for start node to green.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{graph_model.getCell(goalID)}, STYLE_FILLCOLOR, "#ff615d");//set color for goal node to red.

                //change the size of each node
                geometry.setWidth(80);
                geometry.setHeight(80);
                cell.getGeometry().setOffset(new mxPoint(0, 10));

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_SHAPE, SHAPE_ELLIPSE);//change the shape of nodes to an ellipse.

                //insert all the style changes in graph_styling hashmap
                graph_styling.put("node_font_size", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FONTSIZE));
                graph_styling.put("node_stroke_color", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKECOLOR));
                graph_styling.put("graph_end_arrow", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_ENDARROW));
                if(cell.getId().equals(startID)){
                    graph_styling.put("start_node_color", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
                }
                if(cell.getId().equals(goalID)){
                    graph_styling.put("goal_node_color", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
                }
                graph_styling.put("node_width", String.valueOf(cell.getGeometry().getWidth()));
                graph_styling.put("node_height", String.valueOf(cell.getGeometry().getHeight()));
                graph_styling.put("node_y_Offset", String.valueOf(cell.getGeometry().getOffset().getY()));
                graph_styling.put("node_shape", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_SHAPE));



            }

            else{//if the cell/component is an edge:
                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(colours.length);
                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_STROKECOLOR, colours[index]);//set stroke color to a random color from the colours ArrayList.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_FONTCOLOR, colours[index]);//set font color to a random color from the colours ArrayList.

                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_STROKEWIDTH, "5");//set stroke width to 5.

                //insert all the style changes in graph_styling hashmap.
                graph_styling.put("edge_color", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKECOLOR));
                graph_styling.put("edge_font_color", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FONTCOLOR));
                graph_styling.put("edge_stroke_width", (String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKEWIDTH));


            }
        }
        return graph_styling;
    }

    /***
     *The method generate_graph_image() will create the image of the graph and store in the source folder of the project
     * @param graph_adapter jgraphXAdapter.
     * @param image_path The path where the image will be placed.
     * @throws IOException if path in invalid.
     */
    public void generate_graph_image(JGraphXAdapter<String, Weighted_Edge> graph_adapter,String image_path) throws IOException {

            BufferedImage image = mxCellRenderer.createBufferedImage(graph_adapter, null, 1, Color.white, true, null);
            File imgFile = new File(image_path);
            ImageIO.write(image, "PNG", imgFile);

    }


    /***
     * The method style_correct_route_image_nodes() will set the styling for the nodes in the correct route image
     * @param graph_component mxGraphComponent
     * @param graph_model mxGraphModel
     * @param jgraph_cells An arraylist of objects containing all the objects in the jgraph
     * @param startID jgraph ID of the start node
     * @param goalID jgraph ID of the goal node
     * @return HashMap containing the styles for each node in the correct route image
     * e.g. correct_route_node_styling{key:"node_fill_color",value:"red"}
     */
    public HashMap<String,String> style_correct_route_image_nodes(mxGraphComponent graph_component,mxGraphModel graph_model,Collection<Object> jgraph_cells,String startID,String goalID){

        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                jgraph_cells.toArray(), STYLE_FILLCOLOR, "white");//set color for all nodes to white.

        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                new Object[]{graph_model.getCell(startID)}, STYLE_FILLCOLOR, "#90EE90");//set color for start node to green.

        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                new Object[]{graph_model.getCell(goalID)}, STYLE_FILLCOLOR, "#ff615d");//set color for goal node to red.

        //insert all the style changes in correct_route_node_styling hashmap.
        for (Object c : jgraph_cells) {
            mxCell cell = (mxCell) c;
            correct_route_node_styling.put("node_fill_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
            if(cell.getId().equals(startID)){
                correct_route_node_styling.put("start_node_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
            }
            if(cell.getId().equals(goalID)){
                correct_route_node_styling.put("goal_node_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
            }
        }

        //set path to an ArrayList of Nodes containing all nodes paths in the shortest path
        List<Node> path = graph.shortest_path(new Node(get_start_node()), new Node(get_goal_node()));


        String[] parts = jgraph_cells.toString().split("mxCell");//split the jgraph data to access the details of each cell/component.

        for(Node n:path){//for all the nodes in the shortest path.

            if(n.get_ID() != get_start_node() && n.get_ID() != get_goal_node()){//if the node is not a start or goal node:

                for (String part : parts) {

                    //if the jgraph data contains the node:
                    if (part.contains("value=" +n.get_ID()+",") && (part.contains("height=80.0") ||part.contains("height=20.0"))) {

                        String p = part.substring(0, part.indexOf(","));
                        String path_nodeID = p.substring(p.indexOf("=") + 1);//set path_nodeID to the jgraph ID.
                        path_nodes.add(path_nodeID);//add the path node to the ArrayList path_nodes.

                        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                                new Object[]{graph_model.getCell(path_nodeID)}, STYLE_STROKECOLOR, "red");//set the  path nodes stroke color to red.
                        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                                new Object[]{graph_model.getCell(path_nodeID)}, STYLE_FILLCOLOR, "black"); //set the path nodes color to black.
                        mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                                new Object[]{graph_model.getCell(path_nodeID)}, STYLE_FONTCOLOR, "white"); //set the path nodes font color to white.

                        //insert all the style changes in correct_route_node_styling hashmap.
                        for (Object c : jgraph_cells) {
                            mxCell cell = (mxCell) c;
                            if(cell.getId().equals(path_nodeID)){
                                correct_route_node_styling.put("path_node_stroke_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKECOLOR));
                                correct_route_node_styling.put("path_node_fill_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FILLCOLOR));
                                correct_route_node_styling.put("path_node_font_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FONTCOLOR));
                            }

                        }
                    }
                }
            }

        }

return correct_route_node_styling;
    }

    /***
     * The method style_correct_route_image_edges() will set the styling for the edges in the correct route image
     * @param graph_component mxGraphComponent
     * @param graph_model mxGraphModel
     * @param jgraph_cells An arraylist of objects containing all the objects in the jgraph
     * @return HashMap containing the styles for each edge in the correct route image
     *      * e.g.  style_correct_route_image_edges{key:"edge_stroke_color",value:"cbcbcb"}
     */
    public HashMap<String,String> style_correct_route_image_edges(mxGraphComponent graph_component,mxGraphModel graph_model,Collection<Object> jgraph_cells){


        List<Integer> shortest_path = get_shortest_path();

        int node_counter = 0;
        int edge_counter = 0;
        int other_counter = 0;
        for (Object c: jgraph_cells){//For all the cells/components in the jgraph:
            mxCell cell = (mxCell) c;

            if (cell.isVertex()){node_counter++;}//if the cell is a node,increment the node counter.
            else if (cell.isEdge()){edge_counter++;}//if the cell is an edge,increment the edge counter.
            else {other_counter++;}//if cell is not an edge or a vertex,increment other counter
        }

        for (Object c : jgraph_cells) {//For all the cells/components in the jgraph:
            mxCell cell = (mxCell) c; //cast

            if (cell.isEdge()) {//if the cell is an edge:
                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_STROKECOLOR, "#cbcbcb");//set edge stroke color to grey.
                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_FONTCOLOR, "#cbcbcb");//set edge font color to grey.
                mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                        new Object[]{cell}, STYLE_STROKEWIDTH, "5");//set stroke width.

                //insert all the style changes in correct_route_edge_styling hashmap.
                correct_route_edge_styling.put("edge_stroke_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKECOLOR));
                correct_route_edge_styling.put("edge_font_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FONTCOLOR));
                correct_route_edge_styling.put("edge_stroke_width",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKEWIDTH));
            }
        }

        for (int index = 0; index<shortest_path.size()-1; index++){//for every edge in the correct path:
            int edge_id = graph.find_edge(new Node(shortest_path.get(index)), new Node(shortest_path.get(index + 1))).get_ID();
            mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                    new Object[]{graph_model.getCell(Integer.toString(edge_id + node_counter + other_counter))}, STYLE_STROKEWIDTH, "20");//set the stroke width to 20.
            mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                    new Object[]{graph_model.getCell(Integer.toString(edge_id + node_counter + other_counter))}, STYLE_STROKECOLOR, "green");//set the stroke color to green.
            mxStyleUtils.setCellStyles(graph_component.getGraph().getModel(),
                    new Object[]{graph_model.getCell(Integer.toString(edge_id + node_counter + other_counter))}, STYLE_FONTCOLOR, "red");//set the font color to red.

            //insert all the styling changes in correct_route_edge_styling hashmap.
            for (Object c : jgraph_cells) {
                mxCell cell = (mxCell) c;
                if(cell.getId().equals(Integer.toString(edge_id + node_counter + other_counter))){
                    correct_route_edge_styling.put("path_edge_stroke_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKECOLOR));
                    correct_route_edge_styling.put("path_edge_font_color",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_FONTCOLOR));
                    correct_route_edge_styling.put("path_edge_stroke_width",(String) graph_component.getGraph().getCellStyle(cell).get(STYLE_STROKEWIDTH));
                }
            }
        }
        return correct_route_edge_styling;
    }


    /***
     * The method generate_correct_route_image() will create the image for the correct path and store it in the source folder of the project.
     * @param graph_adapter mxGraphAdapter.
     * @param image_path The path where the image will be placed.
     * @throws IOException if path is invalid.
     */
    public void generate_correct_route_image(JGraphXAdapter<String, Weighted_Edge> graph_adapter,String image_path) throws IOException {
        BufferedImage correct_route_image =
                mxCellRenderer.createBufferedImage(graph_adapter, null, 1, Color.white, true, null);//scaling has to be set to 1 for Graph_Frame to work
        File imgFile = new File(image_path);
        ImageIO.write(correct_route_image, "PNG", imgFile);
    }


    /***
     * The method  get_graph_styling()
     * @returns a HashMap containing the styling for the graph image.
     */
    public HashMap<String,String> get_graph_styling(){
        return  graph_styling;
    }

    /***
     * The method get_correct_route_node_styling()
     * @returns a HashMap containing the styling for the nodes in the correct route image
     */
    public HashMap<String,String> get_correct_route_node_styling(){
        return  correct_route_node_styling;
    }

    /***
     * The method get_correct_route_edge_styling()
     * @returns a HashMap containing the styling for the edges in the correct route image
     */
    public HashMap<String,String> get_correct_route_edge_styling(){
        return  correct_route_edge_styling;
    }

    /***
     * The method get_path_nodes()
     * @returns an ArrayList of strings containing all the nodes in the shortest path
     */
    public List<String> get_path_nodes(){
        return path_nodes;
    }

    /***
     * The method get_start_node()
     * @returns a randomly generated start node
     */
    public int get_start_node(){
        return Integer.parseInt(start);
    }

    /***
     * The method get_goal_node()
     * @returns a randomly generated goal node
     */
    public int get_goal_node(){
        return Integer.parseInt(goal);
    }

    /***
     * The method get_random_nodes() calls the method generate_random_nodes
     * in the graph class and converts the nodes to strings
     * @returns an ArrayList of strings containing 2 randomly generated unique nodes
     */
    public List<String> get_random_nodes() {

        List<Node> generate_random_nodes = graph.generate_random_nodes(nodes);
        List<String> random_nodes = new ArrayList<>();

        for (Node n : generate_random_nodes) {
            random_nodes.add(Integer.toString(n.get_ID()));
        }

        return random_nodes;
    }

    /***
     * The method get_shortest_path() calls the method shortest_path()
     * in the graph class and converts the nodes to integers
     * @returns an ArrayList of Integers
     */
    public List<Integer> get_shortest_path() {
        List<Node> path = graph.shortest_path(new Node(get_start_node()), new Node(get_goal_node()));

        List<Integer>nodes = new ArrayList<>();
        for (Node n : path) {
            nodes.add(n.get_ID());
        }
        return nodes;
    }

}
