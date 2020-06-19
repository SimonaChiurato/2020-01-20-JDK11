package it.polito.tdp.artsmia.model;

public class Artist {
	
	private int id;
	private String nome;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Artist(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}
