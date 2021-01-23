package format;

import java.util.ArrayList;

public class Output {
    private ArrayList<OutputCustomers> consumers;
    private ArrayList<OutputDistributors> distributors;
    private ArrayList<OutputProducer> energyProducers;
    /**
     * returns the consumers
     */
    public ArrayList<OutputCustomers> getConsumers() {
        return consumers;
    }
    /**
     * returns the distributors
     */
    public ArrayList<OutputDistributors> getDistributors() {
        return distributors;
    }
    /**
     * returns the producers
     */
    public ArrayList<OutputProducer> getEnergyProducers() {
        return energyProducers;
    }

    public Output(final ArrayList<OutputCustomers> customers,
                  final ArrayList<OutputDistributors> distributors,
                  final ArrayList<OutputProducer> energyProducers) {
        this.consumers = customers;
        this.distributors = distributors;
        this.energyProducers = energyProducers;
    }
}
