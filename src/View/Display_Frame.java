package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;


public class Display_Frame extends JFrame {

    /***
     * Jpanel with a box layout which will contain the graph_display panel
     * The graph_display panel will contain the following:
     * graph image
     * zoom in button
     * zoom out button
     */
    JPanel mainPanel = new JPanel();

    /***
     * Jpanel which will contain the zoom in and zoom out buttons
     * this jpanel will be added to graph_display panel
     */
    JPanel buttons = new JPanel();

    /***
     * Constructor for Display_Frame class
     * This class is used by Correct_Route_Frame class in the View package the Game class in the Controller Package
     *it displays a frame containing the image for the graph and two zoom buttons created using the
     * Graph_Panel class in View
     * @param title title of the frame
     * @param path file path where the image is located
     */
    public Display_Frame(String title,String path){

        //set the title for the frame
        setTitle(title);

        //if the graph is to be displayed,set the close operation to "EXIT ON CLOSE"
        if(Objects.equals(title, "Graph")) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }else{//else if  the correct route graph is to be displayed,set the close operation to "DISPOSE_ON_CLOSE"
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        try{
            //read file path and scale the image
            File file = new File(path);
            BufferedImage bimg = ImageIO.read(file);
            Image scaled = bimg.getScaledInstance(4000, 4000, Image.SCALE_SMOOTH);//scale to a higher resolution so that when you zoom out,image doesn't start fading.
            ImageIcon icon = new ImageIcon(scaled);

            //create an instace of Graph_Panel
            Graph_Panel graph_panel = new Graph_Panel(icon,20);

            //create a Jscrollpane to allow scrolling around the image
            JScrollPane pane = new JScrollPane(graph_panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            graph_panel.setPreferredSize(new Dimension(1600, 1200));//setting size of panel for JScroll Pane

            //create graph_display panel and add jscrollpane to it
            JPanel graph_display = new JPanel();
            pane.setPreferredSize(new Dimension(600, 600));//setting size of JScroll Pane,this has to be set to 400x400 for an optimal Scroll Pane
            pane.getVerticalScrollBar().setUnitIncrement(16);
            graph_display.add(pane);

            //set size for buttons panel
            buttons.setPreferredSize(new Dimension(50,50));
            buttons.setMaximumSize(new Dimension(150,50));

            //add zoom in button to buttons panel
            buttons.add(new JButton(new AbstractAction("+") {
                public void actionPerformed(ActionEvent e) {
                    graph_panel.zoomIn();
                    graph_display.revalidate();//refresh graph_display panel once zoomed in
                    graph_display.repaint();//repaint graph_display panel once zoomed in
                }
            }));

            //add zoom out button to buttons panel
            buttons.add(new JButton(new AbstractAction("-") {
                public void actionPerformed(ActionEvent e) {
                    graph_panel.zoomOut();
                    graph_display.revalidate();//refresh graph_display panel once zoomed out
                    graph_display.repaint();//repaint graph_display panel once zoomed out
                }
            }));


            //add buttons panel to graph display panel
            graph_display.add(buttons);

            //set graph display layout
            graph_display.setLayout(new BoxLayout(graph_display,BoxLayout.Y_AXIS));

            //if graph image is to be displayed set the mainPanels box layout to X_AXIS
            if(Objects.equals(title, "Graph")){
                mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
            }else{//else if correct route image is to be displayed set the mainPanels box layout to Y_AXIS
                mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            }

            //add graph display panel to graph main panel
            mainPanel.add(graph_display);

            //add mainPanel to Display_Frame
            add(mainPanel);

            //set Jscrollpane background color to white
            pane.setBackground(Color.white);

            //setting the default zoom out
            graph_panel.zoomOut();
            graph_panel.set_zoom_percentage(10);
            graph_panel.zoomOut();
            graph_panel.zoomOut();
            graph_panel.zoomOut();
            graph_panel.zoomOut();
            graph_panel.zoomOut();


        } catch(Exception e){
            System.out.println("Image not found");
        }


        pack();//make sure all swing components fit inside Display_Frame
        setLocationRelativeTo(null);//make frame pop up in the middle
        setSize(1000,700);//set Display_Frame default size
        setVisible(true);//make Display_Frame visible
    }

    /***
     * The method getMainPanel()
     * @return Jpanel with a box layout which will contain the graph_display panel
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }

    /***
     * The method getButtons()
     * @return Jpanel which will contain the zoom in and zoom out buttons
     */
    public JPanel getButtons(){
        return buttons;
    }
}
