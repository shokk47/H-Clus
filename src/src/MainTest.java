import clustering.HierachicalClusterMiner;
import clustering.InvalidDepthException;
import data.Data;
import data.NoDataException;
import distance.AverageLinkdistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;
import java.io.IOException;

public class MainTest {

	public static void main(String[] args) {
		try {
			HierachicalClusterMiner clustering;

			// Richiesta all'utente se desidera caricare un oggetto HierachicalClusterMiner esistente o crearne uno nuovo
			System.out.println("Vuoi caricare un oggetto HierachicalClusterMiner precedentemente serializzato?");
			System.out.println("1. Si (carica un oggetto esistente)");
			System.out.println("2. No (crea un nuovo oggetto)");

			int choice = Keyboard.readInt(); // Legge la scelta dell'utente

			if (choice == 1) { // Se l'utente sceglie di caricare un oggetto esistente
				System.out.print("Inserisci il percorso del file da caricare: ");
				String fileName = Keyboard.readString(); // Legge il percorso del file
				clustering = HierachicalClusterMiner.loadHierarchicalClusterMiner(fileName); // Carica l'oggetto
				System.out.println("File caricato con successo!");
				// Visualizza le informazioni dell'oggetto caricato
				System.out.println(clustering.toString());

			} else if (choice == 2) { // Se l'utente sceglie di creare un nuovo oggetto
				while (true) {
					try {
						System.out.print("Inserisci il nome della tabella contenente i dati: ");
						String tableName = Keyboard.readString();
						Data data = new Data(tableName);

						// Verifica che i dati siano stati caricati correttamente
						if (data.getNumberOfExamples() == 0) {
							throw new NoDataException("Nessun dato trovato nella tabella: " + tableName);
						}

						System.out.println(data);

						System.out.print("Inserisci la profondit\u00E0 dell'oggetto dendrogramma: ");
						int k = Keyboard.readInt();

						if (k < 0) {
							throw new IllegalArgumentException("La profondit\u00E0 non può essere negativa.");
						}

						if (k > data.getNumberOfExamples()) {
							throw new InvalidDepthException("La profondit\u00E0 del dendrogramma e' superiore al numero di esempi nel dataset.");
						}

						System.out.println("Scegli il tipo di misura di distanza tra cluster:");
						System.out.println("1. Single link distance");
						System.out.println("2. Average link distance");
						System.out.print("Scelta: ");
						int distanceChoice = Keyboard.readInt();

						ClusterDistance distance;
						switch (distanceChoice) {
							case 1:
								System.out.println("Single link distance");
								distance = new SingleLinkDistance();
								break;
							case 2:
								System.out.println("Average link distance");
								distance = new AverageLinkdistance();
								break;
							default:
								System.out.println("Scelta non valida. Verr\u00E0 utilizzata la Single link distance di default.");
								distance = new SingleLinkDistance();
								break;
						}

						clustering = new HierachicalClusterMiner(k);
						clustering.mine(data, distance);

						System.out.print("Inserisci il percorso del file in cui salvare l'oggetto HierachicalClusterMiner: ");
						String saveFileName = Keyboard.readString();
						clustering.salva(saveFileName);

						System.out.println(clustering);
						System.out.println(clustering.toString(data));

						break;
					} catch (NoDataException e) {
						System.out.println("ERRORE: " + e.getMessage());
					}
				}
			} else {
				throw new IllegalArgumentException("Scelta non valida.");
			}

		} catch (InvalidDepthException | IllegalArgumentException | IOException | ClassNotFoundException e) {
			System.out.println("ERRORE: " + e.getMessage());
		}
	}
}
