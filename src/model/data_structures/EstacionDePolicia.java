package model.data_structures;

public class EstacionDePolicia {
	
	
	private int objectID;
	
	private String epodescrip;
	private String eposervicio;
	private String epohorario;
	private String epotelefon;
	private String epoiulocal;
	private String epodir_sitio;
	
	private double epolatitud;
	private double epolongitud;
	
	
	
	

	public EstacionDePolicia(int OBJECTID, String DESCRIP, String  EPODIR_SITIO,double  EPOLATITUD ,double  EPOLONGITU,
			String EPOSERVICIO, String EPOHORARIO, String EPOTELEFON, String EPOIULOCAL)
	{
		
		objectID = OBJECTID;
		
		epodescrip = DESCRIP;
		epodir_sitio = EPODIR_SITIO;
		eposervicio = EPOSERVICIO;
		epohorario = EPOHORARIO;
		epotelefon = EPOTELEFON;
		epoiulocal = EPOIULOCAL;
		
		epolatitud = EPOLATITUD;
		epolongitud = EPOLONGITU;
		
	}

	public String darDatos()
	{
		return "\nOBJECTID: " + objectID + "\nDescripcion: " + epodescrip +
				"\nDireccion del sitio : " + epodir_sitio + "\nServicio: "+ eposervicio 
				+"\nHorario: " + epohorario + "\nTelefono: " + epotelefon
				+"\nNumero de local" + epoiulocal + "\n";
	}
	
	
	public int getObjectID() {
		return objectID;
	}

	public String getEpodescrip() {
		return epodescrip;
	}

	public String getEposervicio() {
		return eposervicio;
	}

	public String getEpohorario() {
		return epohorario;
	}

	public String getEpotelefon() {
		return epotelefon;
	}

	public String getEpoiulocal() {
		return epoiulocal;
	}

	public String getEpodir_sitio() {
		return epodir_sitio;
	}
	
	public double getEpolatitud() {
		return epolatitud;
	}

	public double getEpolongitud() {
		return epolongitud;
	}
	
}
