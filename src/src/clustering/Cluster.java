package clustering;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import data.Data;

public class Cluster implements Iterable<Integer>, Cloneable, Serializable {
	private static final long serialVersionUID = 1L; // Aggiunta del serialVersionUID per la serializzazione
	private Set<Integer> clusteredData = new TreeSet<>(); // Set ordinato per memorizzare gli ID dei dati clusterizzati

	// Metodo per aggiungere un ID di dato al cluster
	void addData(int id) {
		clusteredData.add(id);
	}

	// Metodo per ottenere la dimensione del cluster
	public int getSize() {
		return clusteredData.size();
	}

	// Metodo per clonare il cluster
	@Override
	public Object clone() {
		try {
			Cluster copyC = (Cluster) super.clone(); // Clona l'istanza corrente
			copyC.clusteredData = new TreeSet<>(this.clusteredData); // Crea una nuova copia del Set
			return copyC;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	// Metodo per unire due cluster in uno nuovo
	Cluster mergeCluster(Cluster c) {
		Cluster newC = new Cluster(); // Crea un nuovo cluster
		newC.clusteredData.addAll(this.clusteredData); // Aggiunge i dati del primo cluster
		newC.clusteredData.addAll(c.clusteredData); // Aggiunge i dati del secondo cluster
		return newC;
	}

	// Metodo per ottenere un iteratore sugli ID dei dati nel cluster
	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	// Metodo per ottenere una rappresentazione del cluster come stringa
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		Iterator<Integer> iterator = clusteredData.iterator();
		while (iterator.hasNext()) {
			str.append(iterator.next());
			if (iterator.hasNext()) {
				str.append(",");
			}
		}
		return str.toString();
	}

	// Metodo per ottenere una rappresentazione del cluster con i dati effettivi
	String toString(Data data) {
		StringBuilder str = new StringBuilder();
		for (Integer id : clusteredData) {
			str.append("<").append(data.getExample(id)).append(">");
		}
		return str.toString();
	}

	// Metodo principale per testare la classe Cluster
	public static void main(String[] args) {
		Cluster cluster = new Cluster();
		cluster.addData(1);
		cluster.addData(3);
		cluster.addData(2);
		System.out.println(cluster); // Stampa il cluster

		Cluster clusterCopy = (Cluster) cluster.clone();
		System.out.println("Cloned Cluster: " + clusterCopy); // Stampa il cluster clonato

		Cluster anotherCluster = new Cluster();
		anotherCluster.addData(4);
		anotherCluster.addData(5);

		Cluster mergedCluster = cluster.mergeCluster(anotherCluster);
		System.out.println("Merged Cluster: " + mergedCluster); // Stampa il cluster unito
	}
}
