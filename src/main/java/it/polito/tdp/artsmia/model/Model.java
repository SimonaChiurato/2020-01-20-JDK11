package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao;
	private SimpleWeightedGraph< Artist, DefaultWeightedEdge> grafo;
	private Map<Integer, Artist> idMap;
	private List<Adiacenza> adia;
	public Model() {
		this.dao= new ArtsmiaDAO();
		adia= null;
		this.idMap= new HashMap<>();
		
	}
	public List<String> listRole() {
		// TODO Auto-generated method stub
		return this.dao.listRole();
	}
	public int archi() {
		return this.grafo.edgeSet().size();
		
	}
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	public List<Adiacenza> adiacenze(){
		return adia;
	}
	public void creaGrafo(String role) {
		grafo = new SimpleWeightedGraph<Artist, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao.getArtist(idMap,role);
		Graphs.addAllVertices(grafo, idMap.values());
		this.adia=this.dao.getAdiacenze(idMap, role);
		for(Adiacenza a: adia) {
			Graphs.addEdge(grafo, a.getA1(),a.getA2(), a.getPeso());
		}
	}
	


}
