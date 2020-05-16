package model.data_structures;

public class Lugar {

	private double latitud;
	
	private double longitud;
	
	
	
	public Lugar(double lat, double lon)
	{
		latitud = lat;
		longitud = lon;
	}


	public double getLatitud() {
		return latitud;
	}


	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}


	public double getLongitud() {
		return longitud;
	}


	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public String darDatos()
	{
		return "\nLa latitud es: " + latitud + "\nLa longitud es: " + longitud;
	}
}
