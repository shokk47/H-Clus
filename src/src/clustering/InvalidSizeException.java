package clustering;

// Definizione di una nuova eccezione che estende la classe Exception
public class InvalidSizeException extends Exception {

    // Costruttore che accetta un messaggio di errore e lo passa al costruttore della superclasse Exception
    public InvalidSizeException(String message) {
        super(message);
    }
}
