package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.*;


public class Modelo {

	public static String PATHcerdos = "./data/estacionpolicia.geojson";

	public static String PATHvertices = "./data/bogota_vertices.txt";

	public static String PATHarcos = "./data/bogota_arcos.txt";

	public static String PATHarcosVertices = "./data/grafo";

	public static String PATHcomparendos = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";

	public Queue<EstacionDePolicia> listaCerdos;

	public Queue<Comparendo> listaComparendos;

	int mayorId = 0;

	private MiGrafo<Integer, Lugar> grafito;



	public Modelo()
	{
		listaCerdos = new Queue<EstacionDePolicia>();
		listaComparendos = new Queue<Comparendo>();
	}


	public  String agregarEstacionesDeCerdos()
	{

		JsonReader reader1;
		try 
		{

			reader1 = new JsonReader(new FileReader(PATHcerdos));
			JsonElement elem = JsonParser.parseReader(reader1);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			for(JsonElement e: e2) 
			{
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String DESCRIP = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_ENT").getAsString();
				String EPODIR_SITIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
				double EPOLATITUD = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLATITUD").getAsDouble();
				double EPOLONGITU = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOLONGITU").getAsDouble();
				String EPOSERVICIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOSERVICIO").getAsString();
				String EPOHORARIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOHORARIO").getAsString();
				String EPOTELEFON = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsString();
				String EPOIULOCAL = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIULOCAL").getAsString();


				EstacionDePolicia c = new EstacionDePolicia(OBJECTID, DESCRIP, EPODIR_SITIO, EPOLATITUD , EPOLONGITU, 
						EPOSERVICIO, EPOHORARIO, EPOTELEFON, EPOIULOCAL);
				listaCerdos.enqueue(c);

			}

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "\nCarga completa \nEl número total de estaciones de cerdos es: "+ listaCerdos.size() + "\n\nLa estacion con mayor OBJECTID es: \n";
	}


	public void agragarArcosYVertices()
	{
		try {
			JsonReader reader1 ;
			Queue<String> arcos = new Queue<String>();
			reader1 = new JsonReader(new FileReader(PATHarcosVertices));
			JsonElement elem = JsonParser.parseReader(reader1);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			for(JsonElement e: e2) 
			{
				int IDINICIAL = e.getAsJsonObject().get("properties").getAsJsonObject().get("IDINICIAL").getAsInt();
				double LONGITUD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LONGITUD").getAsDouble();
				double LATITUD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LATITUD").getAsDouble();
				grafito.addVertex(IDINICIAL, new Lugar(LONGITUD, LATITUD));
				for (JsonElement arco : e.getAsJsonObject().get("properties").getAsJsonObject().get("ARCOS").getAsJsonArray()) {
					int IDFINAL = arco.getAsJsonObject().get("properties").getAsJsonObject().get("IDFINAL").getAsInt();
					double COSTO = arco.getAsJsonObject().get("properties").getAsJsonObject().get("COSTO").getAsDouble();
					arcos.enqueue(IDINICIAL + " " + IDFINAL + " " + COSTO);
				}

			}
			for (String jsonElement : arcos) {
				String[] datos = arcos.dequeue().split(" ");
				grafito.addEdge(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), Double.parseDouble(datos[3]));
			}
		}

		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public String agregarComparendos()
	{
		JsonReader reader;
		try 
		{
			reader = new JsonReader(new FileReader(PATHcomparendos));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


			SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			for(JsonElement e: e2) 
			{
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();
				String almostDate =  s.split("Z") [0];
				String finalDate = almostDate.split("T") [0] + " " + almostDate.split("T") [1];

				Date FECHA_HORA = parser.parse(finalDate); 
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, CLASE_VEHI, TIPO_SERVI, INFRACCION, LOCALIDAD, longitud, latitud);
				listaComparendos.enqueue(c);


			}
		} 
		catch (FileNotFoundException | ParseException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return "\nCarga completa \nEl número total de comparendos es: "+ listaComparendos.size() +"\n\nEl comparendo con mayor OBJECTID es: \n" ;
	}

	public int darE()
	{
		return grafito.E();
	}
	
	public int darV()
	{
		return grafito.V();
	}

	public void encontrarMasCercano(double longitud, double latitud)
	{
		int idCercano = 0;
		Haversine e = new Haversine();
		
		
	}



}