package entities;

public final class CustomerFactory {
    public enum TypesOfCustomers {
        SIMPLE_CUSTOMER
    }

    private CustomerFactory() { }
    /**
     * creates a new customer
     */
    public static Customer createCustomer(final TypesOfCustomers type,
                                          final int id,
                                          final int initialBudget,
                                          final int monthlyIncome) {
        switch (type) {
            case SIMPLE_CUSTOMER: return new Customer(id,
                    initialBudget,
                    monthlyIncome);
            default: return null;
        }
    }
}
