package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Cladd for simulating the game
 */
public final class Game {

    private static final Game INSTANCE = new Game();

    private Game() { }

    public static Game getInstance() {
        return INSTANCE;
    }
    /**
     * The game loop
     */
    public void gameLoop(final ArrayList<Distributor> distributors,
                                final ArrayList<Customer> customers,
                                final ArrayList<Producer> producers,
                                final JSONArray monthlyUpdates,
                                final int rounds) {
        boolean mark = false;

        for (int i = 1; i <= rounds + 1; i++) {
            mark = false;
            System.out.print(i + "\n");

            if (i != 1) {
                JSONObject update = (JSONObject) monthlyUpdates.get(i - 2);
                JSONArray updCustomers = (JSONArray) update.get("newConsumers");
                JSONArray updDistributors = (JSONArray) update.get("distributorChanges");
                Customer.addSet(customers, updCustomers);
                Distributor.updateCost(distributors, updDistributors);
            }

            for (Distributor distributor : distributors) {
                distributor.checkSuply(producers);
                distributor.calculateCurrentPrice();
            }

            for (Customer customer: customers) {
                customer.colect();
                customer.pay(distributors);
            }

            for (Distributor distributor : distributors) {
                distributor.checkSuply(producers);
                distributor.pay();
            }

            for (Distributor distributor : distributors) {
                if (!distributor.isFaliment()) {
                    mark = true;
                }
            }
            if (!mark) {
                break;
            }

            if (i != 1) {
                JSONObject update = (JSONObject) monthlyUpdates.get(i - 2);
                JSONArray updProducers = (JSONArray) update.get("producerChanges");
                Producer.update(producers, updProducers);
            }
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isFaliment()) {
                distributor.finishFalimentCustomers();
            }
        }
    }
}
