package it.polito.tdp.borders.model;

public class Event implements Comparable <Event> {

	public enum EventType {
		ARRIVO, SPOSTAMENTO, STANZIALITA
	}

	private EventType type ;
	private int tempo ;
	private int numeroPersone;
	private Country paese;
	
	public Event(EventType type, int tempo, int numeroPersone, Country paese) {
		super();
		this.type = type;
		this.tempo = tempo;
		this.numeroPersone = numeroPersone;
		this.paese = paese;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public Country getPaese() {
		return paese;
	}

	public void setPaese(Country paese) {
		this.paese = paese;
	}

	@Override
	public int compareTo(Event o) {
		return this.tempo-o.tempo;
	}
}
