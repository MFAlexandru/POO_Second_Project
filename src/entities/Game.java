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
                                final JSONArray monthlyUpdates,
                                final int rounds) {
        boolean mark = false;

        for (Distributor distributor : distributors) {
            distributor.calculateCurrentPrice();
        }

        for (Customer customer: customers) {
            customer.colect();
            customer.pay(distributors);
        }

        for (Distributor distributor : distributors) {
            distributor.pay();
        }


        for (int i = 0; i < rounds; i++) {
            mark = false;
            JSONObject update = (JSONObject) monthlyUpdates.get(i);
            JSONArray updCustomers = (JSONArray) update.get("newConsumers");
            JSONArray updDistributors = (JSONArray) update.get("costsChanges");
            Customer.addSet(customers, updCustomers);
            Distributor.updateCost(distributors, updDistributors);

            for (Distributor distributor : distributors) {
                distributor.calculateCurrentPrice();
            }

            for (Customer customer: customers) {
                customer.colect();
                customer.pay(distributors);
            }

            for (Distributor distributor : distributors) {
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
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isFaliment()) {
                distributor.finishFalimentCustomers();
            }
        }
    }
}
