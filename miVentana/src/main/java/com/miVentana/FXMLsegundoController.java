package com.miVentana;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.miVentana.Modelo.Persona;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLsegundoController {
	final String mensaje = "Bienvenido ";
	public String nombre = "";
	@FXML
	private Label lblMensaje;
	
	@FXML
	private TextField txtContenido;
	
	@FXML
	private void mostrarMensaje(ActionEvent event) {
		lblMensaje.setText(mensaje + nombre);
	}
	
	@FXML
	private void guardar1(ActionEvent event) {
		String val = txtContenido.getText();
		try {
			OutputStream out = new FileOutputStream("salidita.com");
			out.write(val.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void guardar2(ActionEvent event) {
		String val = txtContenido.getText();
		try {
			ByteArrayOutputStream outByte = new ByteArrayOutputStream();
			int[] arrEnt = {1,2,3,5,7,11,13};
			outByte.write(arrEnt.toString().getBytes());
			outByte.flush();
			OutputStream out = new FileOutputStream("salidita.txt");
			out.write(outByte.toByteArray());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void guardar3(ActionEvent event) {
		Persona p = new Persona("Pepe", 50.5, 27);
		try {
			/*
			FileOutputStream out = new FileOutputStream("objetito.txt");
			ObjectOutputStream obj = new ObjectOutputStream(out);
			obj.writeInt(101);
			obj.writeObject(p);
			obj.flush();
			obj.close();
			out.close();
			*/
			
			//Lo abrimos
			FileInputStream in = new FileInputStream("objetito.txt");
			ObjectInputStream obj2 = new ObjectInputStream(in);
			int valorLeido = obj2.readInt();
			Persona per = (Persona) obj2.readObject();
			per.hablar();
			System.out.println(valorLeido);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void guardarArchivo(ActionEvent event) {
    	String valor = "Mensaje cualquiera";
    	Node node = (Node) event.getSource();
        Stage stageActual = (Stage) node.getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("archivos txt (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File archivo = fileChooser.showSaveDialog(stageActual);
        if (archivo != null)
            textoArchivo(valor, archivo);
	}
	private void textoArchivo(String texto, File arch) {
    	try {
            PrintWriter writer;
            writer = new PrintWriter(arch);
            writer.println(texto);
            writer.close();
        } catch (IOException e) {
            //Tratando el error
        }
	}
	
	public void setNombre(String txt) {
		nombre = txt;
	}
}
