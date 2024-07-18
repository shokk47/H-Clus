package distance;

import clustering.Cluster;
import data.Data;
import data.Example;

/**
 * Calcola la distanza media tra due cluster utilizzando la media delle distanze tra tutti gli esempi nei due cluster.
 *
 * La distanza media è calcolata come la somma delle distanze tra tutti gli esempi nei due cluster, divisa per il prodotto delle dimensioni dei cluster.
 *
 * Questa classe implementa l'interfaccia ClusterDistance.
 *
 * @author Map Tutor
 */
public class AverageLinkdistance implements ClusterDistance {

    /**
     * Calcola la distanza media tra due cluster.
     *
     * @param c1 Il primo cluster.
     * @param c2 Il secondo cluster.
     * @param d Il dataset di esempi.
     * @return La distanza media tra i due cluster.
     */
    public double distance(Cluster c1, Cluster c2, Data d) {
        double sum = 0;
        // Itera su tutti gli esempi nei cluster c1 e c2
        for (Integer id1 : c1) {
            Example e1 = d.getExample(id1);
            for (Integer id2 : c2) {
                Example e2 = d.getExample(id2);
                // Calcola la distanza tra gli esempi e aggiunge alla somma
                sum += e1.distance(e2);
            }
        }
        // Calcola la distanza media dividendo la somma per il prodotto delle dimensioni dei cluster
        return sum / (c1.getSize() * c2.getSize());
    }
}
