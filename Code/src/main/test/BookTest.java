import com.finalproject.code.BookController;
import com.finalproject.code.SearchBooksController;
import com.finalproject.code.classes.Book;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookTest {

    // Set up the UI and Controller references
    private BookController bookController;
    private SearchBooksController searchBooksController;
    private Label bookTitle;
    private Label bookAuthor;
    private Label bookGenre;
    private Label bookPages;
    private ImageView bookCover;
    private Button addButton;

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {
            bookController = new BookController();
            searchBooksController = Mockito.mock(SearchBooksController.class);

            bookTitle = new Label();
            bookAuthor = new Label();
            bookGenre = new Label();
            bookPages = new Label();
            bookCover = new ImageView();
            addButton = new Button();

            bookController.bookTitle = bookTitle;
            bookController.bookAuthor = bookAuthor;
            bookController.bookGenre = bookGenre;
            bookController.bookPages = bookPages;
            bookController.bookCover = bookCover;
            bookController.addButton = addButton;

            bookController.setSearchBooksController(searchBooksController);
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void SetBookDataShouldPopulateAllTheBookObjectFields() {
        runLater(() -> {
            Book book = new Book("Test Title", "Test Author", "Fiction", 300, "http://example.com/cover.jpg");
            bookController.setBookData(book);

            assertEquals("Test Title", bookTitle.getText());
            assertEquals("Author: Test Author", bookAuthor.getText());
            assertEquals("Genre: Fiction", bookGenre.getText());
            assertEquals("Pages: 300", bookPages.getText());
        });
    }

    @Test
    void OnMouseEnterMethodShouldDisplayTheAddButton() {
        runLater(() -> {
            addButton.setVisible(false);
            bookController.onMouseEnter();
            assertTrue(addButton.isVisible());
        });
    }

    @Test
    void OnMouseExitMethodShouldHideTheAddButton() {
        runLater(() -> {
            addButton.setVisible(true);
            bookController.onMouseExit();
            assertFalse(addButton.isVisible());
        });
    }

    @Test
    void AddButtonClickedMethodShouldDisplayAnErrorSnackbarOnceWhenTheBookObjectIsNull() {
        runLater(() -> {
            bookController.onAddButtonClicked();
            verify(searchBooksController, times(1)).showSnackbar("There was an error adding the book to the library");
        });
    }
}
