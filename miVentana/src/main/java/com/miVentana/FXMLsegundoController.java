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
			String sql = "INSERT INTO persona (nombre,peso,edad) VALUES(?,?,?)";
	        PreparedStatement ps = con.prepareStatement(sql);
	        //ps.setInt(1, 32);
	        ps.setString(1, "Sofia");
	        ps.setDouble(2, 57.5);
	        ps.setInt(3, 21);
	        ps.executeUpdate();
	        System.out.println("Insertado con éxito");
			*/
			
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
			
			//Modificar un registro
			//actualizar(32, "Pepe", 50.0, 20);
			
			//Elimina registro por id
			//borrar(32);
			
			crearTablas();
			
			String sql3 = "SELECT nombre, edad, peso FROM persona WHERE id > ?";
			PreparedStatement st2 = con.prepareStatement(sql3);
			st2.setInt(1, -1);//Ejemplo, id > 4
			ResultSet rs = st2.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("nombre") + ": " +
						rs.getInt("edad"));
				Persona ppp = new Persona(rs.getString("nombre"),
						rs.getDouble("peso"), rs.getInt("edad"));
				ppp.hablar();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(con != null)
					con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	
	private void actualizar(int id, String nombre, double peso, int edad) {
        String sql = "UPDATE persona SET nombre = ? , "
                + "peso = ? , "
                + "edad = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setDouble(2, peso);
            ps.setInt(3, edad);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public void borrar(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	private void crearTablas() {
		String sql = "CREATE TABLE IF NOT EXISTS materia (\r\n"
				+ "	id integer PRIMARY KEY,\r\n"
				+ "	nombre text NOT NULL\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS cursado (\r\n"
				+ "	id integer\r\n"
				+ "        persona_id integer,\r\n"
				+ "	materia_id integer,\r\n"
				+ "	nota real,\r\n"
				+ "	PRIMARY KEY (id),\r\n"
				+ "	FOREIGN KEY (persona_id) REFERENCES persona (id),\r\n"
				+ "	FOREIGN KEY (materia_id) REFERENCES materia (id)\r\n"
				+ ");";
		Statement st;
		try {
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void agregarCursado(String sigla, int personaId, double nota) {
        String sqlMateria = "INSERT INTO materia(nombre) VALUES(?)";
        String sqlCursado = "INSERT INTO cursado(persona_id,materia_id,nota) VALUES(?,?,?)";
        ResultSet rs = null;
        PreparedStatement psMateria = null, psCursado = null;
        try {
            con.setAutoCommit(false);//Para evitar el envío automático
            psMateria = con.prepareStatement(sqlMateria, Statement.RETURN_GENERATED_KEYS);
            psMateria.setString(1, sigla);
            int filasAfectadas = psMateria.executeUpdate();
            rs = psMateria.getGeneratedKeys();
            int materiaId = 0;
            if (rs.next())
            	materiaId = rs.getInt(1);
            if (filasAfectadas != 1)
            	con.rollback();//ponemos la conexion en su estado inicial
            psCursado = con.prepareStatement(sqlCursado);
            psCursado.setInt(1, personaId);
            psCursado.setInt(2, materiaId);
            psCursado.setDouble(3, nota);
            psCursado.executeUpdate();
            con.commit();
        } catch (SQLException e1) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (psMateria != null) psMateria.close();
                if (psCursado != null) psCursado.close();
                if (con != null) con.close();
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }
}
