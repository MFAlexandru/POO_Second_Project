package entities;

import format.Stat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Producer {
    private int id;
    private EnergyType type;
    private int maxDistributors;
    private double price;
    private int energy;
    private ArrayList<Stat> stats;
    private ArrayList<ProducerObserver> observers;

    public Producer(int id, EnergyType type, int maxDistributors, double price, int energy) {
        this.id = id;
        this.type = type;
        this.maxDistributors = maxDistributors;
        this.price = price;
        this.energy = energy;
        stats = new ArrayList<>();
        observers = new ArrayList<>();
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
     * get the type
     */
    public EnergyType getType() {
        return type;
    }
    /**
     * set the type
     */
    public void setType(EnergyType type) {
        this.type = type;
    }
    /**
     * return the max distributors
     */
    public int getMaxDistributors() {
        return maxDistributors;
    }
    /**
     * set the max diostributors
     */
    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }
    /**
     * get the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * set the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * get the energy
     */
    public int getEnergy() {
        return energy;
    }
    /**
     * return the observer
     */
    public void addObserver(ProducerObserver obs) {
        observers.add(obs);
    }
    /**
     * delete the observers
     */
    public void deleteObserver(ProducerObserver obs) {
        observers.remove(obs);
    }
    /**
     * count the observers
     */
    public int countObservers() {
        return observers.size();
    }
    /**
     * set the energy
     */
    public void setEnergy(int energy) {
        this.energy = energy;
        for (ProducerObserver o : observers) {
            o.update();
        }
    }
    /**
     * add to stat
     */
    public void addToStats() {
        ArrayList<Integer> distr = new ArrayList<>();
        for (ProducerObserver p : observers) {
            distr.add(p.getId());
        }
        distr.sort((a, b) -> a - b);
        stats.add(new Stat(stats.size() + 1, distr));
    }
    /**
     * get the stats
     */
    public ArrayList<Stat> getStats() {
        return stats;
    }
    /**
     * add a set of producers
     */
    public static void addSet(final ArrayList<Producer> producers,
                              final JSONArray producersIN) {
        for (Object i : producersIN) {
            JSONObject producer = (JSONObject) i;
            producers.add(ProducerFactory.createProducer(
                    ProducerFactory.TypesOfProducers.SIMPLE_PRODUCER,
                    (int) (long) producer.get("id"),
                    EnergyType.valueOf((String) producer.get("energyType")),
                    (int) (long) producer.get("maxDistributors"),
                    (double) producer.get("priceKW"),
                    (int) (long) producer.get("energyPerDistributor")));
        }
    }
    /**
     * update the producers
     */
    public static void update(final ArrayList<Producer> producers,
                              final JSONArray producersIN) {
        for (Object i : producersIN) {
            JSONObject producerInfo = (JSONObject) i;
            producers.get((int) (long) producerInfo.get("id"))
                    .setEnergy((int) (long) producerInfo.get("energyPerDistributor"));
        }

    }
}
