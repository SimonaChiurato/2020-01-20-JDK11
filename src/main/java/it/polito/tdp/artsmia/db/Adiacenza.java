package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.Artist;

public class Adiacenza implements Comparable<Adiacenza>{
	private Artist a1;
	private Artist a2;
	private double peso;
	
	public Adiacenza(Artist a1, Artist a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Artist getA1() {
		return a1;
	}
	public void setA1(Artist a1) {
		this.a1 = a1;
	}
	public Artist getA2() {
		return a2;
	}
	public void setA2(Artist a2) {
		this.a2 = a2;
	}
	public double getPeso() {
		return peso;
	}
	
	
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		
		return -Double.compare(peso, o.peso);
	}
	

}
