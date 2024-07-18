package database;

// Eccezione lanciata quando si tenta di eseguire un'operazione su un set vuoto
public class EmptySetException extends Exception {
    // Costruttore che accetta un messaggio di errore
    public EmptySetException(String message) {
        super(message);
    }
}
