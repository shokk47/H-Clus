package clustering;

import java.io.Serializable;
import data.Data;

public class Dendrogram implements Serializable {
    private static final long serialVersionUID = 1L; // Aggiunta del serialVersionUID per la serializzazione
    private ClusterSet[] tree; // Array per modellare il dendrogramma

    // Costruttore che inizializza l'array del dendrogramma con la profondità specificata
    Dendrogram(int depth) {
        tree = new ClusterSet[depth];
    }

    // Metodo per impostare un ClusterSet a un determinato livello del dendrogramma
    void setClusterSet(ClusterSet c, int level) {
        tree[level] = c;
    }

    // Metodo per ottenere il ClusterSet a un determinato livello del dendrogramma
    ClusterSet getClusterSet(int level) {
        return tree[level];
    }

    // Metodo per ottenere la profondità del dendrogramma
    int getDepth() {
        return tree.length;
    }

    // Metodo per ottenere una rappresentazione stringa del dendrogramma
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tree.length; i++) {
            str.append("level").append(i).append(":\n").append(tree[i]).append("\n");
        }
        return str.toString();
    }

    // Metodo per ottenere una rappresentazione stringa del dendrogramma con i dati effettivi
    String toString(Data data) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tree.length; i++) {
            str.append("level").append(i).append(":\n").append(tree[i].toString(data)).append("\n");
        }
        return str.toString();
    }
}
