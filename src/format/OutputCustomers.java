package format;

public class OutputCustomers {
    private int id;
    private boolean isBankrupt;
    private int budget;
    /**
     *returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * retrurns if customer is bankrupt
     */
    public boolean getIsBankrupt() {
        return isBankrupt;
    }
    /**
     * returns the budget
     */
    public int getBudget() {
        return budget;
    }

    public OutputCustomers(final int id,
                           final boolean isBankrupt,
                           final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }
}
