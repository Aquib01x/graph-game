package View;

import javax.swing.*;
import java.awt.*;


public class Graph_Panel extends JPanel {
    /***
     * the image of the graph or correct route which is used by the paintComponent()
     * method to visualize the zoom in and zoom out features
     */
    private final ImageIcon ImageIcon;
    /***
     * double value which is used as measurement for increasing or decreasing zoom percentage
     * e.g. percentage= 0.1 zoom = 1.0
     * zoomIn() will set the zoom to 1.1
     * zoomOut() will set the zoom 0.9
     * initially set to 1.0
     */
    private double zoom = 1.0;

    /***
     * double value which represents the percentage of zoom to be applied
     */
    private double percentage;

    /***
     * Constructor for Graph_Panel class
     * The graph panel class is used by the Display Frame class to implement the zoom in and zoom out buttons
     * @param icon the graph image to be displayed
     * @param zoomPercentage the initial zoom percentage
     */
    public Graph_Panel(ImageIcon icon, double zoomPercentage) {
        this.ImageIcon = icon;
        percentage = zoomPercentage / 100;
    }

    /***
     * The method paintComponent() repaints the given image
     * everytime it is zoomed in or zoomed out
     * @param grp the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics grp) {
        Graphics2D g2D = (Graphics2D) grp;
        g2D.scale(zoom, zoom);
        g2D.drawImage(ImageIcon.getImage(), 0, 0, this);
    }


    /***
     * The method set_zoom_percentage takes an integer value and sets it the current zoom percentage
     * @param zoomPercentage the preferred zoom percentage to be used
     */
    public void set_zoom_percentage(int zoomPercentage) {
        percentage = ((double) zoomPercentage) / 100;
    }

    /***
     * The method get_zoom_percentage()
     * @return the zoom percentage currently being used
     */
    public double get_zoom_percentage(){return percentage*100;}

    /***
     * The method zoomIn() increments the zoom percentage and makes the image larger
     * @return incremented zoom percentage
     */
    public double zoomIn() {
        zoom += percentage;
        return zoom;
    }

    /***
     * The method zoomOut() decreases the zoom percentage and makes the image smaller
     *@return decreased zoom percentage
     */
    public double zoomOut() {
        zoom -= percentage;
        //if zoom(1.0) is less than current zoom percentage,limit the zoom out so that the image doesn't disappear
        if (zoom < percentage) {
            if (percentage > 1.0) {
                zoom = 1.0;
            } else {
                zoomIn();
            }
        }
        return zoom;
    }
}
