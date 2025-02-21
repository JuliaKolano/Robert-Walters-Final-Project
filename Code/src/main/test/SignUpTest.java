import com.finalproject.code.SignUpController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {

    // Set up all the UI and Controller references
    private SignUpController signUpController;
    private TextField usernameField;
    private TextField passwordField;
    private TextField repeatPasswordField;
    private Label errorMessage;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            signUpController = new SignUpController();
            usernameField = new TextField();
            passwordField = new TextField();
            repeatPasswordField = new TextField();
            errorMessage = new Label();

            signUpController.usernameField = usernameField;
            signUpController.passwordField = passwordField;
            signUpController.repeatPasswordField = repeatPasswordField;
            signUpController.errorMessage = errorMessage;
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void EmptyUsernameShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("");
            passwordField.setText("Password1");
            repeatPasswordField.setText("Password1");
            signUpController.onSignUpButtonClick(null);

            assertEquals("Username is required.", errorMessage.getText());
        });
    }

    @Test
    void MismatchedPasswordsShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("username");
            passwordField.setText("Password1");
            repeatPasswordField.setText("Password2");
            signUpController.onSignUpButtonClick(null);

            assertEquals("Passwords do not match.", errorMessage.getText());
        });
    }

    @Test
    void UsernameThatIsTooLongShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("VeryLongUsername12345");
            passwordField.setText("Password1");
            repeatPasswordField.setText("Password1");
            signUpController.onSignUpButtonClick(null);

            assertEquals("Username cannot be longer than 15 characters.", errorMessage.getText());
        });
    }

    @Test
    void PasswordThatIsTooLongShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("username");
            passwordField.setText("VeryLongPassword12345");
            repeatPasswordField.setText("VeryLongPassword12345");
            signUpController.onSignUpButtonClick(null);

            assertEquals("Password cannot be longer than 20 characters.", errorMessage.getText());
        });
    }

    @Test
    void InvalidPasswordShouldReturnAnError() {
        runLater(() -> {
            usernameField.setText("validUser");
            passwordField.setText("password");
            repeatPasswordField.setText("password");
            signUpController.onSignUpButtonClick(null);

            assertEquals("Password must be at least 8 characters long and contain at least one number and capital letter.", errorMessage.getText());
        });
    }
}
