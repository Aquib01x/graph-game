package View;


import Controller.Generate_Graph_Image;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Correct_Route_Frame {

    /***
     * instance of display frame class used to display the correct route image
     */
    Display_Frame correct_route_frame;
    /***
     * Jlabel containing the shortest distance which will be displayed
     * on the main panel of the correct route frame
     */
    JLabel distance_label;
    /***
     * List of Jlabels which will contain the shortest path
     * {one Jlabel for each node in the shortest path}
     */
    List<JLabel> labels;

    /***
     * Constructor for Correct_Route_Frame class
     * This class is used by the controller to display the correct route frame
     * once the user clicks the "CORRECT ROUTE" button,it displays the "correct_route.png"
     * alongside the shortest distance and shortest path
     * @param image an instance of the Generate_Graph_Image class
     * @param shortest_distance the shortest distance between two randomly generated nodes
     */
    public Correct_Route_Frame(Generate_Graph_Image image, int shortest_distance){
        //display correct_route.png
//        correct_route_frame= new Display_Frame("Correct Route","./src/correct_route.png");
        correct_route_frame= new Display_Frame("Correct Route","./correct_route.png");

        //content panel
        JPanel correct_route_content_panel = new JPanel(new GridLayout(2, 1));

        //correct_route_panel to display correct path and shortest distance
        JPanel correct_route_panel = new JPanel();
        JLabel correct_path_label = new JLabel("The correct path is ");
        correct_path_label.setFont(new Font("arial", Font.BOLD, 20));
        correct_route_panel.add(correct_path_label);


        labels = new ArrayList<>();//ArrayList of Jlabels containing all generated Jlabels in ArrayList labels
        List<Integer> shortest_path = image.get_shortest_path();
        int i = 0;
        for (int n : shortest_path) {//for each node in the path,generate a Jlabel add it to correct route panel
            i++;
            JLabel node_label = new JLabel(Integer.toString(n));
            labels.add(node_label);
            node_label.setFont(new Font("arial", Font.BOLD, 20));
            correct_route_panel.add(node_label);

            //after each node_label,generate Jlabels containing arrows to display shortest path
            if (i != shortest_path.size()) {//dont generate arrow for last node
                JLabel arrow = new JLabel("→");
                correct_route_panel.add(arrow);
            }
        }
        //shortest_distance_panel to display shortest distance
        JPanel shortest_distance_panel = new JPanel();//label for total distance
        JLabel total_distance_label = new JLabel("The total distance is ");
        total_distance_label.setFont(new Font("arial", Font.BOLD, 20));
        distance_label = new JLabel(String.valueOf(shortest_distance));
        distance_label.setFont(new Font("arial", Font.BOLD, 20));
        shortest_distance_panel.add(total_distance_label);
        shortest_distance_panel.add(distance_label);

        correct_route_content_panel.setMaximumSize(new Dimension(2000, 100));
        correct_route_content_panel.add(correct_route_panel);//add the correct_route_panel to the correct_route_content panel
        correct_route_content_panel.add(shortest_distance_panel);//add the shortest_distance_panel to the correct_route_content panel


        correct_route_frame.getMainPanel().add(correct_route_content_panel);//add the updated contents to the correct route frame
    }

    /***
     * The method get_correct_route_frame()
     * @return an instance of the Display Frame class whose main Jpanel
     * has been modified to display the shortest distance and shortest path on top of the "correct_route.png"
     */
    public Display_Frame get_correct_route_frame(){
        return correct_route_frame;
    }

    /***
     * The method get_distance_label()
     * @return Jlabel containing the shortest distance which will be displayed
     * on the main panel of the correct route frame
     */
    public JLabel get_distance_label(){
        return distance_label;
    }

    /***
     * The method get_labels()
     * List of Jlabels which will contain the shortest path
     * {one Jlabel for each node in the shortest path}
     */
    public List<JLabel> get_labels(){
        return labels;
    }
}

