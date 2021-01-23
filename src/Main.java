import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Customer;
import entities.Distributor;
import entities.Producer;
import format.OutputCustomers;
import format.OutputDistributors;
import format.Output;
import format.OutputProducer;
import format.OutputCustomerToDistribuitor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(args[0]));
        JSONObject input = (JSONObject) obj;
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Distributor> distributors = new ArrayList<>();
        ArrayList<Producer> producers = new ArrayList<>();

        int months = (int) (long) input.get("numberOfTurns");

        JSONObject initialData = (JSONObject) input.get("initialData");

        JSONArray customersIn = (JSONArray) initialData.get("consumers");

        Customer.addSet(customers, customersIn);

        JSONArray distributorsIN = (JSONArray) initialData.get("distributors");

        Distributor.addSet(distributors, distributorsIN);

        JSONArray producersIN = (JSONArray) initialData.get("producers");

        Producer.addSet(producers, producersIN);

        JSONArray monthlyUpdates = (JSONArray) input.get("monthlyUpdates");

        gameLoop(distributors, customers, producers, monthlyUpdates, months);

        ArrayList<OutputCustomers> customersOut = new ArrayList<>();
        ArrayList<OutputDistributors> distributorsOut = new ArrayList<>();
        ArrayList<OutputProducer> producersOut = new ArrayList<>();

        for (Customer customer : customers) {
            customersOut.add(new OutputCustomers(customer.getId(),
                    customer.isFaliment(),
                    (int) customer.getBudget()));
        }

        for (Distributor distributor : distributors) {
            ArrayList<OutputCustomerToDistribuitor> consumerData = new ArrayList<>();
            for (Map.Entry consumerEntryData : distributor.getCustomers().entrySet()) {
                Customer customer = (Customer) consumerEntryData.getValue();
                consumerData.add(new OutputCustomerToDistribuitor(customer.getId(),
                        (int) customer.getPayment(),
                        customer.getContractLength()));
            }
            consumerData.sort((Comparator.comparingInt(
                    OutputCustomerToDistribuitor::getRemainedContractMonths)));
            distributorsOut.add(new OutputDistributors(distributor.getId(),
                    distributor.isFaliment(),
                    (int) distributor.getBudget(),
                    consumerData,
                    (int) distributor.getCurrentPrice(),
                    distributor.getEnergyNeeded(),
                    distributor.getStrategyName()));
        }

        for (Producer producer : producers) {
            producersOut.add(new OutputProducer(
                    producer.getId(),
                    producer.getMaxDistributors(),
                    producer.getPrice(),
                    producer.getType().getLabel(),
                    producer.getEnergy(),
                    producer.getStats()
            ));
        }

        Output output = new Output(customersOut, distributorsOut, producersOut);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        FileWriter file = new FileWriter(args[1]);
        file.write(mapper.writeValueAsString(output));
        file.flush();
    }
    /**
     * The game loop
     */
    static void gameLoop(final ArrayList<Distributor> distributors,
                         final ArrayList<Customer> customers,
                         final ArrayList<Producer> producers,
                         final JSONArray monthlyUpdates,
                         final int rounds) {
        boolean mark;

        for (int i = 1; i <= rounds + 1; i++) {
            mark = false;

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

            if (i != 1 && i != 2) {
                for (Producer producer : producers) {
                    producer.addToStats();
                }
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
                    break;
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
            distributor.checkSuply(producers);
        }

        for (Producer producer : producers) {
            producer.addToStats();
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isFaliment()) {
                distributor.finishFalimentCustomers();
            }
        }
    }
}
