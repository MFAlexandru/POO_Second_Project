import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Customer;
import entities.Distributor;
import entities.Game;
import entities.Producer;
import format.Output;
import format.OutputCustomerToDistribuitor;
import format.OutputCustomers;
import format.OutputDistributors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
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

        Game myGame = Game.getInstance();

        myGame.gameLoop(distributors, customers, producers, monthlyUpdates, months);

        ArrayList<OutputCustomers> customersOut = new ArrayList<>();
        ArrayList<OutputDistributors> distributorsOut = new ArrayList<>();

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
            consumerData.sort(((o1, o2) -> o1.getRemainedContractMonths()
                    - o2.getRemainedContractMonths()));
            distributorsOut.add(new OutputDistributors(distributor.getId(),
                    distributor.isFaliment(),
                    (int) distributor.getBudget(),
                    consumerData));
        }

        Output output = new Output(customersOut, distributorsOut);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        FileWriter file = new FileWriter(args[1]);
        file.write(mapper.writeValueAsString(output));
        file.flush();
    }
}
