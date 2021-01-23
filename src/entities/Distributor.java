package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import strategies.EnergyChoiceStrategyType;
import strategies.EnergyStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import strategies.GreenStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Distributor {
    private static final float RATIO = 0.2f;
    private static final float MAMBO_NUMBA_5 = 10;
    private int id;
    private int contractLength;
    private float budget;
    private float infrastructureCost;
    private float productionCost;
    private float currentPrice = 0;
    private boolean faliment = false;
    private int energyNeeded;
    private final HashMap<Integer, Customer> customers;
    private String strategyName;

    private ProducerObserver observer;
    private EnergyStrategy currentStrategy;
    private ArrayList<Producer> myProducers;

    public Distributor(final int id,
                       final int contractLength,
                       final int initialBudget,
                       final int initialInfrastructureCost,
                       final int energyNeeded,
                       final EnergyChoiceStrategyType strat) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.productionCost = 0;
        this.energyNeeded = energyNeeded;
        strategyName = strat.getLabel();
        switch (strat) {
            case GREEN:
                currentStrategy = new GreenStrategy();
                break;
            case PRICE:
                currentStrategy = new PriceStrategy();
                break;
            case QUANTITY:

                currentStrategy = new QuantityStrategy();
                break;
            default:
                break;
        }
        customers = new HashMap<>();
        myProducers = new ArrayList<>();
        observer = new ProducerObserver(true, id);
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
     * checks for new producers
     */
    public void checkSuply(ArrayList<Producer> producers) {
        if (!faliment && observer.getLog()) {
            for (Producer p : myProducers) {
                p.deleteObserver(observer);
            }
            myProducers.clear();
            productionCost = 0;
            ArrayList<Producer> newBoyz;
            newBoyz = currentStrategy.chose(producers, energyNeeded);
            for (Producer p : newBoyz) {
                p.addObserver(observer);
                myProducers.add(p);
                productionCost += p.getEnergy() * p.getPrice();
            }
            productionCost = Math.round(Math.floor(productionCost / (float) MAMBO_NUMBA_5));
            observer.setLog(false);
        }
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
                    (int) (long) distributor.get("energyNeededKW"),
                    EnergyChoiceStrategyType.valueOf((String)
                            distributor.get("producerStrategy"))));
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
    /**
     * returns the energy needed
     */
    public int getEnergyNeeded() {
        return energyNeeded;
    }
    /**
     * returns the actual strategy
     */
    public EnergyStrategy getCurrentStrategy() {
        return currentStrategy;
    }
    /**
     * returns the strat name
     */
    public String getStrategyName() {
        return strategyName;
    }
}
