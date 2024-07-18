package distance;

import clustering.Cluster;
import data.Data;
import data.Example;

/**
 * Calcola la distanza tra due cluster utilizzando la single link distance.
 *
 * La single link distance tra due cluster è definita come la distanza minima tra tutti i punti
 * di un cluster e tutti i punti dell'altro cluster.
 *
 * Questa classe implementa l'interfaccia ClusterDistance e fornisce un algoritmo specifico
 * per calcolare la distanza tra due cluster utilizzando la single link distance.
 *
 * @author Map Tutor
 */
public class SingleLinkDistance implements ClusterDistance {

	/**
	 * Calcola la distanza tra due cluster utilizzando la single link distance.
	 *
	 * @param c1 Il primo cluster.
	 * @param c2 Il secondo cluster.
	 * @param d Il dataset di esempi.
	 * @return La distanza tra i due cluster utilizzando la single link distance.
	 */
	public double distance(Cluster c1, Cluster c2, Data d) {
		double minDistance = Double.MAX_VALUE;

		// Itera su tutti gli esempi nei cluster c1 e c2
		for (Integer id1 : c1) {
			Example e1 = d.getExample(id1);
			for (Integer id2 : c2) {
				Example e2 = d.getExample(id2);
				double distance = e1.distance(e2);
				if (distance < minDistance) {
					minDistance = distance;
				}
			}
		}

		return minDistance;
	}
}
