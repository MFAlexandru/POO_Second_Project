package strategies;

import entities.Producer;

import java.util.ArrayList;

public interface EnergyStrategy {
    public ArrayList<Producer> Chose(final ArrayList<Producer> producers , int quantity);
}
