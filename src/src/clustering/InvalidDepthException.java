package clustering;

// Definizione di una nuova eccezione che estende la classe Exception
public class InvalidDepthException extends Exception {

    // Costruttore che accetta un messaggio di errore e lo passa al costruttore della superclasse Exception
    public InvalidDepthException(String message) {
        super(message);
    }
}
