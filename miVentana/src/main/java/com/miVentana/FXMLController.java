package com.miVentana;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.miVentana.Modelo.Empleado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

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
    	try {
    		Stage escenario = new Stage();
			Scene escena = new Scene(loadFXML("segundo"));//Crear el archivo
			escenario.setScene(escena);
			escenario.setTitle("Segunda ventana");
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
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
