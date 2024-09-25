package View;

import Controller.Generate_Graph_Image;
import Model.Graph;
import Model.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Correct_Route_Frame_Testing {

    /***
     * Scenario-7:
     * {User Action} = User clicks on the "CORRECT ROUTE" button
     * {Result} = The correct route frame is displayed
     */
    @Test
    @DisplayName("Testing scenario7")
    void scenario7_test() throws IOException {
        Generate_Graph_Image img = new Generate_Graph_Image();//instance of Generate_Graph_Image from Controller package
        Graph graph = new Graph();//instance of Graph from Graph_ADT package

        int expected_shortest_distance = graph.shortest_distance(new Node(img.get_start_node()), new Node(img.get_goal_node()));
        Correct_Route_Frame correct_route_frame = new Correct_Route_Frame(img,expected_shortest_distance);

        assertTrue(correct_route_frame.get_correct_route_frame().isDisplayable());//Assert that the correct route frame is displayed

        assertEquals(expected_shortest_distance,Integer.parseInt(correct_route_frame.get_distance_label().getText()));//Assert that the Jlabel contains the correct shortest distance

        List<Integer> expected_shortest_path = img.get_shortest_path();
        List<Integer> actual_shortest_path = new ArrayList<>();
        for(JLabel label :correct_route_frame.get_labels()){//For all the Jlabels in ArrayList labels
            actual_shortest_path.add(Integer.valueOf(label.getText()));//Add their content to the ArrayList of integers actual_shortest_path
        }
        assertEquals(expected_shortest_path,actual_shortest_path); //Assert that the Jlabels contain the correct shortest path
    }
}
