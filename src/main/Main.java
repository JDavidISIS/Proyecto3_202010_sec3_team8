package main;
import java.text.ParseException;

import controller.Controller;

public class Main {
	
	public static void main(String[] args) 
	{
		Controller controler = new Controller();
		try {
			controler.run();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
}
