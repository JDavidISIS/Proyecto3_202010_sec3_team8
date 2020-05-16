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
	
	public static String PATHcomparendos = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";
	
	public RedBlackBST<Integer, EstacionDePolicia> listaCerdos;
	
	public RedBlackBST<Integer, Comparendo> listaComparendos;

	int mayorId = 0;

	private MiGrafo<Integer, Lugar> grafito;
	


	public Modelo()
	{
		

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
				String EPOSERVICIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PROY").getAsString();
				String EPOHORARIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PROY").getAsString();
				String EPOTELEFON = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PROY").getAsString();
				String EPOIULOCAL = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOCOD_PROY").getAsString();
				
				
				EstacionDePolicia c = new EstacionDePolicia(OBJECTID, DESCRIP, EPODIR_SITIO, EPOLATITUD , EPOLONGITU, 
						EPOSERVICIO, EPOHORARIO, EPOTELEFON, EPOIULOCAL);
				listaCerdos.put(c.getObjectID(), c);
				
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "\nCarga completa \nEl número total de estaciones de cerdos es: "+  "\n\nLa estacion con mayor OBJECTID es: \n" + listaCerdos.get(listaCerdos.max());
	}
	
	public  String agregarComparendos()
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
				listaComparendos.put(c.darId(), c);

				
			}
		} 
		catch (FileNotFoundException | ParseException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return "\nCarga completa \nEl número total de comparendos es: "+ listaComparendos.size() +"\n\nEl comparendo con mayor OBJECTID es: \n" + listaComparendos.get(listaComparendos.max());
	}


	public String cargaVertices() 
	{
		Lugar mayor = null;
		
		try {
			FileReader reader;
			reader = new FileReader(PATHvertices);

			BufferedReader bufferedReader = new BufferedReader(reader);
			Queue<String> vertices = new Queue<String>();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				String datos = line;
				vertices.enqueue(datos);
			}
			grafito = new MiGrafo<>(vertices.size());
			while (vertices.size() != 0) {
				String[] completo = vertices.dequeue().split(",");
				int id = Integer.parseInt(completo[0]); 
				double latitud = Double.parseDouble(completo[1]);
				double longitud = Double.parseDouble(completo[2]);
				Lugar lugar = new Lugar(latitud, longitud);
				grafito.addVertex(id, lugar);
				if(id > mayorId) mayor = lugar;
			}

			reader.close();

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "\nCarga completa \nEl número total de vertices es: "+ grafito.V() +"\n\nEl vertice con mayor OBJECTID es: \n" + mayor.darDatos();
	}


	public void cargarArcos()
	{
		try {
			FileReader reader = new FileReader(PATHarcos);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				String[] datos = line.split(" ");

				int primero = Integer.parseInt(datos[0]);
				if(grafito.esta(primero)) {
					double startLat = grafito.getInfoVertex(primero).getLatitud();
					double startLong = grafito.getInfoVertex(primero).getLongitud();

					for (int i = 1; i < datos.length; i++) {

						int segundo = Integer.parseInt(datos[i]);
						{
							if(grafito.esta(segundo)) {

								double endLat = grafito.getInfoVertex(primero).getLatitud();
								double endLong = grafito.getInfoVertex(segundo).getLongitud();
								Haversine peso = new Haversine();
								grafito.addEdge(primero, segundo, peso.distance(startLat, startLong, endLat, endLong));
							}
						}

					}
				}
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	

}







