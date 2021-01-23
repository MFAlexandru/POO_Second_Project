package strategies;

import entities.Producer;

import java.util.ArrayList;

public class PriceStrategy implements EnergyStrategy {
    /**
     * method tho chose a producer
     */
    @Override
    public ArrayList<Producer> chose(final ArrayList<Producer> producersIn, int quantity) {
        ArrayList<Producer> producers = new ArrayList<>(producersIn);
        for (int i = 0; i < producers.size(); i++) {
            for (int j = i + 1; j < producers.size(); j++) {
                if (producers.get(i).getPrice() ==  producers.get(j).getPrice()) {
                    if (producers.get(i).getEnergy() == producers.get(j).getEnergy()) {
                        if (producers.get(i).getId() > producers.get(j).getId()) {
                            Producer aux = producers.get(i);
                            producers.set(i, producers.get(j));
                            producers.set(j, aux);
                        }
                    } else if (producers.get(i).getEnergy() < producers.get(j).getEnergy()) {
                        Producer aux = producers.get(i);
                        producers.set(i, producers.get(j));
                        producers.set(j, aux);
                    }
                } else if (producers.get(i).getPrice() >  producers.get(j).getPrice()) {
                    Producer aux = producers.get(i);
                    producers.set(i, producers.get(j));
                    producers.set(j, aux);
                }
            }
        }
        ArrayList<Producer> output = new ArrayList<>();

        for (int i = 0, sum = 0; sum < quantity; i++) {
            if (producers.get(i).countObservers() != producers.get(i).getMaxDistributors()) {
                output.add(producers.get(i));
                sum += producers.get(i).getEnergy();
            }
        }
        return output;
    }
}
