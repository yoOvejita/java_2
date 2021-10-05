package com.miVentana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXMLsegundoController {
	final String mensaje = "Bienvenido ";
	public String nombre = "";
	@FXML
	private Label lblMensaje;
	
	@FXML
	private void mostrarMensaje(ActionEvent event) {
		lblMensaje.setText(mensaje + nombre);
	}
	
	public void setNombre(String txt) {
		nombre = txt;
	}
}
