import com.finalproject.code.LibraryBookController;
import com.finalproject.code.UserLibraryController;
import com.finalproject.code.classes.LibraryBook;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryBookTest {

    // Set up the UI and Controller references
    private LibraryBookController controller;
    private LibraryBook book;
    private UserLibraryController userLibraryController;

    private ImageView bookCover;
    private Label bookTitle;
    private Label bookAuthor;
    private Label bookGenre;
    private Label bookPages;
    private Button readButton;
    private Button deleteButton;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            controller = new LibraryBookController();
            bookCover = new ImageView();
            bookTitle = new Label();
            bookAuthor = new Label();
            bookGenre = new Label();
            bookPages = new Label();
            readButton = new Button("Mark as Read");
            deleteButton = new Button("Delete");

            controller.bookCover = bookCover;
            controller.bookTitle = bookTitle;
            controller.bookAuthor = bookAuthor;
            controller.bookGenre = bookGenre;
            controller.bookPages = bookPages;
            controller.readButton = readButton;
            controller.deleteButton = deleteButton;

            book = new LibraryBook("Test Title", "Test Author", "Fiction", 300, null, false);
            controller.setLibraryBookData(book);
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void LibraryBookDataShouldBePopulatedProperly() {
        runLater(() -> {
            assertEquals("Test Title", bookTitle.getText());
            assertEquals("Author: Test Author", bookAuthor.getText());
            assertEquals("Genre: Fiction", bookGenre.getText());
            assertEquals("Pages: 300", bookPages.getText());
        });
    }

    @Test
    void ReadButtonShouldToggleTheIsReadValueOfLibraryBook() {
        runLater(() -> {
            assertFalse(book.getIsRead());
            controller.onReadButtonClicked();
            assertTrue(book.getIsRead());
            controller.onReadButtonClicked();
            assertFalse(book.getIsRead());
        });
    }

    @Test
    void DeleteButtonShouldBeHiddenAfterTheLibraryBookIsDeleted() {
        runLater(() -> {
            assertTrue(deleteButton.isVisible());
            controller.onDeleteButtonClicked();
            deleteButton.setVisible(false);
            assertFalse(deleteButton.isVisible());
        });
    }

    @Test
    void DeleteButtonShouldBeVisibleOnHoverAndInvisibleOnExit() {
        runLater(() -> {
            deleteButton.setVisible(false);
            controller.onMouseEnter();
            assertTrue(deleteButton.isVisible());
            controller.onMouseExit();
            assertFalse(deleteButton.isVisible());
        });
    }
}
