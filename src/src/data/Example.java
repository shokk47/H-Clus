package data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// Classe che rappresenta un esempio nel dataset
public class Example implements Iterable<Double> {
    // Lista per memorizzare le caratteristiche dell'esempio
    List<Double> example;

    // Costruttore per inizializzare la lista
    public Example() {
        example = new LinkedList<>();
    }

    // Implementazione del metodo iterator per iterare sugli elementi dell'esempio
    public Iterator<Double> iterator() {
        return example.iterator();
    }

    // Metodo per aggiungere un elemento all'esempio
    public void add(Double v) {
        example.add(v);
    }

    // Metodo per ottenere l'elemento all'indice specificato nell'esempio
    Double get(int index) {
        return example.get(index);
    }

    // Metodo per calcolare la distanza tra due oggetti Example
    public double distance(Example newE) {
        double sum = 0;
        Iterator<Double> thisIterator = this.iterator();
        Iterator<Double> newEIterator = newE.iterator();

        // Calcolo della somma dei quadrati delle differenze tra le caratteristiche
        while (thisIterator.hasNext() && newEIterator.hasNext()) {
            double diff = thisIterator.next() - newEIterator.next();
            sum += Math.pow(diff, 2);
        }
        return sum;
    }

    // Metodo per rappresentare l'oggetto Example come stringa
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Iterator<Double> it = this.iterator();
        while (it.hasNext()) {
            s.append(it.next());
            if (it.hasNext()) {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }
}
