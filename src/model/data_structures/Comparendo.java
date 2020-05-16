package model.data_structures;

import java.util.Date;

public class Comparendo implements Comparable<Comparendo>
{
	private int objectId;
	
	private Date fecha_hora;
	private String clase_vehi;
	private String tipo_servi;
	private String infraccion;
	private String localidad;

	private double latitud;
	private double longitud;
	


	public Comparendo(int objeId, Date fecha, String claseVeh, String tipoSer, String codInfraccion, String localidadP, double lonP, double latP)
	{
		objectId = objeId;
		fecha_hora = fecha;

		clase_vehi = claseVeh;
		tipo_servi = tipoSer;
		infraccion = codInfraccion;
		localidad = localidadP;
		longitud = lonP;
		latitud = latP;


	}
	
	
	public String darDatos()
	{
		return "\nOBJECTID: " + objectId + "\nFECHA_HORA: " + fecha_hora.toString() + "\nINFRACCION: " + infraccion + "\nCLASE_VEHI: "+ clase_vehi +"\nTIPO_SERVI: " + tipo_servi + "\nLOCALIDAD: " + localidad;
	}

	

	public int darId()
	{
		return  objectId;
	}

	public Date darFechaHora()
	{
		return fecha_hora;
	}

	public String darClase()
	{
		return clase_vehi;
	}

	public String darTipo()
	{
		return tipo_servi;
	}

	public String darInfraccion()
	{
		return infraccion;
	}


	public String darLocalidad()
	{
		return localidad;
	}


	public double darLongitud()
	{
		return longitud;
	}

	public double darLatitud()
	{
		return latitud;
	}

	public boolean parametroFecha(Date pFecha)
	{
		return fecha_hora.getMonth() == pFecha.getMonth() && fecha_hora.getDate() == pFecha.getDate();
	}

	public boolean parametroInfraccion(String pInfraccion) 
	{
		return infraccion.equals(pInfraccion);
	}

	public boolean parametroLocalidad(String pLocalidad)
	{
		return localidad.equals(pLocalidad);
	}

	public boolean parametroTipo(String pTipo) 
	{
		return tipo_servi.equals(pTipo);
	}
	@Override
	public int compareTo(Comparendo o) {
		if(o.darId() - objectId == 0 )return 0;
		else if(o.darId() - objectId < 0) return -1;
		else return 1;
	}
	
	
}