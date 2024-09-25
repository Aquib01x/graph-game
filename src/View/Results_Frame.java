package View;

import javax.swing.*;
import java.awt.*;

public class Results_Frame extends JFrame {
    /***
     * The player_data panel is the content panel which contains:
     * {title_panel} -> {title_label} -> displays the title "Game Results"
     *{player_name_panel} ->{player_name_label} -> displays player name
     *{difficulty_panel} -> {difficulty_label} -> displays difficulty level
     *{correct_answer_panel} -> {correct_answer_label} -> displays the correct answer(the shortest distance)
     * {user_guess_panel} -> {user_guess_label} -> displays users guess
     * {accuracy_panel} -> {accuracy_label} -> displays accuracy level
     * {score_panel} -> {score_label} -> displays score for the game(accuracy*difficulty)
     */
    JPanel player_data,title_panel,player_name_panel, difficulty_panel,correct_answer_panel, user_guess_panel,accuracy_panel,score_panel;
    JLabel title_label,player_name_label,difficulty_label,correct_answer_label,user_guess_label,accuracy_label,score_label;

    /***
     * Constructor for results frame class
     * This class is used by the game class in the Controller package
     * once the "SUBMIT" button is clicked on the game frame,the results frame is displayed
     * it displays the results of a given game through five Jlabels:
     * {player name}
     * {difficulty}
     * {correct answer}
     * {your guess}
     * {accuracy}
     * {score}
     * @param calculated_score the score for a given game
     * @param player_name username entered by the player
     * @param difficulty the difficulty level{easy,medium,hard}
     * @param user_guess the users guess
     * @param shortest_distance the shortest distance between two randomly generated nodes
     */
    public Results_Frame(int calculated_score, String player_name, String difficulty, String user_guess, int shortest_distance){
        int distance_guess = Integer.parseInt(user_guess);
        int accuracy = 100 * Math.min(distance_guess, shortest_distance) / Math.max(distance_guess, shortest_distance);

         //set frame styling
        setVisible(true);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setLocationRelativeTo(null);//make frame pop up in the middle
        setResizable(false);
        setTitle("Game Results");

        //create player_data panel to contain all other panels
        player_data = new JPanel();
        player_data.setLayout(new GridLayout(7,1));

        //create title panel and add it to player data panel
        title_panel = new JPanel();
        title_label = new JLabel("Game Results");
        title_label.setFont(new Font("Arial", Font.BOLD, 35));
        title_panel.add(title_label);

        player_data.add(title_panel);

        //create player name panel and add it to player data panel
        player_name_panel = new JPanel();
        player_name_label = new JLabel(player_name);
        JLabel txt1= new JLabel("Player Name:");
        txt1.setFont(new Font("Arial", Font.BOLD, 30));
        player_name_label.setFont(new Font("Arial", Font.PLAIN, 30));
        player_name_panel.add(txt1);
        player_name_panel.add(player_name_label);

        player_data.add(player_name_panel);

        //create difficulty panel and add it to player data panel
        difficulty_panel = new JPanel();
        difficulty_label = new JLabel(difficulty);
        JLabel txt2= new JLabel("Difficulty:");
        txt2.setFont(new Font("Arial", Font.BOLD, 30));
        difficulty_label.setFont(new Font("Arial", Font.PLAIN, 30));
        difficulty_panel.add(txt2);
        difficulty_panel.add(difficulty_label);

        player_data.add(difficulty_panel);

        //create correct answer panel and add it to player data panel
        correct_answer_panel = new JPanel();
        correct_answer_label = new JLabel(String.valueOf(shortest_distance));
        JLabel txt3= new JLabel("Correct Answer:");
        txt3.setFont(new Font("Arial", Font.BOLD, 30));
        correct_answer_label.setFont(new Font("Arial", Font.PLAIN, 30));
        correct_answer_panel.add(txt3);
        correct_answer_panel.add(correct_answer_label);

        player_data.add(correct_answer_panel);

        //create user guess panel and add it to player data panel
        user_guess_panel = new JPanel();
        user_guess_label = new JLabel(user_guess);
        JLabel txt4= new JLabel("Your Guess:");
        txt4.setFont(new Font("Arial", Font.BOLD, 30));
        user_guess_label.setFont(new Font("Arial", Font.PLAIN, 30));
        user_guess_panel.add(txt4);
        user_guess_panel.add(user_guess_label);

        player_data.add(user_guess_panel);

        //create accuracy panel and add it to player data panel
        accuracy_panel = new JPanel();
        accuracy_label = new JLabel(accuracy+"%");
        JLabel txt5= new JLabel("Accuracy:");
        txt5.setFont(new Font("Arial", Font.BOLD, 30));
        accuracy_label.setFont(new Font("Arial", Font.PLAIN, 30));
        accuracy_panel.add(txt5);
        accuracy_panel.add(accuracy_label);

        player_data.add(accuracy_panel);

        //create score panel and add it to player data panel
        score_panel = new JPanel();
        score_label = new JLabel(String.valueOf(calculated_score));
        JLabel txt6= new JLabel("Score:");
        txt6.setFont(new Font("Arial", Font.BOLD, 30));
        score_label.setFont(new Font("Arial", Font.PLAIN, 30));
        score_panel.add(txt6);
        score_panel.add(score_label);

        player_data.add(score_panel);

    //add player data panel to the Results Frame
        add(player_data);

    }

    /***
     * The method get_player_name_label()
     * @return Jlabel which contains the player name
     */
    public JLabel get_player_name_label(){
        return player_name_label;
    }
    /***
     * The method get_difficulty_label()()
     * @return Jlabel which contains the difficulty level{easy,medium,hard}
     */
    public JLabel get_difficulty_label(){
        return difficulty_label;
    }
    /***
     * The method get_correct_answer_label()
     * @return Jlabel which contains the correct answer(the shortest distance)
     */
    public JLabel get_correct_answer_label(){
        return correct_answer_label;
    }
    /***
     * The method get_user_guess_label()
     * @return Jlabel which contains the users guess
     */
    public JLabel get_user_guess_label(){
        return user_guess_label;
    }
    /***
     * The method get_score_label()
     * @return Jlabel which contains the score for a given game
     */
    public JLabel get_score_label(){
        return score_label;
    }

}
