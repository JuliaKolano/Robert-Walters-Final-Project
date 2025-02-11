import com.finalproject.code.LoginController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    // Set up the UI and Controller references
    private LoginController loginController;
    private TextField usernameField;
    private TextField passwordField;
    private Label errorMessage;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            loginController = new LoginController();
            usernameField = new TextField();
            passwordField = new TextField();
            errorMessage = new Label();

            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;
            loginController.errorMessage = errorMessage;
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void EmptyUsernameShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("");
            passwordField.setText("Password1");
            loginController.onLoginButtonClick(null);

            assertEquals("Username is required.", errorMessage.getText());
        });
    }

    @Test
    void EmptyPasswordShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("username");
            passwordField.setText("");
            loginController.onLoginButtonClick(null);

            assertEquals("Password is required.", errorMessage.getText());
        });
    }

    @Test
    void InvalidCredentialsShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("username");
            passwordField.setText("password");
            loginController.onLoginButtonClick(null);

            assertEquals("Invalid username or password.", errorMessage.getText());
        });
    }

    @Test
    void ValidCredentialsShouldClearErrorMessage() {
        runLater(() -> {
            usernameField.setText("julia");
            passwordField.setText("Password1");
            loginController.onLoginButtonClick(null);

            assertTrue(errorMessage.getText().isEmpty() || errorMessage.getText().equals(""));
        });
    }

    @Test
    void ClickingOnSignUpButtonShouldClearErrorMessage() {
        runLater(() -> {
            loginController.onSignUpButtonClick(null);

            assertTrue(errorMessage.getText().isEmpty() || errorMessage.getText().equals(""));
        });
    }
}
