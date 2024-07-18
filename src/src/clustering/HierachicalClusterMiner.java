package clustering;

import java.io.*;
import data.Data;
import distance.ClusterDistance;

public class HierachicalClusterMiner implements Serializable {
    private static final long serialVersionUID = 1L; // Aggiunta del serialVersionUID per la serializzazione
    private final Dendrogram dendrogram; // Aggregazione del dendrogramma

    // Costruttore che inizializza il dendrogramma con la profondità specificata
    public HierachicalClusterMiner(int depth) {
        dendrogram = new Dendrogram(depth);
    }

    // Metodo per ottenere una rappresentazione stringa del dendrogramma
    @Override
    public String toString() {
        return dendrogram.toString();
    }

    // Metodo per ottenere una rappresentazione stringa del dendrogramma con i dati effettivi
    public String toString(Data data) {
        return dendrogram.toString(data);
    }

    // Metodo per creare il dendrogramma a partire dai dati utilizzando una certa distanza tra cluster
    public void mine(Data data, ClusterDistance distance) {
        ClusterSet clusterSet = new ClusterSet(data.getNumberOfExamples()); // Insieme di cluster

        // Creazione iniziale dei cluster con un esempio ciascuno
        for (int i = 0; i < data.getNumberOfExamples(); i++) {
            Cluster cluster = new Cluster();
            cluster.addData(i);
            clusterSet.add(cluster);
        }

        // Imposta il livello 0 del dendrogramma
        dendrogram.setClusterSet(clusterSet, 0);

        // Iterazione sui livelli del dendrogramma per unire i cluster
        for (int i = 1; i < dendrogram.getDepth(); i++) {
            ClusterSet newClusterSet = clusterSet.mergeClosestClusters(distance, data);
            dendrogram.setClusterSet(newClusterSet, i);
            clusterSet = newClusterSet;
        }
    }

    // Metodo per salvare un'istanza di HierarchicalClusterMiner su un file
    public void salva(String fileName) throws FileNotFoundException, IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    // Metodo statico per caricare un'istanza di HierarchicalClusterMiner da un file
    public static HierachicalClusterMiner loadHierarchicalClusterMiner(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (HierachicalClusterMiner) in.readObject();
        }
    }

}
