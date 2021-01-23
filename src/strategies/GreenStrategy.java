package strategies;

import entities.Producer;

import java.util.ArrayList;

public class GreenStrategy extends EnergyStrategy {
    /**
     * method tho chose a producer
     */
    @Override
    public ArrayList<Producer> chose(final ArrayList<Producer> producersIn, int quantity) {
        ArrayList<Producer> producers = new ArrayList<>(producersIn);
        for (int i = 0; i < producers.size(); i++) {
            for (int j = i + 1; j < producers.size(); j++) {
                if ((producers.get(i).getType().isRenewable()
                        &&
                        producers.get(j).getType().isRenewable())
                        ||
                        (!producers.get(i).getType().isRenewable()
                                &&
                                !producers.get(j).getType().isRenewable())) {

                    if (producers.get(i).getPrice() ==  producers.get(j).getPrice()) {
                        if (producers.get(i).getEnergy() == producers.get(j).getEnergy()) {
                            if (producers.get(i).getId() > producers.get(j).getId()) {
                                swap(producers, i, j);
                            }
                        } else if (producers.get(i).getEnergy() < producers.get(j).getEnergy()) {
                            swap(producers, i, j);
                        }
                    } else if (producers.get(i).getPrice() >  producers.get(j).getPrice()) {
                        swap(producers, i, j);
                    }
                } else if (!producers.get(i).getType().isRenewable()) {
                    swap(producers, i, j);
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
