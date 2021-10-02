module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
    opens com.miVentana to javafx.fxml;
    exports com.miVentana;
}