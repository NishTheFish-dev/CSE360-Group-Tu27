module GroupTu27 {
requires javafx.controls;
requires javafx.fxml;
requires javafx.graphics;
requires java.sql;


opens application to javafx.graphics, javafx.fxml;
opens controllers to javafx.fxml;
    exports application;
}