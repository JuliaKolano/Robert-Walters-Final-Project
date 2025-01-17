module com.finalproject.code {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.finalproject.code to javafx.fxml;
    exports com.finalproject.code;
}