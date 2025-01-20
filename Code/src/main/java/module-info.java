module com.finalproject.code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.finalproject.code to javafx.fxml;
    exports com.finalproject.code;
}