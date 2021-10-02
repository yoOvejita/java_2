package com.miVentana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXMLsegundoController {
	final String nom = "";
	final String pass = "";
	@FXML
	private Label lblMensaje;
	
	@FXML
	private void mostrarMensaje(ActionEvent event) {
		lblMensaje.setText("Bienvenidos a la segunda ventana.");
	}
}
