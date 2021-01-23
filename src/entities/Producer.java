package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class Producer extends Observable {
    private int id;
    private EnergyType type;
    private int maxDistributors;
    private double price;
    private int energy;

    public Producer(int id, EnergyType type, int maxDistributors, double price, int energy) {
        this.id = id;
        this.type = type;
        this.maxDistributors = maxDistributors;
        this.price = price;
        this.energy = energy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnergyType getType() {
        return type;
    }

    public void setType(EnergyType type) {
        this.type = type;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        System.out.print(this.id + " ");
        this.energy = energy;
        setChanged();
        notifyObservers();
        deleteObservers();
    }

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

    public static void update(final ArrayList<Producer> producers,
                              final JSONArray producersIN) {
        for (Object i : producersIN) {
            JSONObject producerInfo = (JSONObject) i;
            producers.get((int) (long) producerInfo.get("id"))
                    .setEnergy((int) (long) producerInfo.get("energyPerDistributor"));
        }

    }
}
