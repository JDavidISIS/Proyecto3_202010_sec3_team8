package model.data_structures;

import java.util.*;

import algs4.*;


public class MiGrafo<K, V> {

	private EdgeWeightedGraph grafo;
	private SeparateChainingHashST<K, Integer> llaveAEntero;
	private SeparateChainingHashST<Integer, K> enteroALlave;
	private SeparateChainingHashST<K, V> llaveAInfoVertex;

	private int V = 0;

	public MiGrafo(int tamaNo) {
		grafo = new EdgeWeightedGraph(tamaNo);
		llaveAEntero = new SeparateChainingHashST<K, Integer>();
		llaveAInfoVertex = new SeparateChainingHashST<K, V>();
		enteroALlave = new SeparateChainingHashST<Integer, K>();
	}

	public void addEdge(K from, K to, double peso) {

		if(!llaveAEntero.contains(from)) {
			llaveAEntero.put(from, V);
			enteroALlave.put(V, from);
			V++;
		}
		int fromEntero = llaveAEntero.get(from);

		if(!llaveAEntero.contains(to)) {
			llaveAEntero.put(to, V);
			enteroALlave.put(V, to);
			V++;
		}
		int toEntero = llaveAEntero.get(to);

		grafo.addEdge(new Edge(fromEntero, toEntero, peso));
	}

	public V getInfoVertex(K key) {
		return llaveAInfoVertex.get(key);
	}

	public void addVertex(K key, V info) {
		llaveAInfoVertex.put(key, info);
		if(!llaveAEntero.contains(key)) {
			llaveAEntero.put(key, V);
			enteroALlave.put(V, key);
			V++;
		}
	}

	public Iterable<K> getCC(K key) {
		CC cc = new CC (grafo);
		ArrayList<K> lista = new ArrayList<K>();

		for(int v = 0; v < grafo.V(); v++) {
			if(cc.id(v) == cc.id(llaveAEntero.get(key))) {
				lista.add(enteroALlave.get(v));
			}
		}

		return lista;
	}


	public Iterable<K> adj(K key) {
		Iterable<Edge> a = grafo.adj(llaveAEntero.get(key));

		ArrayList<K> resultado = new ArrayList<K>();
		for(Edge e: a) {
			int v = e.other(llaveAEntero.get(key));
			resultado.add(enteroALlave.get(v));
		}
		return resultado;
	}

	public int V()
	{
		return grafo.V();
	}

	public int E()
	{
		return grafo.E();
	}

	public void setInfoVertex(K key, V info)
	{
		llaveAInfoVertex.put(key, info);
	}
	
	public boolean esta(K key)
	{
		return llaveAInfoVertex.contains(key);
	}
	
	public void uncheck()
	{
		return;
	}
	
	
	void setCostArc(K idVertexIni,K idVertexFin, double cost)
	{
		int llaveIni = llaveAEntero.get(idVertexIni);
		int llaveFin = llaveAEntero.get(idVertexIni);
		
		for(Edge m: grafo.edges())
		{
			if (m.other(llaveIni) == llaveFin)
			{
				m.setWeight(cost);
			}
		}
	}
	
	
}