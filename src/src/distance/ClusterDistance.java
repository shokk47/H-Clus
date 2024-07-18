package distance;

import clustering.Cluster;
import data.Data;

/**
 * Interfaccia per calcolare la distanza tra due cluster.
 *
 * Questa interfaccia definisce un metodo per calcolare la distanza tra due cluster utilizzando i dati del dataset.
 * Ogni implementazione di questa interfaccia fornisce un algoritmo specifico per calcolare la distanza tra due cluster.
 *
 * @author Map Tutor
 */
public interface ClusterDistance {

	/**
	 * Calcola la distanza tra due cluster utilizzando i dati del dataset.
	 *
	 * @param c1 Il primo cluster.
	 * @param c2 Il secondo cluster.
	 * @param d Il dataset di esempi.
	 * @return La distanza tra i due cluster.
	 */
	double distance(Cluster c1, Cluster c2, Data d);
}
