package clustering;

import java.io.Serializable;
import data.Data;
import distance.ClusterDistance;

class ClusterSet implements Serializable {
	private static final long serialVersionUID = 1L; // Aggiunta del serialVersionUID per la serializzazione
	private Cluster[] C; // Array per memorizzare i cluster
	private int lastClusterIndex = 0; // Indice dell'ultimo cluster aggiunto

	// Costruttore che inizializza l'array di cluster con la dimensione specificata
	ClusterSet(int k) {
		C = new Cluster[k];
	}

	// Metodo per aggiungere un cluster all'array
	void add(Cluster c) {
		for (int j = 0; j < lastClusterIndex; j++) {
			if (c == C[j]) // Evita duplicati
				return;
		}
		C[lastClusterIndex] = c; // Aggiunge il cluster all'array
		lastClusterIndex++; // Incrementa l'indice dell'ultimo cluster
	}

	// Metodo per ottenere un cluster dall'array in base all'indice
	Cluster get(int i) {
		return C[i];
	}

	// Metodo per ottenere una rappresentazione stringa dell'array di cluster
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str.append("cluster").append(i).append(":").append(C[i]).append("\n");
			}
		}
		return str.toString();
	}

	// Metodo per ottenere una rappresentazione stringa dell'array di cluster con i dati effettivi
	String toString(Data data) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str.append("cluster").append(i).append(":").append(C[i].toString(data)).append("\n");
			}
		}
		return str.toString();
	}

	// Metodo per unire i cluster più vicini in base alla distanza specificata
	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) {
		int mCluster1 = 0; // Indice del primo cluster da unire
		int mCluster2 = 0; // Indice del secondo cluster da unire
		double minDistance = Double.MAX_VALUE; // Distanza minima inizialmente impostata al massimo valore possibile

		// Trova i due cluster più vicini
		for (int i = 0; i < lastClusterIndex; i++) {
			for (int j = i + 1; j < lastClusterIndex; j++) {
				double d = distance.distance(C[i], C[j], data); // Calcola la distanza tra i cluster
				if (d < minDistance) {
					minDistance = d;
					mCluster1 = i;
					mCluster2 = j;
				}
			}
		}

		// Crea un nuovo ClusterSet per memorizzare i cluster uniti
		ClusterSet newCS = new ClusterSet(C.length - 1);

		// Aggiunge i cluster al nuovo ClusterSet, unendo i due cluster più vicini
		for (int i = 0; i < lastClusterIndex; i++) {
			if (i == mCluster1) {
				newCS.add(C[mCluster1].mergeCluster(C[mCluster2]));
			} else if (i != mCluster2) {
				newCS.add(C[i]);
			}
		}
		return newCS;
	}
}
