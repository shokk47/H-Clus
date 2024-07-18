package data;

import clustering.InvalidSizeException;
import database.DbAccess;
import database.EmptySetException;
import database.MissingNumberException;
import database.TableData;
import database.DatabaseConnectionException;

import java.sql.SQLException;
import java.util.List;

// Classe che rappresenta un insieme di dati provenienti da una tabella del database
public class Data {
	// Lista di esempi e numero totale di esempi
	private List<Example> data;
	private int numberOfExamples;

	// Costruttore che recupera i dati dalla tabella specificata
	public Data(String tableName) throws NoDataException {
		// Inizializzazione dell'accesso al database
		DbAccess dbAccess = new DbAccess();
		try {
			// Connessione al database
			dbAccess.initConnection();
			// Recupero dei dati distinti dalla tabella
			TableData tableData = new TableData(dbAccess);
			data = tableData.getDistinctTransazioni(tableName);
			// Impostazione del numero totale di esempi
			numberOfExamples = data.size();
			// Chiusura della connessione al database
			dbAccess.closeConnection();
		} catch (SQLException | EmptySetException | MissingNumberException e) {
			// Gestione delle eccezioni relative al recupero dei dati
			throw new NoDataException("Errore nel recupero dei dati: " + e.getMessage());
		} catch (DatabaseConnectionException e) {
			// Gestione delle eccezioni relative alla connessione al database
			throw new NoDataException("Errore nella connessione al database: " + e.getMessage());
		}
	}

	// Metodo per ottenere il numero totale di esempi
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	// Metodo per ottenere un esempio specifico dall'insieme di dati
	public Example getExample(int exampleIndex) {
		return data.get(exampleIndex);
	}

	// Metodo per calcolare le distanze tra gli esempi nell'insieme di dati
	public double[][] distance() throws InvalidSizeException {
		double[][] distanceMatrix = new double[numberOfExamples][numberOfExamples];

		// Controllo che ci siano almeno due esempi per calcolare le distanze
		if (numberOfExamples < 2) {
			throw new InvalidSizeException("Il dataset deve contenere almeno due esempi per calcolare le distanze.");
		}

		// Calcolo delle distanze tra gli esempi nell'insieme di dati
		for (int i = 0; i < numberOfExamples; i++) {
			for (int j = i + 1; j < numberOfExamples; j++) {
				if (data.get(i).example.size() != data.get(j).example.size()) {
					// Controllo che gli esempi abbiano le stesse dimensioni
					throw new InvalidSizeException("Le dimensioni degli esempi sono diverse.");
				}
				// Calcolo della distanza tra due esempi
				distanceMatrix[i][j] = data.get(i).distance(data.get(j));
			}
		}
		return distanceMatrix;
	}

	// Metodo per rappresentare l'insieme di dati come una stringa
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		int i = 0;
		for (Example example : data) {
			s.append(i++).append(":").append(example).append("\n");
		}
		return s.toString();
	}
}
