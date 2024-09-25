package Controller;

import Model.Graph;
import Model.Node;
import View.Correct_Route_Frame;
import View.Display_Frame;
import View.Results_Frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    /***
     * Instance of graph class from Model package, it used to:
     * get the randomly generated start and goal nodes
     * get the shortest path and shortest distance
     * calculate the score
     */
    Graph graph = new Graph();
    /***
     * Jlabel used for input validation, it displays
     * various types of errors if invalid input entered by user
     */
    JLabel error_label;

    /***
     * ArrayList of strings which stores users guesses,
     * it is used to prevent the user from entering the same value twice
     */
    List<String> user_guesses = new ArrayList<>();

    /***
     * JtextField used to get the users guess for the shortest distance
     */
    JTextField distance_text_field;
    /***
     * Jbutton used to handle user guesses,once clicked it validates the user input:
     * return an error Jlabel if input is invalid
     * return the game results if input is valid
     */
    JButton submit_button;
    /***
     * instance of Display_Frame class,used to display the graph
     * on the main frame
     */
    Display_Frame graph_frame;

    /***
     * Jbutton which allows the user to see the correct path,
     * once clicked it displays a frame which :
     * -visualizes the correct path
     * -displays the shortest path
     * -displays the shortest distance
     */
    JButton correct_route_button;

    /***
     * Jbutton which allows the user to restart the game,
     * once clicked, it:
     * -generates a new graph frame with different start and goal nodes.
     * -Takes the score from the previous game and displays it
     */
    JButton restart_button;

    /***
     * Jlabel used to store total score which is displayed
     * everytime the game is restarted
     */
    JLabel total_score_label;
    /***
     * Jlabel used to store difficulty level which
     * is displayed on the game frame and used
     * to calculate the score
     */
    JLabel difficulty_level;

    /***
     * Jlabel used to display the randomly
     * generated start node
     */
    JLabel start_node_label;
    /***
     * Jlabel used to display the randomly
     * generated goal node
     */
    JLabel goal_node_label;
    /***
     * integer value which represents
     * the number of times the game has been restarted
     */
    int restart_count = 0;
    /***
     * integer value used to calculate total score once game is restarted
     * it does this by adding last games score to the current games one
     */
    int new_total_score;

    /***
     * Jlabel used to display the player username on the game frame
     */
    JLabel player_name_label;
    /***
     * String value which represents difficulty level,
     * difficulty is calculated using the following formula:
     * path.size() <= 3  =>easy
     * path.size() <= 6  =>medium
     * path.size() >= 7  =>hard
     */
    String difficulty;
    /***
     * Jlabel which displays "Distance:" before the distance_text_field
     * which is used to get the users guess
     */
    JLabel distance_label;


    /***
     * Constructor for game class,it takes the player name and total score and
     * runs the run_game() method
     * @param player_name username entered by the player
     * @param total_score the total score for a given game
     * @throws IOException if path in invalid
     */
    public Game(String player_name, int total_score) throws IOException {
        run_game(player_name, total_score);
    }

    /***
     * The method run_game takes the player name and total score as parameters
     * it then generates an image for the graph and dispalys it on the game
     * frame alongside other components(Jlabels,Jbuttons and a Jtextfield)
     * @param player_name username entered by the player
     * @param total_score the total score for a given game
     * @throws IOException if path in invalid
     */
    public void run_game(String player_name, int total_score) throws IOException {
        final boolean[] button_visible = {false};

         new_total_score = total_score;

        //generate graph image
        Generate_Graph_Image image;
        try {
            image = new Generate_Graph_Image();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        //use the generated graph image and display it on the graph frame
//        graph_frame = new Display_Frame("Graph","./src/graph.png");
        graph_frame = new Display_Frame("Graph","./graph.png");

        JPanel content_panel = new JPanel(new GridLayout(9, 1));
        content_panel.setMaximumSize(new Dimension(1000, 2000));


        /////////////////////////////add player name to content panel//////////////////////////////////////////
        player_name_label = new JLabel(player_name);
        player_name_label.setFont(new Font("Arial", Font.BOLD, 30));
        JPanel player_name_panel = new JPanel();
        JLabel player_name_title_label = new JLabel("Player Name:");
        player_name_title_label.setFont(new Font("Arial", Font.BOLD, 30));
        player_name_panel.add(player_name_title_label);
        player_name_panel.add(player_name_label);
        content_panel.add(player_name_panel);

        /////////////////////////////add total score to content panel//////////////////////////////////////////
        total_score_label = new JLabel(String.valueOf(total_score));
        total_score_label.setFont(new Font("Arial", Font.BOLD, 30));
        JPanel total_score_panel = new JPanel();
        JLabel total_score_title_label = new JLabel("Total score: ");
        total_score_title_label.setFont(new Font("Arial", Font.BOLD, 30));
        total_score_panel.add(total_score_title_label);
        total_score_panel.add(total_score_label);
        content_panel.add(total_score_panel);

        /////////////////////////////add difficulty level to content panel////////////////////////////////////
        List<Node> path = graph.shortest_path(new Node(image.get_start_node()), new Node(image.get_goal_node()));

        if (path.size() <= 3) {
            difficulty = "easy";
        } else if (path.size() <= 6) {
            difficulty = "medium";
        } else {
            difficulty = "hard";
        }

        JLabel difficulty_label = new JLabel("Difficulty:");
        difficulty_level = new JLabel(difficulty);
        difficulty_level.setFont(new Font("Arial", Font.BOLD, 25));
        difficulty_label.setFont(new Font("Arial", Font.PLAIN, 25));

        if (difficulty.equals("easy")) {
            difficulty_level.setForeground(new Color(0, 170, 0)); // dark green
        } else if (difficulty.equals("medium")) {
            difficulty_level.setForeground(Color.BLUE);
        } else {
            difficulty_level.setForeground(Color.RED);
        }
        JPanel difficulty_panel = new JPanel();
        difficulty_panel.add(difficulty_label);
        difficulty_panel.add(difficulty_level);

        content_panel.add(difficulty_panel);

        ////////////////////////////////add start and goal node to content panel//////////////////////////////

        int start_node = image.get_start_node();
        start_node_label = new JLabel("Start Node:");
        JLabel start_value_label = new JLabel(String.valueOf(start_node));
        start_value_label.setFont(new Font("Arial", Font.BOLD, 25));
        start_node_label.setFont(new Font("Arial", Font.PLAIN, 25));
        start_value_label.setForeground(new Color(0, 170, 0)); // dark green

        int goal_node = image.get_goal_node();
        goal_node_label = new JLabel("Goal Node:");
        JLabel goal_value_label = new JLabel(String.valueOf(goal_node));
        goal_value_label.setFont(new Font("Arial", Font.BOLD, 25));
        goal_node_label.setFont(new Font("Arial", Font.PLAIN, 25));
        goal_value_label.setForeground(Color.RED);

        JPanel start_and_goal = new JPanel(new GridLayout(2, 1));

        JPanel start_panel = new JPanel();
        start_panel.add(start_node_label);
        start_panel.add(start_value_label);
        start_and_goal.add(start_panel);

        JPanel goal_panel = new JPanel();
        goal_panel.add(goal_node_label);
        goal_panel.add(goal_value_label);
        start_and_goal.add(goal_panel);

        content_panel.add(start_and_goal);

        ///////////////add distance label, error label and distance input text area to content panel////////////

        distance_label = new JLabel("Distance:");
        distance_text_field = new JTextField(2);
        distance_text_field.setPreferredSize(new Dimension(40, 40));
        distance_text_field.setFont(new Font("Arial", Font.PLAIN, 25));
        distance_label.setFont(new Font("Arial", Font.PLAIN, 25));
        JPanel distance_panel = new JPanel();
        distance_panel.add(distance_label);
        distance_panel.add(distance_text_field);
        content_panel.add(distance_panel);
        error_label = new JLabel();

        content_panel.add(error_label);

        ////////////////////////////////////submit button////////////////////////////////////////

        JPanel submit_button_panel = new JPanel();
        String finalDifficulty = difficulty;
        String finalDifficulty1 = difficulty;
        submit_button = new JButton(new AbstractAction("SUBMIT") {
            @Override
            public void actionPerformed(ActionEvent e) {//if submit button is clicked:

                if (validate_distance_text_field(distance_text_field.getText()) != null) {// display correct route button only if the user enters a valid guess
                    if (!button_visible[0]) {//if button_visible is set to false, display the correct route button
                         correct_route_button = new JButton(new AbstractAction("CORRECT ROUTE") {

                             @Override
                            public void actionPerformed(ActionEvent e) {
                                 //if correct route button is clicked,create an instance of correct route frame class from view
                                 Correct_Route_Frame correct_route_frame =
                                       new Correct_Route_Frame(image, graph.shortest_distance(new Node(image.get_start_node()), new Node(image.get_goal_node())));
                            }
                        });
                        correct_route_button.setPreferredSize(new Dimension(200, 30));
                        correct_route_button.setFont(new Font("Arial", Font.BOLD, 15));
                        correct_route_button.setForeground(Color.white);
                        correct_route_button.setBackground(Color.blue);
                        JPanel correct_route_panel = new JPanel();
                        correct_route_panel.add(correct_route_button);

                        content_panel.add(correct_route_panel);
                        content_panel.revalidate();
                        content_panel.repaint();

                        button_visible[0] = true;
                    }
                }

                //get the user guess and validate it
                String user_guess = validate_distance_text_field(distance_text_field.getText());

                //if the user guess in not null and is not present in guessed_already then:
                if (user_guess != null && (!guessed_already(distance_text_field.getText()))) {
                    //calculate the score
                    int current_score = calculated_score(finalDifficulty, user_guess, graph.shortest_distance(new Node(image.get_start_node()), new Node(image.get_goal_node())));

                    //create an instance of the game_results class from view to display the game results
                    Results_Frame game_results = new Results_Frame(current_score,player_name, finalDifficulty1,user_guess,graph.shortest_distance(new Node(image.get_start_node()), new Node(image.get_goal_node())));

                    //update the total score
                    new_total_score = total_score + current_score;

                }
            }
        });

        //set submit button styling and add it to content panel
        submit_button.setPreferredSize(new Dimension(200, 50));
        submit_button.setFont(new Font("Arial", Font.BOLD, 35));
        submit_button.setBackground(Color.green);
        submit_button_panel.add(submit_button);
        submit_button_panel.add(submit_button);
        content_panel.add(submit_button_panel);

        JPanel restart_button_panel = new JPanel();
        ////////////////////////////////////restart button////////////////////////////////////////
         restart_button = new JButton(new AbstractAction("RESTART") {
            @Override
            public void actionPerformed(ActionEvent e) {//once restart button is pressed:
                graph_frame.dispose();//close the current frame
                restart_count++;//increment the restart count which represents the number of games played
                try {
                    Game game = new Game(player_name, new_total_score);//generate new game
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //set restart button styling and add it to content panel
        restart_button.setPreferredSize(new Dimension(200, 50));
        restart_button.setFont(new Font("Arial", Font.BOLD, 35));
        restart_button.setBackground(Color.orange);
        restart_button_panel.add(restart_button);
        restart_button_panel.add(restart_button);

        content_panel.add(restart_button_panel);

        //add content panel containing all other panels to main panle
        graph_frame.getMainPanel().add(content_panel);
        graph_frame.revalidate();
        graph_frame.repaint();



    }

    /***
     * The method validate_distance_text_field validates the users guess
     * and displays the appropriate error messages if the input is invalid
     * @param distance_text_field_value the value of text field where input in entered
     * @return validated user guess
     */
    public String validate_distance_text_field(String distance_text_field_value){
        String distance = distance_text_field_value;
        if (distance.isEmpty()) {
            JLabel error = get_error_label();
            error.setForeground(Color.red);
            error.setFont((new Font("Arial", Font.BOLD, 20)));
            error.setText("Error:You have not entered a value!");
            return null;
        }
            if (!isNumeric(distance)) {
                JLabel error = get_error_label();
                error.setForeground(Color.red);
                error.setFont((new Font("Arial", Font.BOLD, 20)));
                error.setText("Error:Your input is invalid!");
                return null;}
            if (isNumeric(distance) && Integer.parseInt(distance)<0) {
                JLabel error = get_error_label();
                error.setForeground(Color.red);
                error.setFont((new Font("Arial", Font.BOLD, 20)));
                error.setText("Error:Entered negative number.");
                return null;}
            if (distance.length()>3) {
                JLabel error = get_error_label();
                error.setForeground(Color.red);
                error.setFont((new Font("Arial", Font.BOLD, 20)));
                error.setText("Error:The maximum length is 3.");
                return null;}

            if(isNumeric(distance)&& Integer.parseInt(distance)>0 &&distance.length()<3){
                JLabel error = get_error_label();
                error.setText("");
            }


        return distance;
    }

    /***
     * The method guessed_already() checks if the user has already guessed the input
     * if it has,it displays an error through the error Jlabel
     * @param distance_text_field the value of text field where input in entered
     * @return boolean indicating if value has been already guessed or not
     */
    public boolean guessed_already(String distance_text_field){
        if(user_guesses.contains(distance_text_field)){
            JLabel error = get_error_label();
            error.setForeground(Color.red);
            error.setFont((new Font("Arial", Font.BOLD, 20)));
            error.setText("Error:You have already entered "+distance_text_field + ".");
            //JOptionPane.showMessageDialog(null, "You have already entered "+distance_text_field + ".");
            return true;
        }else{
            JLabel error = get_error_label();
            error.setText("");
            user_guesses.add(distance_text_field);
            return false;
        }
    }

    /***
     * The method calculated score  takes 3 parameters
     * and calcualtes the score for the current game
     * score = accuracy * difficulty
     * @param difficulty string value which represents the difficulty level
     * @param distance_text_field string value which contains the users guess
     * @param shortest_distance integer which contains the shortest distance
     * @return integer value which will contain the calculated score
     */
    public int calculated_score(String difficulty, String distance_text_field, int shortest_distance) {

        int score = 0;

        int distance_guess = Integer.parseInt(distance_text_field);
        int accuracy = 100 * Math.min(distance_guess, shortest_distance) / Math.max(distance_guess, shortest_distance);

            if (difficulty.equals("easy")) {
                score = accuracy * 2;
            } else if ((difficulty.equals("medium"))) {
                score = accuracy * 4;
            } else if ((difficulty.equals("hard"))){
                score = accuracy * 8;
            }
        return score;
    }

    /***
     * The method get_correct_route_button
     * @return correct route button
     */
    public JButton get_correct_route_button(){
        return correct_route_button;
    }

    /***
     * The method isNumeric takes a string as input and
     * determines if the string contains a number,if does:
     * @return true
     * if it doesn't:
     * @return  false
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    /***
     * The method get_error_label()
     * @return the error label which display various types of
     * errors if user input is invalid
     */
    public JLabel get_error_label(){
        return error_label;
    }
    /***
     * The method get_distance_text_field()
     * @return the distance text field which takes the users guess
     */
    public JTextField get_distance_text_field(){return distance_text_field;}
    /***
     * The method get_submit_button()
     * @return the submit button which handles user guesses
     */
    public JButton get_submit_button(){return submit_button;}

    /***
     * The method get_graph_frame()
     * @return graph frame which display the graph image
     */
    public Display_Frame get_graph_frame(){
        return graph_frame;
    }
    /***
     * The method get_restart_button()
     * @return the restart button which allows the user to restart the game
     */
    public JButton get_restart_button(){
        return restart_button;
    }
    /***
     * The method get_restart_count()
     * @return integer value which represents the number of games played
     */
    public int get_restart_count(){
        return restart_count;
    }

    /***
     * The method get_new_total_score()
     * @return integer value which represents the updated total score
     * once the game is restarted
     */
    public int get_new_total_score(){
        return new_total_score;
    }
    /***
     * The method get_player_name_label()()
     * @return Jlabel which displays the text:"Player Name:"
     */
    public JLabel get_player_name_label(){return player_name_label;}

    /***
     * The method get_total_score_label()
     * @return Jlabel which displays the total score for a given game
     */
    public JLabel get_total_score_label(){return total_score_label;}

    /***
     * The method get_Difficulty()
     * @return string value which represents the difficulty level
     */
    public String get_difficulty(){return difficulty;}

    /***
     * The method get_difficulty_level()
     * @return Jlabel used to store difficulty level which
     * is displayed on the game frame and used to calculate the score
     */
    public JLabel get_difficulty_level(){return difficulty_level;}

    /***
     * The method get_start_node_label()
     * @return Jlabel used to display the randomly generated start node
     */
    public JLabel get_start_node_label(){return start_node_label;}
    /***
     * The method get_goal_node_label()
     * @return Jlabel used to display the randomly generated start node
     */
    public JLabel get_goal_node_label(){return goal_node_label;}
    /***
     * The method get_distance_label()
     * @return Jlabel which displays "Distance:" before
     * the distance text field which is used to get the users guess
     */
    public JLabel get_distance_label(){return distance_label;}
}
