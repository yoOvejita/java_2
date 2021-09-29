module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.miVentana to javafx.fxml;
    exports com.miVentana;
}