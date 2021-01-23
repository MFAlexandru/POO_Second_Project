package format;

import java.util.ArrayList;

public class Output {
    private ArrayList<OutputCustomers> consumers;
    private ArrayList<OutputDistributors> distributors;

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

    public Output(final ArrayList<OutputCustomers> customers,
                  final ArrayList<OutputDistributors> distributors) {
        this.consumers = customers;
        this.distributors = distributors;
    }
}
