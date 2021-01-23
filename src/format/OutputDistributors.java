package format;

import java.util.ArrayList;

public class OutputDistributors {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private String producerStrategy;
    private boolean isBankrupt;
    private ArrayList<OutputCustomerToDistribuitor> contracts;
    /**
     * returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * JAVADOC
     */
    public int getEnergyNeededKW() {
        return energyNeededKW;
    }
    /**
     * JAVADOC
     */
    public int getContractCost() {
        return contractCost;
    }
    /**
     * returns the budget
     */
    public int getBudget() {
        return budget;
    }
    /**
     * JAVADOC
     */
    public String getProducerStrategy() {
        return producerStrategy;
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
                              final ArrayList<OutputCustomerToDistribuitor> customers,
                              final int contractCost,
                              final int energyNeededKW,
                              final String producerStrategy) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
        this.contracts = customers;
        this.contractCost = contractCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
    }
}
