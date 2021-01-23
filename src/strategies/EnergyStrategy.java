package strategies;

import entities.Producer;

import java.util.ArrayList;

public abstract class EnergyStrategy {
    /**
     * method tho chose a producer
     */
    public abstract ArrayList<Producer> chose(ArrayList<Producer> producers, int quantity);
    /**
     * swap stuff
     */
    public void swap(final ArrayList<Producer> producers, int i, int j) {
        Producer aux = producers.get(i);
        producers.set(i, producers.get(j));
        producers.set(j, aux);
    }
}
