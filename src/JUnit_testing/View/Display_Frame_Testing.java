package View;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Display_Frame_Testing {
    @Test
    @DisplayName("Testing Display Frame")
    void display_frame() {

        Display_Frame display_frame = new Display_Frame("Graph","./src/graph.png");
        assertTrue(display_frame.isDisplayable());//Assert that the display_frame id displayed.

    }
}
