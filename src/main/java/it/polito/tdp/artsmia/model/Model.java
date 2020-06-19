package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
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
	List<Artist> best;
	public Model() {
		this.dao= new ArtsmiaDAO();
		adia= null;

		
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
				this.idMap= new HashMap<>();
		dao.getArtist(idMap,role);
		Graphs.addAllVertices(grafo, idMap.values());
		this.adia=this.dao.getAdiacenze(idMap, role);
		for(Adiacenza a: adia) {
			Graphs.addEdge(grafo, a.getA1(),a.getA2(), a.getPeso());
		}
	}
	public boolean controllaId(int id) {
	if(idMap.containsKey(id)) {
		return true;
	}
		return false;
	}
	public List<Artist> calcolaPercorso(int id) {
		best= new ArrayList<>();
		List<Artist> parziale= new ArrayList<>();
		parziale.add(idMap.get(id));
		this.cerca(parziale);
		return best;
	}
	private void cerca(List<Artist> parziale) {

		Artist ultimo= parziale.get(parziale.size()-1);
		double peso=0.0;
		List<Artist> vicini= Graphs.neighborListOf(grafo, ultimo);
		
		for(Artist a: vicini) {
			if(!parziale.contains(a) && parziale.size()==1) {
				parziale.add(a);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
				peso=grafo.getEdgeWeight(grafo.getEdge(ultimo, a));
			}else if(!parziale.contains(a) && grafo.getEdgeWeight(grafo.getEdge(ultimo, a))==peso) {
				parziale.add(a);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		if(parziale.size()>best.size()) {
			best= new ArrayList<>(parziale);
		}
	}
	


}
