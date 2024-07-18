package data;

// Eccezione personalizzata per gestire il caso in cui non ci siano dati disponibili
public class NoDataException extends Exception {
    // Costruttore che accetta un messaggio di errore
    public NoDataException(String message) {
        super(message);
    }
}
