package strategies;

import entities.Producer;

import java.util.ArrayList;

public interface EnergyStrategy {
    /**
     * method tho chose a producer
     */
    ArrayList<Producer> chose(ArrayList<Producer> producers, int quantity);
}
