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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	Connection con = null;
	
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
	private void interactuarConDDBB(ActionEvent event) {
		
		try {
			//Creará la base de datos miDDBB.db si no existe; caso contrario, se conecta
			String url = "jdbc:sqlite:C:/Users/rusok/Desktop/miDDBB.db";
			con = DriverManager.getConnection(url);
			DatabaseMetaData meta = con.getMetaData();
			System.out.println("Conexion exitosa usando: " + meta.getDriverName());
			
			/*
			Statement st = con.createStatement();
			st.execute("CREATE TABLE persona ("
					+ "id integer PRIMARY KEY,"
					+ "nombre text NOT NULL,"
					+ "peso double NOT NULL,"
					+ "edad integer NOT NULL);");
			*/
			//Insert
			
			/*
			String sql = "INSERT INTO persona (id,nombre,peso,edad) VALUES(?,?,?,?)";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, 32);
	        ps.setString(2, "Pepe");
	        ps.setDouble(3, 62.5);
	        ps.setInt(4, 40);
	        ps.executeUpdate();
	        System.out.println("Insertado con éxito");*/
			
			/*String sql2 = "SELECT * FROM persona";
			Statement st2 = con.createStatement();
			ResultSet rs = st2.executeQuery(sql2);
			
			while(rs.next()) {
				System.out.println(rs.getString("nombre") + ": " +
						rs.getInt("edad"));
				Persona ppp = new Persona(rs.getString("nombre"),
						rs.getDouble("edad"), rs.getInt("edad"));
				ppp.hablar();
			}*/
			
			String sql3 = "SELECT nombre, edad, peso FROM persona WHERE id > ?";
			PreparedStatement st2 = con.prepareStatement(sql3);
			st2.setInt(1, 55);//Ejemplo, id > 55
			ResultSet rs = st2.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("nombre") + ": " +
						rs.getInt("edad"));
				Persona ppp = new Persona(rs.getString("nombre"),
						rs.getDouble("peso"), rs.getInt("edad"));
				ppp.hablar();
			}
			
		} catch (SQLException e) {
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
