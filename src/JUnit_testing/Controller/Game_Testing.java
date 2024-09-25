package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Game_Testing {
    Game game;

    @BeforeEach
    void setUp() throws IOException {
        game = new Game("User", 0);
    }

    /***
     * Scenario-5:
     * {User Action} = User enters a valid username
     * {Result} = The graph frame is displayed
     */
    @Test
    @DisplayName("Testing scenario5")
    void scenario1_test() {
        assertTrue(game.get_graph_frame().isDisplayable());//Assert that the game frame is displayed
        assertEquals("User", game.get_player_name_label().getText());//Assert that the player name Jlabel contains the correct username
        assertEquals("0", game.get_total_score_label().getText());//Assert that the total score  Jlabel contains the correct value
        assertEquals(game.get_difficulty(), game.get_difficulty_level().getText());//Assert that the difficulty Jlabel contains the correct difficulty level
        assertTrue(game.get_start_node_label().isDisplayable());//Assert that the start node Jlabel is displayed
        assertTrue(game.get_goal_node_label().isDisplayable());//Assert that the goal node Jlabel is displayed
        assertTrue(game.get_distance_text_field().isDisplayable());//Assert that the distance text field is displayed
        assertTrue(game.get_distance_label().isDisplayable());//Assert that the distance Jlabel is displayed
        assertTrue(game.get_distance_text_field().isDisplayable());//Assert that the distance text field is displayed
        assertTrue(game.get_submit_button().isDisplayable());//Assert that the submit button is displayed
        assertTrue(game.get_restart_button().isDisplayable());//Assert that the restart button is displayed
    }


    /***
     * Scenario-6
     * {User Action} = User clicks "SUBMIT" button without entering a guess for the distance
     * {Result} = User is show error message:"Error:You have not entered a value!"
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario6")
    void scenario6() throws IOException {

        game.get_submit_button().doClick();//click the submit button

        String expected_error = "Error:You have not entered a value!";
        String actual_error = game.get_error_label().getText();

        assertTrue(game.get_distance_text_field().getText().isEmpty());//Assert that the distance text field is empty
        assertFalse(game.get_error_label().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error, actual_error);//Assert that the correct error is displayed

    }

    /***
     * Scenario-6.1
     * {User Action} = Users enter invalid guess(anything other than a number) for the distance and clicks "SUBMIT" button
     * {Result} = User is show error message:"Error:Your input is invalid!"
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario6.1")
    void scenario6_edgecase1() throws IOException {

        game.get_distance_text_field().setText("String");//input something invalid
        game.get_submit_button().doClick();//click the submit button

        String expected_error = "Error:Your input is invalid!";
        String actual_error = game.get_error_label().getText();

        assertFalse(game.get_error_label().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error, actual_error);//Assert that the correct error is displayed

    }


    /***
     * Scenario-6.2
     * {User Action} = User enters a negative number for the distance and clicks "SUBMIT" button
     * {Result} = User is show error message:"Error:Entered negative number."
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario6.2")
    void scenario6_edgecase2() throws IOException {

        game.get_distance_text_field().setText("-1");//input a negative number
        game.get_submit_button().doClick();//click the submit button

        String expected_error = "Error:Entered negative number.";
        String actual_error = game.get_error_label().getText();

        assertFalse(game.get_error_label().getText().isEmpty());//Assert that the error is displayed
        assertEquals(expected_error, actual_error);//Assert that the correct error is displayed

    }

    /***
     * Scenario-6.3 is in =>JUnit_testing/View/Results_Frame_Testing
     */

    /***
     * Scenario-6.4
     * {User Action} = User enters a valid input for the distance and clicks "SUBMIT" button
     * {Result} = "CORRECT ROUTE" button is made visible on the panel
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario6.4")
    void scenario6_edgecase4() throws IOException {

        game.get_distance_text_field().setText("3");//input a valid number
        game.get_submit_button().doClick();//click the submit button

        assertTrue(game.get_correct_route_button().isDisplayable());//Assert that the "Correct Route" button is displayed

    }

    /***
     * Scenario-7 is in=>JUnit_testing/View/Correct_Route_Frame_Testing
     */

    /***
     * Scenario-8
     * {User Action} = User clicks "RESTART" button
     * {Result} = Game is restarted with and the total score is updated
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario8")
    void scenario8() throws IOException {

        game.get_distance_text_field().setText("3");//input a valid number
        game.get_submit_button().doClick();//click the submit button

        assertTrue(game.get_restart_button().isDisplayable());//Assert that the "RESTART" button is displayed

        game.get_restart_button().doClick();//click restart button

        assertEquals(game.get_restart_count(), 1);//Assert that the restart count has been incremented,meaning the game has restarted

        game.get_restart_button().doClick();//click restart button

        assertNotEquals(0, game.get_new_total_score()); //Assert that the total score has been updated

    }

    @Test
    @DisplayName("Testing method validate_distance_text_field()")
    void validate_distance_value() throws IOException {

        assertNull(game.validate_distance_text_field(""));//Assert that the null is returned with empty string as input
        assertNull(game.validate_distance_text_field("String"));//Assert that null is returned with non integer as input
        assertNull(game.validate_distance_text_field("1234"));//Assert that null is returned if with input longer than 3 digits
        assertEquals("1", game.validate_distance_text_field("1"));//Assert that correct value is valid input is entered

    }

}
