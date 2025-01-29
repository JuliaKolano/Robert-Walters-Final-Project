module com.finalproject.code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires okhttp3;
    requires org.json;

    opens com.finalproject.code to javafx.fxml;
    exports com.finalproject.code;
}