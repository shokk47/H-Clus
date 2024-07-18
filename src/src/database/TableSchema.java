package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Questa classe rappresenta lo schema di una tabella nel database.
 *
 * Viene utilizzata per ottenere informazioni sulle colonne della tabella, come nome e tipo di dato.
 *
 * @author Map Tutor
 */
public class TableSchema {
	private DbAccess db;

	/**
	 * Rappresenta una colonna all'interno dello schema della tabella.
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * Costruttore della classe Column.
		 *
		 * @param name Il nome della colonna.
		 * @param type Il tipo di dato della colonna.
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Restituisce il nome della colonna.
		 *
		 * @return Il nome della colonna.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Verifica se il tipo di dato della colonna è numerico.
		 *
		 * @return true se il tipo di dato è numerico, false altrimenti.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Restituisce una rappresentazione testuale della colonna.
		 *
		 * @return Una stringa che rappresenta la colonna nel formato "nome:tipo".
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	private List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * Costruttore della classe TableSchema.
	 *
	 * @param db L'accesso al database da utilizzare.
	 * @param tableName Il nome della tabella di cui ottenere lo schema.
	 * @throws SQLException Eccezione lanciata in caso di errore durante l'interrogazione del database.
	 * @throws DatabaseConnectionException Eccezione lanciata se la connessione al database fallisce.
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException {
		this.db = db;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// Mappatura dei tipi di dati SQL ai tipi di dati Java
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		// Itera sul ResultSet per ottenere le informazioni sulle colonne della tabella
		while (res.next()) {
			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
				tableSchema.add(new Column(
						res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
				);
			}
		}
		res.close();
	}

	/**
	 * Restituisce il numero di attributi nella tabella.
	 *
	 * @return Il numero di attributi nella tabella.
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * Restituisce la colonna corrispondente all'indice specificato.
	 *
	 * @param index L'indice della colonna da restituire.
	 * @return La colonna corrispondente all'indice specificato.
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}
