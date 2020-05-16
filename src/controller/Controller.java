package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.management.Query;


import model.data_structures.Queue;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() throws ParseException 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option)
			{
			
			case 0:
				String numeroDeComparendos = modelo.agregarComparendos();
				view.printMessage(numeroDeComparendos);
				String numeroDeEstaciones = modelo.agregarComparendos();
				view.printMessage(numeroDeEstaciones);
				String numeroDeVertices = modelo.cargaVertices();
				view.printMessage(numeroDeVertices + "\n");
				modelo.cargarArcos();
				view.printMessage("El numero de arcos es ");
				break; 

			
			


			default: 
				view.printMessage("--------- \n Opcion Invalida \n---------");
				break;
			}
		}

	}	
}