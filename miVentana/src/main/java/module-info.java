module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	requires org.apache.commons.io;
	requires java.sql;
    opens com.miVentana to javafx.fxml;
    exports com.miVentana;
}