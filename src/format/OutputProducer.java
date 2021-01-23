package format;

import java.util.ArrayList;

public class OutputProducer {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private String energyType;
    private int energyPerDistributor;
    private ArrayList<Stat> monthlyStats;

    public OutputProducer(int id, int maxDistributors,
                          double priceKW,
                          String energyType,
                          int energyPerDistributor,
                          ArrayList<Stat> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }
    /**
     * return the id
     */
    public int getId() {
        return id;
    }
    /**
     * set the id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * get max dist
     */
    public int getMaxDistributors() {
        return maxDistributors;
    }
    /**
     * set max dist
     */
    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }
    /**
     * get price
     */
    public double getPriceKW() {
        return priceKW;
    }
    /**
     * set price
     */
    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }
    /**
     * get energy type
     */
    public String getEnergyType() {
        return energyType;
    }
    /**
     * set energy type
     */
    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }
    /**
     * get energy per dit
     */
    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }
    /**
     * set energy per dist
     */
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
    /**
     * get monthly stats
     */
    public ArrayList<Stat> getMonthlyStats() {
        return monthlyStats;
    }
    /**
     * set monthly stats
     */
    public void setMonthlyStats(ArrayList<Stat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
