package format;

public class OutputCustomerToDistribuitor {
    private int consumerId;
    private int price;
    private int remainedContractMonths;
    /**
     * returns the id
     */
    public int getConsumerId() {
        return consumerId;
    }
    /**
     * returns the price
     */
    public int getPrice() {
        return price;
    }
    /**
     * returns the remaining months
     */
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public OutputCustomerToDistribuitor(final int consumerId,
                                        final int price,
                                        final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }
}
