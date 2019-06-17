package it.polito.tdp.extflightdelays.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	SimpleWeightedGraph<Airport,DefaultWeightedEdge> grafo;
	Map<Integer, Airport> aIdMap;
	ExtFlightDelaysDAO dao;
	
	public SimpleWeightedGraph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public Model() {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		aIdMap=new HashMap<>();
		dao=new ExtFlightDelaysDAO();
		dao.loadAllAirports(aIdMap);
	}
	
	public void creaGrafo(int distanzaMedia) {
		
		//Graphs.addAllVertices(grafo, aIdMap.values());
		
		for(Rotta r: dao.getRotte(aIdMap, distanzaMedia)) {
			// se aggiungo i vertici cosi nella combobox ho solo aeroporti connessi 
			grafo.addVertex(r.getPartenza());
			grafo.addVertex(r.getArrivo());
			
			Graphs.addEdgeWithVertices(grafo, r.getArrivo(), r.getPartenza());
			//controllo se esiste arco
			DefaultWeightedEdge edge = grafo.getEdge(r.getPartenza(), r.getArrivo());
			
			if (edge == null) {
				Graphs.addEdge(grafo, r.getPartenza(), r.getArrivo(), r.getMediaMedia());
			} else {
				double peso = grafo.getEdgeWeight(edge);
				double newPeso = (peso + r.getMediaMedia()) / 2;

				grafo.setEdgeWeight(edge, newPeso);
			}
			System.out.println("Grafo creato!");
			System.out.println("Vertici: " + grafo.vertexSet().size());
			System.out.println("Archi: " + grafo.edgeSet().size());
			
		}
	}

	public boolean isValid(String distanzaInput) {
		
		return distanzaInput.matches("\\d+");
	}

	public Collection<Airport> getAirport() {
		
		return aIdMap.values();
	}

	public String connessi(Airport selezionato) {
		
		String risultato=" ";
		List<Airport> vicini=Graphs.neighborListOf(grafo, selezionato);
		//per ordinare in ordine decrescente di peso
		Collections.sort(vicini, new Comparator<Airport>() {
			
			//recupero gli archi per comparare il peso
			@Override
			public int compare(Airport o1, Airport o2) {
				DefaultWeightedEdge arco1= grafo.getEdge(o1, selezionato);
				double peso1=grafo.getEdgeWeight(arco1);
				
				DefaultWeightedEdge arco2= grafo.getEdge(o2, selezionato);
				double peso2=grafo.getEdgeWeight(arco2);
				return (int) (peso2-peso1);
			}
		});
		//Collections.reverse(vicini); cambia l'ordinamento della lista
		
		for(Airport a: vicini) {
			
			DefaultWeightedEdge edge=grafo.getEdge(a, selezionato);		
			double peso=grafo.getEdgeWeight(edge);
//			if(edge==null) {
//				risultato= "aeroporto non connesso!";
//			}
//			else
			if(vicini== null && vicini.isEmpty()) {
				risultato+= "aeroporto non connesso!";
			}
			else
			risultato+= a.getAirportName() +" - distanza : "+peso+ "\n";
		}
		
		return risultato;
	}
	
	

}
