package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Start_Menu{
    /***
     * Jframe window which pops up once
     * user executes the application
     */
    JFrame window;
    /***
     * Container which will contains the Jpanels:
     * textPanel and inputPanel
     */
    Container container;
    /***
     * Jpanels:
     * textPanel: displays "Please enter your username" and the error Jlabel
     * inputPanel: displays the input text field and the play button
     * emptyPanel is used to leave a gap between the text and the button in the inputPanel
     */
    JPanel textPanel,inputPanel,emptyPanel;
    /***
     * font used for the error Jlabel in inputPanel
     */
    Font errorfont = new Font("Times New Roman",Font.BOLD,16);
    /***
     * textLabel is used to display "Please enter your username"
     * errorLabel is used to display various errors e.g.""Error:Your have to enter something to continue!"
     */
    JLabel textLabel,errorLabel;
    /***
     * textField used to get the username which is validated by the play button
     */
    JTextField input;
    /***
     * button which handles the user input
     * it the input is invalid:it displays errors through the error Jlabel
     * if input is valid: it closes the current frame and generates the main Game frame
     */
    JButton play;
    /***
     * input handler used by the play button to handle input
     */
    InputHandler inputHandler = new InputHandler();

    public Start_Menu(){
        Font arial = new Font("Arial", Font.PLAIN, 30);//font used for Jframe window

        //create the window Jframe
        window = new JFrame("Graph Game");
        window.setSize(500,500);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.getContentPane().setBackground(new Color(236,236,236));
        window.setLayout(null);
        window.setLocationRelativeTo(null);//make frame pop up in the middle
        window.setResizable(false);
        window.setFont(arial);
        container = window.getContentPane();

        //set textPanel styling
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2,1));
        textPanel.setBounds(55,100,400,100);
        textPanel.setBackground(new Color(236,236,236));
        textPanel.setFont(arial);

        //add textLabel and errorLabel to textPanel
        textLabel = new JLabel("Please enter your username");
        textLabel.setForeground(Color.black);
        textLabel.setFont(arial);
        textPanel.add(textLabel);
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.black);
        errorLabel.setFont(errorfont);
        textPanel.add(errorLabel);

        //add textPanel to container
        container.add(textPanel);

        //set inputPanel styling
        inputPanel = new JPanel();
        inputPanel.setBounds(150,250,200,100);
        inputPanel.setBackground(Color.black);
        inputPanel.setLayout(new GridLayout(3,1));

        //add input textField to inputPanel
        input = new JTextField();
        input.setFont(arial);
        inputPanel.add(input);

        //set playButton styling
        play = new JButton("PLAY");
        play.setPreferredSize(new Dimension(200, 50));
        play.setFont(new Font("arial", Font.BOLD, 30));
        play.setBackground(Color.green);
        play.setForeground(Color.black);
        play.addActionListener(inputHandler);//implements class inputHandler

        //add inputPanel and play button to inputPanel
        emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(236,236,236));
        inputPanel.add(emptyPanel);
        inputPanel.add(play);

        //add inputPanel to container
        container.add(inputPanel);
        window.setVisible(true);

    }

    /***
     * The class InputHandler is an inner class implemented by the play button to handle the user input
     * if the user input is valid,once play button is clicked it closes the window Jframe
     * and generates a new game frame
     */
    public class InputHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String text = input.getText();//get the user input

            String player_name = validate_player_name(text);//validate the input
            window.revalidate();//refresh the frame

            if(player_name != null){//if user enters a valid username
                try {
                    window.dispose();//close the frame
                    Game game = new Game(player_name,0);//generate a new game frame
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


    /***
     * The method validate_player_name() takes a string input and validates it
     * The input is invalid if:
     * its empty ""
     * its numeric "0"
     * longer than 9 characters "longerthan9"
     * shorter than 3 character "xy"
     * it contains blank spaces " "
     * it contains special character "&^"
     * if the input is invalid it also updates the contents of the error label with appropriate error messages
     * @param player_name username entered by the player
     * @return null if the input in invalid or the validated input if its valid
     */
    public String validate_player_name(String player_name) {

        if (player_name.isEmpty()){
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: You have to enter something to continue!");
        }

        if (Game.isNumeric(player_name)) {
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: Your username has to contain some letters.");
        }

        if (player_name.length()>9) {
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: The maximum username length is 9 characters.");
        }
        if (player_name.length()<3 && (!player_name.isEmpty()) &&(!Game.isNumeric(player_name))) {
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: The minimum username length is 3 characters.");
        }
        if ((contains_special_characters(player_name)) || ((contains_whitespaces(player_name)))) {
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: The username cannot contain special characters.");
        }
        if  ((contains_whitespaces(player_name))) {
            JLabel error = getErrorLabel();
            error.setForeground(Color.red);
            error.setText("Error: The username cannot contain blank spaces.");
        }

        if(!player_name.isEmpty() && !(Game.isNumeric(player_name)) && !(player_name.length()>9) &&
                !(player_name.length()<3) &&!(contains_special_characters(player_name)) &&!(contains_whitespaces(player_name))){
            return player_name;
        }

        return null;
    }

    /***
     * The method  contains_special_characters checks every character of the input string for special characters
     * @param player_name username entered by the player
     * @return a boolean value indicating if the input contains special characters or not
     */
    public boolean contains_special_characters(String player_name){
        for (int i = 0; i < player_name.length(); i++) {
            if (!Character.isDigit(player_name.charAt(i)) && !Character.isLetter(player_name.charAt(i)) && !Character.isWhitespace(player_name.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    /***
     * The method  contains_whitespaces checks every character of the input string for blank spaces
     * @param player_name username entered by the player
     * @return a boolean value indicating if the input contains blank spaces or not
     */
    public boolean contains_whitespaces(String player_name){
        for (int i = 0; i < player_name.length(); i++) {
            if (!Character.isDigit(player_name.charAt(i)) && !Character.isLetter(player_name.charAt(i)) && Character.isWhitespace(player_name.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /***
     * The method getInput()
     * @return textField used to get the username which is validated by the play button
     */
    public JTextField getInput(){
        return input;
    }
    /***
     * The method get_play()
     * @return play button which handles the user input
     */
    public  JButton get_play(){
        return play;
    }
    /***
     * The method getErrorLabel()
     * @return error label which displays various errors
     * if user input is invalid
     */
    public JLabel getErrorLabel(){
        return errorLabel;
    }
    /***
     * The method getWindow()
     * @return the main Jframe
     */
    public JFrame getWindow(){
        return window;
    }

    /***
     * Main Method to run the application
     * @param args
     */
    public static void main(String[] args){
        new Start_Menu();
    }


}
