package database;

import data.Example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce l'accesso ai dati di una tabella del database.
 *
 * Viene utilizzata per recuperare le transazioni distinte dalla tabella specificata.
 * Ogni transazione è rappresentata come un oggetto di tipo Example.
 *
 * @author Map Tutor
 */
public class TableData {
    private DbAccess db;

    /**
     * Costruttore della classe TableData.
     *
     * @param db L'accesso al database da utilizzare.
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Recupera le transazioni distinte dalla tabella specificata.
     *
     * @param table Il nome della tabella da cui recuperare le transazioni.
     * @return Una lista di oggetti Example rappresentanti le transazioni.
     * @throws SQLException Eccezione lanciata in caso di errore durante l'esecuzione della query SQL.
     * @throws EmptySetException Eccezione lanciata se il set di dati ottenuti è vuoto.
     * @throws MissingNumberException Eccezione lanciata se manca un numero durante l'elaborazione dei dati.
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, MissingNumberException {
        List<Example> transazioni = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.createStatement();

            // Esegue la query per ottenere le transazioni distinte dalla tabella specificata
            rs = stmt.executeQuery("SELECT DISTINCT * FROM " + table);

            // Ottiene il numero di colonne nel ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            // Ottiene lo schema della tabella
            TableSchema schema = new TableSchema(db, table);

            // Itera sul ResultSet
            while (rs.next()) {
                Example example = new Example();

                // Aggiunge i valori delle colonne come attributi all'example
                for (int i = 1; i <= numColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    TableSchema.Column column = schema.getColumn(i - 1);

                    // Verifica se l'attributo è numerico
                    if (column.isNumber()) {
                        double value = rs.getDouble(i);
                        if (rs.wasNull()) {
                            throw new MissingNumberException("Valore mancante per l'attributo numerico: " + columnName);
                        }
                        example.add(value);
                    } else {
                        // Lancia un'eccezione se l'attributo non è numerico
                        throw new MissingNumberException("Attributo non numerico trovato: " + columnName);
                    }
                }

                transazioni.add(example);
            }

            // Se la lista è vuota, solleva un'eccezione
            if (transazioni.isEmpty()) {
                throw new EmptySetException("La tabella è vuota: " + table);
            }

        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            // Chiude le risorse JDBC
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transazioni;
    }
}
