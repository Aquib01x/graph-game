package View;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Results_Frame_Testing {


    /***
     * Scenario-6.3
     * {User Action} = User enters a valid input for the distance and clicks "SUBMIT" button
     * {Result} = Game results are displayed
     * @throws IOException if file path is invalid
     */
    @Test
    @DisplayName("Testing scenario6.3")
    void scenario6_edgecase3() throws IOException {
        Results_Frame results_frame =
                new Results_Frame(50,"Player1","easy","2",4);//results frame with manually set inputs

        assertTrue(results_frame.isDisplayable());//Assert that the results_frame id displayed.
        assertEquals("50",results_frame.get_score_label().getText());//Assert that the score Jlabel contains the correct score
        assertEquals("Player1",results_frame.get_player_name_label().getText());//Assert that the Jlabel contains the correct name
        assertEquals("easy",results_frame.get_difficulty_label().getText());//Assert that the Jlabel contains the correct difficulty
        assertEquals("2",results_frame.get_user_guess_label().getText());//Assert that the Jlabel contains  the correct guess
        assertEquals(results_frame.get_correct_answer_label().getText(),Integer.toString(4));//Assert that the Jlabel contains the correct answer

        String expected_score = "50"; //score = accuracy*difficulty
        assertEquals(expected_score,results_frame.get_score_label().getText());//Assert that the Jlabel contains the correct score

    }
}
