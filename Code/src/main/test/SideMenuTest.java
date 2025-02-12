import com.finalproject.code.SideMenuController;
import com.finalproject.code.classes.User;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.*;

public class SideMenuTest {

    // Set up the UI and Controller references
    private SideMenuController controller;
    private ImageView profilePicture;
    private Label username;
    private Label errorMessage;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            controller = new SideMenuController();
            profilePicture = new ImageView();
            username = new Label();
            errorMessage = new Label();

            controller.profilePicture = profilePicture;
            controller.userName = username;
            controller.errorMessage = errorMessage;
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void UserDetailsShouldBeDisplayedIfUserExists() {
        runLater(() -> {
            User mockUser = User.getInstance();
            assert mockUser != null;
            mockUser.setUsername("TestUser");
            mockUser.setProfilePicturePath("profile.png");

            controller.initialize();

            assertEquals("TestUser", username.getText());
            assertNotNull(profilePicture.getImage());
        });
    }

    @Test
    void DefaultValuesShouldBeDisplayedIfUserDoesNotExist() {
        runLater(() -> {
            controller.initialize();

            assertEquals("Unknown User", username.getText());
            assertNotNull(profilePicture.getImage());
        });
    }

    @Test
    void ErrorMessageShouldAppearIfLibraryPageFailsToLoad() {
        runLater(() -> {
            controller.onMyLibraryButtonClick(null);
            assertTrue(errorMessage.isVisible());
            assertEquals("There was a problem loading the page", errorMessage.getText());
        });
    }

    @Test
    void ErrorMessageShouldAppearIfSearchPageFailsToLoad() {
        runLater(() -> {
            controller.onSearchBooksButtonClick(null);
            assertTrue(errorMessage.isVisible());
            assertEquals("There was a problem loading the page", errorMessage.getText());
        });
    }

    @Test
    void ErrorMessageShouldAppearIfReadingGoalPageFailsToLoad() {
        runLater(() -> {
            controller.onReadingGoalButtonClick(null);
            assertTrue(errorMessage.isVisible());
            assertEquals("There was a problem loading the page", errorMessage.getText());
        });
    }
}
