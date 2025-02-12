import com.finalproject.code.UserLibraryController;
import com.finalproject.code.classes.LibraryBook;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserLibraryTest {

    // Set up the UI and Controller references
    private UserLibraryController controller;
    private FlowPane bookFlowPane;
    private Label snackbarLabel;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            controller = new UserLibraryController();
            bookFlowPane = new FlowPane();
            snackbarLabel = new Label();
            controller.bookFlowPane = bookFlowPane;
            controller.snackbarLabel = snackbarLabel;

            bookFlowPane.getChildren().clear();
            snackbarLabel.setText("");
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void PopulateUserLibraryMethodShouldDisplayAllTheCreatedLibraryBookObjects() {
        runLater(() -> {
            Arrays.asList(
                    new LibraryBook("Book 1", "Author 1", "Genre 1", 100, null, false),
                    new LibraryBook("Book 2", "Author 2", "Genre 2", 200, null, true)
            );

            controller.populateUserLibrary();
            assertEquals(2, bookFlowPane.getChildren().size());
        });
    }

    @Test
    void PopulateUserLibraryMethodShouldDisplayASnackbarMessageWhenNoBookObjectsWereCreated() {
        runLater(() -> {
            controller.populateUserLibrary();
            assertEquals("There are no books in the library yet", snackbarLabel.getText());
            assertEquals(0, bookFlowPane.getChildren().size());
        });
    }

    @Test
    void ShowSnackbarMethodShouldDisplayTheSnackbarMessage() {
        runLater(() -> {
            controller.showSnackbar("Test Message");
            assertEquals("Test Message", snackbarLabel.getText());
        });
    }
}
