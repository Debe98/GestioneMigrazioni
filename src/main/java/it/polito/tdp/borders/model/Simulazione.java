package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

import it.polito.tdp.borders.model.Event.EventType;

public class Simulazione {
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	// VARIABILI
	private Graph <Country, DefaultEdge> grafo;
	private int numeroMigranti;
	private Map <Country, Integer> popolazione;
	private Country statoIniziale;
	private int passoCorrente;
	

	// SETTER PER I PARAMETRI ESTERNI
	
	public void setGrafo(Graph <Country, DefaultEdge> grafo) {
		this.grafo = grafo;
	}
	
	public void setStatoIniziale(Country statoIniziale) {
		this.statoIniziale = statoIniziale;
	}
	
	// GETTER PER I RISULTATI
	
	public Map<Country, Integer> getPopolazione() {
		return popolazione;
	}

	public int getPassoCorrente() {
		return passoCorrente;
	}
	
	// FUNZIONI DI UTILITA'
	

	// SIMULAZIONE VERA E PROPRIA


	public void run() {
		// preparazione iniziale (mondo + coda eventi)
		popolazione = new HashMap <>();
		passoCorrente = 1;
		numeroMigranti = 1000;
		
		if (statoIniziale == null || grafo == null) {
			return;
		}
		
		// inizzializzazione coda eventi
		
		queue.add(new Event(EventType.ARRIVO, passoCorrente, numeroMigranti, statoIniziale));
		
		// esecuzione del ciclo di simulazione
		while (!queue.isEmpty()) {
			Event e = this.queue.poll();
			//System.out.println(e);
			processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		switch(e.getType()) {

		case ARRIVO:
			passoCorrente = e.getTempo();
			
			int numeroPersoneNomadi = e.getNumeroPersone()/2;
			int numeroStatiConfinanti = grafo.degreeOf(e.getPaese());
			
			int nomadiPerStato = numeroPersoneNomadi/numeroStatiConfinanti;
			
			if (nomadiPerStato != 0) {
				for (Country c : Graphs.neighborListOf(grafo, e.getPaese())) {
					queue.add(new Event(EventType.ARRIVO, passoCorrente+1, nomadiPerStato, c));
				}
			}
			
			int numeroStanziali = e.getNumeroPersone() - (nomadiPerStato*numeroStatiConfinanti);
			queue.add(new Event(EventType.STANZIALITA, passoCorrente, numeroStanziali, e.getPaese()));
			break;

		case STANZIALITA:
			if (popolazione.get(e.getPaese()) == null) {
				popolazione.put(e.getPaese(), e.getNumeroPersone());
			}
			else {
				popolazione.put(e.getPaese(), popolazione.get(e.getPaese())+e.getNumeroPersone());
			}
			break;
		}

	}
}

