package format;

import java.util.ArrayList;

public class OutputDistributors {
    private int id;
    private int budget;
    private int energyNeededKW;
    private int contractCost;
    private boolean isBankrupt;
    private String producerStrategy;
    private ArrayList<OutputCustomerToDistribuitor> contracts;
    /**
     * returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * returns the budget
     */
    public int getBudget() {
        return budget;
    }
    /**
     * returns if company is bankrupt
     */
    public boolean getIsBankrupt() {
        return isBankrupt;
    }
    /**
     * returns the contracts
     */
    public ArrayList<OutputCustomerToDistribuitor> getContracts() {
        return contracts;
    }

    public OutputDistributors(final int id,
                              final boolean isBankrupt,
                              final int budget,
                              final ArrayList<OutputCustomerToDistribuitor> customers) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
        this.contracts = customers;
    }
}
