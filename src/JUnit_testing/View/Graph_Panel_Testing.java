package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Graph_Panel_Testing {
    BufferedImage bimg;
    ImageIcon icon;
    Graph_Panel graph_panel;
    @BeforeEach
    void setUp() throws IOException {
//        File file = new File("./src/graph.png");
        File file = new File("./graph.png");
       bimg = ImageIO.read(file);
       icon = new ImageIcon(bimg);
       graph_panel = new Graph_Panel(icon,20);//creating a graph panel with zoom percentage 20%.

    }

    /***
     * Scenario-3:
     * {User Action} = User clicks zoom in button
     * {Result} = Graph Image is zoomed in
     */
    @Test
    @DisplayName("Testing scenario3")
    void scenario3() throws IOException {
        double zoom = 1.0;
        assertNotEquals(graph_panel.zoomIn(), zoom, 0.0);//Assert that initial zoom value has changed,meaning zoom in has occurred;

    }

    /***
     * Scenario-4:
     * {User Action} = User clicks zoom out button
     * {Result} = Graph Image is zoomed out
     */
    @Test
    @DisplayName("Testing scenario4")
    void scenario4() throws IOException {
        double zoom = 1.0;

        Graph_Panel graph_panel = new Graph_Panel(icon,20);//creating a graph panel
        assertNotEquals(graph_panel.zoomOut(), zoom, 0.0);//Assert that initial zoom value has changed,meaning zoom out has occurred

    }

    @Test
    @DisplayName("Testing method set_zoom_percentage()")
    void set_zoom_percentage() throws IOException {

        graph_panel.set_zoom_percentage(10);//setting the percentage to 10%.
        assertEquals(10,graph_panel.get_zoom_percentage());//Assert that the zoom percentage has changed.

    }


}
