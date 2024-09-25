package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Start_Menu_Testing {
    Start_Menu start_menu;
    @BeforeEach
    void setUp() throws IOException {
        start_menu = new Start_Menu();
    }

    /***
     * Scenario-1:
     * {User Action} = User clicks on the JAR to execute the program
     * {Result} = The startup window is displayed
     */
    @Test
    @DisplayName("Testing scenario1")
    void scenario1_test() {
        assertTrue(start_menu.getWindow().isDisplayable());//Assert that the window frame is displayed
    }

    /***
     * Scenario-2:
     * {User Action} = User clicks "PLAY" button without entering a username
     * {Result} = User is shown error message:"You have to enter a username to continue"
     */
    @Test
    @DisplayName("Testing scenario2")
    void scenario2_test() {

        start_menu.get_play().doClick();//click play button without any input

        String expected_error = "Error: You have to enter something to continue!";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed

    }

    /***
     * Scenario-2.1:
     * {User Action} = User enters a username with only characters and clicks "PLAY" button
     * {Result} = User is shown error message:"Error:Your username has to contain some letters."
     */
    @Test
    @DisplayName("Testing scenario2.1")
    void scenario2_edgecase1() {

        start_menu.getInput().setText("22");//input only numbers
        start_menu.get_play().doClick();//click play button

        String expected_error = "Error: Your username has to contain some letters.";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed


    }

    /***
     * Scenario-2.2:
     * {User Action} = User enters a username more than 9 characters long and clicks "PLAY" button
     * {Result} = User is shown error message:"Error: The maximum username length is 9 characters."
     */
    @Test
    @DisplayName("Testing scenario2.2")
    void scenario2_edgecase2() {

        start_menu.getInput().setText("morethanine");//input a string more than 9 characters
        start_menu.get_play().doClick();//click play button

        String expected_error = "Error: The maximum username length is 9 characters.";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed


    }

    /***
     * Scenario-2.3:
     * {User Action} = User enters a username less than 3 characters long and clicks "PLAY" button
     * {Result} = User is show error message:"Error:The minimum username length is 3 characters."
     */
    @Test
    @DisplayName("Testing scenario2.3")
    void scenario2_edgecase3() {

        start_menu.getInput().setText("tt");//input a string with less than 3 characters
        start_menu.get_play().doClick();//click play button

        String expected_error = "Error: The minimum username length is 3 characters.";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed


    }

    /***
     * Scenario-2.4:
     * {User Action} = User enters a username with special characters and clicks "PLAY" button
     * {Result} = User is shown error message:"Error:The username cannot contain special characters"
     */
    @Test
    @DisplayName("Testing scenario2.4")
    void scenario2_edgecase4() {

        start_menu.getInput().setText("$£");//input a string with special characters
        start_menu.get_play().doClick();//click play button

        String expected_error = "Error: The username cannot contain special characters.";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed


    }

    /***
     * Scenario-2.5:
     * {User Action} = User enters a username with blank spaces and clicks "PLAY" button
     * {Result} = User is shown error message:"Error:The username cannot contain blank spaces"
     */
    @Test
    @DisplayName("Testing scenario2.5")
    void scenario2_edgecase5() {

        start_menu.getInput().setText("  ");//input a string with blank spaces
        start_menu.get_play().doClick();//click play button

        String expected_error = "Error: The username cannot contain blank spaces.";
        String actual_error = start_menu.getErrorLabel().getText();

        assertFalse(start_menu.getErrorLabel().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error,actual_error);//Assert that the correct error is displayed

    }
}
