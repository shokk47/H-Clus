package database;

// Eccezione personalizzata per gestire errori di connessione al database
public class DatabaseConnectionException extends Exception {
    // Costruttore che accetta un messaggio di errore
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
