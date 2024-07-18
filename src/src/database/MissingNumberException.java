package database;

// Eccezione lanciata quando mancano attributi numerici durante l'elaborazione dei dati
public class MissingNumberException extends Exception {
    // Costruttore che accetta un messaggio di errore
    public MissingNumberException(String message) {
        super(message);
    }
}
