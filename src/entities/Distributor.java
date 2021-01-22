package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Distributor {
    private static final float RATIO = 0.2f;
    private int id;
    private int contractLength;
    private float budget;
    private float infrastructureCost;
    private float productionCost;
    private float currentPrice = 0;
    private boolean faliment = false;
    private final HashMap<Integer, Customer> customers;

    public Distributor(final int id,
                       final int contractLength,
                       final int initialBudget,
                       final int initialInfrastructureCost,
                       final int initialProductionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.productionCost = initialProductionCost;
        customers = new HashMap<>();
    }
    /**
     * returns the id
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * sets the cotract length
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }
    /**
     * sets budget
     */
    public void setBudget(final float budget) {
        this.budget = budget;
    }
    /**
     * sets the ifractructure cost
     */
    public void setInfrastructureCost(final float infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    /**
     * sets the production cost
     */
    public void setProductionCost(final float productionCost) {
        this.productionCost = productionCost;
    }
    /**
     * returns the list of customers
     */
    public HashMap<Integer, Customer> getCustomers() {
        return customers;
    }
    /**
     * adds a customer to the list
     */
    public void gainConsumer(final int customerId, final Customer customer) {
        customers.put(customerId, customer);
    }
    /**
     * returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * returs the cntract length
     */
    public int getContractLength() {
        return contractLength;
    }
    /**
     * returns the budget
     */
    public float getBudget() {
        return budget;
    }
    /**
     * return the infrastructure cost
     */
    public float getInfrastructureCost() {
        return infrastructureCost;
    }
    /**
     * returns the production cost
     */
    public float getProductionCost() {
        return productionCost;
    }
    /**
     * returns the current price
     */
    public float getCurrentPrice() {
        return currentPrice;
    }
    /**
     * checks for fliment
     */
    public boolean isFaliment() {
        return faliment;
    }
    /**
     * colects payment from customers
     */
    public void colect(final float pay) {
        this.budget += pay;
    }
    /**
     * calculates the current price based on the number of
     * customers
     */
    public void calculateCurrentPrice() {
        if (faliment) {
            return;
        }
        float profit = Math.round(Math.floor(RATIO * productionCost));
        if ((customers.size() - getToBeFalimentCustomers()) == 0) {
            currentPrice = infrastructureCost
                    + productionCost
                    + profit;
        } else {
            currentPrice = Math.round(Math.floor(infrastructureCost / (customers.size()
                    - getToBeFalimentCustomers())) + productionCost + profit);
        }
    }
    /**
     * reads a set of distributors in a list
     */
    public static void addSet(final ArrayList<Distributor> distributors,
                              final JSONArray distributorsIN) {
        for (Object i : distributorsIN) {
            JSONObject distributor = (JSONObject) i;
            distributors.add(DistributorFactory.createDistributor(
                    DistributorFactory.TypesOfDistributors.SIMPLE_DISTRIBUTOR,
                    (int) (long) distributor.get("id"),
                    (int) (long) distributor.get("contractLength"),
                    (int) (long) distributor.get("initialBudget"),
                    (int) (long) distributor.get("initialInfrastructureCost"),
                    (int) (long) distributor.get("initialProductionCost")));
        }
    }
    /**
     * updates the cost of the distributors in a list
     */
    public static void updateCost(final ArrayList<Distributor> distributors,
                                  final JSONArray distributorsIN) {
        for (Object i : distributorsIN) {
            JSONObject distributorInfo = (JSONObject) i;
            distributors.get((int) (long) distributorInfo.get("id"))
                    .setInfrastructureCost((int) (long) distributorInfo.get("infrastructureCost"));
            distributors.get((int) (long) distributorInfo.get("id"))
                    .setProductionCost((int) (long) distributorInfo.get("productionCost"));
        }
    }
    /**
     * pays and calculates the taxes
     */
    public void pay() {
        if (faliment) {
            return;
        }
        evaluateFalimentCustomers();
        evaluateTerminatedCustomers();
        float expenses = infrastructureCost + productionCost * (customers.size());
        if (expenses > budget) {
            faliment = true;
            customers.clear();
            budget -= expenses;
        } else {
            budget -= expenses;
        }
    }
    /**
     * removes the falimented customers after taxes
     */
    public void evaluateFalimentCustomers() {
        ArrayList<Integer> toRm = new ArrayList<>();
        customers.forEach((key, value) -> {
            if (value.isFaliment() && value.getBkrTime() == 2) {
                toRm.add(value.getId());
            }
        });
        for (int i : toRm) {
            customers.remove(i);
        }
    }
    /**
     * removes ALL the falimented customers
     */
    public void finishFalimentCustomers() {
        ArrayList<Integer> toRm = new ArrayList<>();
        customers.forEach((key, value) -> {
            if (value.isFaliment()) {
                toRm.add(value.getId());
            }
        });
        for (int i : toRm) {
            customers.remove(i);
        }
    }
    /**
     * returns the falimented customers for taxes calulation
     */
    public int getToBeFalimentCustomers() {
        int ret = 0;
        for (Map.Entry element : customers.entrySet()) {
            if (((Customer) element.getValue()).isFaliment()
                    && ((Customer) element.getValue()).getBkrTime() == 1) {
                ret++;
            }
        }
        return ret;
    }
    /**
     * removes the customers that have found a new distributor
     */
    public void evaluateTerminatedCustomers() {
        ArrayList<Integer> toRm = new ArrayList<>();
        for (Map.Entry element : customers.entrySet()) {

            if (((Customer) element.getValue()).getDistributorId() != id) {
                toRm.add(((Customer) element.getValue()).getId());
            }
        }
        for (int i : toRm) {
            customers.remove(i);
        }
    }
}
