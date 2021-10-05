package com.miVentana;
/*
Put header here


 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.miVentana.Modelo.Empleado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.commons.io.FileUtils;

public class FXMLController implements Initializable {
    
	@FXML
	private TextField num1;
	
	@FXML
	private TextField num2;
	
    @FXML
    private Label resultado;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtApellido;
    
    @FXML
    private DatePicker fechaNac;
    
    @FXML
    private Label mensaje;
    
    @FXML
    private CheckBox check1, check2, check3;
    
    @FXML
    private RadioButton radio1, radio2;
    
    @FXML
    private ProgressBar barra = new ProgressBar();
    
    private double incremento = 0.0;
    
    @FXML
    private void dividirNumeros(ActionEvent evento) {
    	
    	
    	try {
    		int n1 = Integer.parseInt(num1.getText());
        	int n2 = Integer.parseInt(num2.getText());
    		int res = n1 / n2;
    		resultado.setText(res + "");
    	}catch(ArithmeticException e) {
    		resultado.setText("No divida entre 0 por favor");
    	}catch(NumberFormatException e2) {
    		resultado.setText("No ingrese letras");
    		num1.setText("");
    		num2.setText("");
    	}
    	//continua normalmente
    	
    }
    
    @FXML
    private void registrarEmpleado(ActionEvent evento) {
    	if(txtNombre.getText().length() == 0 || txtApellido.getText().length() == 0)
    	{
    		mensaje.setText("Debe llenar los datos del nombre completo.");
    	}else if(hayNumerosAca(txtNombre.getText()) || hayNumerosAca(txtApellido.getText())) {
    		mensaje.setText("No se admiten números en el nombre o apellido.");
    	}else {
    		LocalDate fechaN = fechaNac.getValue();
    		
    		boolean seleccionados[] = new boolean[3];
    		seleccionados[0] = check1.isSelected();
    		seleccionados[1] = check2.isSelected();
    		seleccionados[2] = check3.isSelected();
    		int tamanyo =0;
    		for(boolean elemento : seleccionados)
    			tamanyo = elemento ? tamanyo + 1 : tamanyo;
    		String funciones[] = new String[tamanyo];
    		tamanyo = 0;
    		for(int i = 0; i < 3; i++) {
    			if(	seleccionados[i]) {
    				String funcion = "";
    				switch(i) {
    				case 0: 
    					funcion = "mensajeria";
    					break;
    				case 1:
    					funcion = "recepcion";
    					break;
    				case 2:
    					funcion = "envios";
    					break;
    				}
    				funciones[tamanyo++] = funcion;
    			}
    				
    		}
    		
    		Empleado emp = new Empleado();
    		emp.nombre = txtNombre.getText();
    		emp.apellido = txtApellido.getText();
    		emp.fechaNac = fechaN;
    		emp.funciones = funciones;
    		System.out.println("Registrado en sistema");
    		mensaje.setText("REGISTRADO!");
    	}
    	barra.setProgress(incremento);
    	incremento += 0.1;
    	System.out.println(incremento);
    }
    
    private boolean hayNumerosAca(String texto) {
		for(int i = 0; i < texto.length(); i++) {
			switch(texto.charAt(i)) {
			case '0': case '1': case '2' :
			case '3': case '4': case '5' :
			case '6': case '7': case '8' :
			case '9': 
				return true;
			}
		}
		return false;
	}
    @FXML
    private void abrirVentana(ActionEvent event) {
    	/*try {
    		Stage escenario = new Stage();
    		FXMLLoader loader = loadFXML("segundo");
    		Region raiz = (Region) loader.load();
    		FXMLsegundoController contro = loader.<FXMLsegundoController>getController();
    		//contro.nombre = "Pepe";
    		contro.setNombre("Pepito");
			Scene escena = new Scene(raiz);//Crear el archivo
			escenario.setScene(escena);
			escenario.setTitle("Segunda ventana");
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	Node nodo = (Node) event.getSource();
    	Stage escenario = (Stage) nodo.getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Elija una imagen");
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("JPG","*.jpg"),
    			new FileChooser.ExtensionFilter("PNG","*.png"),
    			new FileChooser.ExtensionFilter("Todititos","*.*")
    			);
    	File archivo = fileChooser.showOpenDialog(escenario);
    	if(archivo != null) {
    		System.out.println("Archivo cargado con éxito");
    		System.out.println("#########Usando FileUtils:");
    		try {
				List<String> lineas = FileUtils.readLines(archivo);
				for(String linea : lineas)
					System.out.println(linea);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("#########Usando Reader:");
    		try {
				Reader reader = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(archivo)
					)
				);
				if(reader.ready()) {
					char[] letras = new char[120];
					reader.read(letras);
					System.out.println(letras);
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		System.out.println("#########Usando BufferedReader:");
    		try {
				FileInputStream fis = new FileInputStream(archivo);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String linea;
				while((linea = br.readLine()) != null) {
					System.out.println(linea);
				}
				fis.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
			}
    		
    	}
    }
    
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader;
    }

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        //RadioButtons
		final ToggleGroup grupoEstadoCivil = new ToggleGroup();
		radio1.setToggleGroup(grupoEstadoCivil);
		radio2.setToggleGroup(grupoEstadoCivil);
		radio2.setSelected(true);
		
		LocalDate l = LocalDate.now();
		l = LocalDate.of(2000, 12, 25);
		fechaNac.setValue(l);
		
		
    }    
}
