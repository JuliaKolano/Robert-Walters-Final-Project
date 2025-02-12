import com.finalproject.code.ReadingGoalController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.*;

public class ReadingGoalTest {

    // Set up the UI and Controller references
    private ReadingGoalController controller;
    private TextField readingGoalField;
    private ToggleButton readingGoalToggle;
    private HBox readingGoalReached;
    private Label readingGoalMessage;
    private Label errorMessage;
    private Label snackbarLabel;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            controller = new ReadingGoalController();
            readingGoalField = new TextField();
            readingGoalToggle = new ToggleButton();
            readingGoalReached = new HBox();
            readingGoalMessage = new Label();
            errorMessage = new Label();
            snackbarLabel = new Label();

            controller.readingGoalField = readingGoalField;
            controller.readingGoalToggle = readingGoalToggle;
            controller.readingGoalReached = readingGoalReached;
            controller.readingGoalMessage = readingGoalMessage;
            controller.errorMessage = errorMessage;
            controller.snackbarLabel = snackbarLabel;
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void ReadingGoalShouldUpdateAndNoErrorMessageShouldBeDisplayedWhenValidInputIsGiven() {
        runLater(() -> {
            readingGoalField.setText("10");
            controller.onUpdateButtonClicked();
            assertFalse(errorMessage.isVisible());
            assertEquals("", errorMessage.getText());
        });
    }

    @Test
    void EmptyReadingGoalShouldReturnAnErrorMessage() {
        runLater(() -> {
            readingGoalField.setText("");
            controller.onUpdateButtonClicked();
            assertTrue(errorMessage.isVisible());
            assertEquals("Please enter a sensible number", errorMessage.getText());
        });
    }

    @Test
    void NonNumericReadingGoalShouldReturnAnErrorMessage() {
        runLater(() -> {
            readingGoalField.setText("hello");
            controller.onUpdateButtonClicked();
            assertTrue(errorMessage.isVisible());
            assertEquals("Please enter a sensible number", errorMessage.getText());
        });
    }

    @Test
    void ZeroOrNegativeReadingGoalShouldReturnAnErrorMessage() {
        runLater(() -> {
            readingGoalField.setText("-5");
            controller.onUpdateButtonClicked();
            assertTrue(errorMessage.isVisible());
            assertEquals("The reading goal must be greater than 0", errorMessage.getText());
        });
    }

    @Test
    void ReadingGoalLargerThanSmallintShouldReturnAnErrorMessage() {
        runLater(() -> {
            readingGoalField.setText("40000");
            controller.onUpdateButtonClicked();
            assertTrue(errorMessage.isVisible());
            assertEquals("The reading goal cannot be that large", errorMessage.getText());
        });
    }

    @Test
    void OnReadingGoalToggledMethodShouldUpdateTheReadingGoalToggleAccordingly() {
        runLater(() -> {
            readingGoalToggle.setSelected(false);
            controller.onReadingGoalToggled();
            assertTrue(readingGoalToggle.isSelected());
            assertEquals("Yes", readingGoalToggle.getText());

            controller.onReadingGoalToggled();
            assertFalse(readingGoalToggle.isSelected());
            assertEquals("No", readingGoalToggle.getText());
        });
    }
}
