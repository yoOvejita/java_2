package com.miVentana;
/*
Put header here


 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {
    
	@FXML
	private TextField num1;
	
	@FXML
	private TextField num2;
	
    @FXML
    private Label resultado;
    
    
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
