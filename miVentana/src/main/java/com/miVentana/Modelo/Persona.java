package com.miVentana.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Persona implements Serializable{
	private String nombre;
	private double peso;
	private int edad;
	public Persona(String nombre, double peso, int edad) {
		super();
		this.nombre = nombre;
		this.peso = peso;
		this.edad = edad;
	}
	public void hablar() {
		System.out.println("Hola, soy " + nombre);
	}
}
