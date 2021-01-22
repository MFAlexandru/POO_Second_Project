package entities;

public final class DistributorFactory {
    public enum TypesOfDistributors {
        SIMPLE_DISTRIBUTOR
    }

    private DistributorFactory() { }
    /**
     * creates a new distributor
     */
    public static Distributor createDistributor(final TypesOfDistributors type,
                                                final int id,
                                                final int contractLength,
                                                final int initialBudget,
                                                final int initialInfrastructureCost,
                                                final int initialProductionCost) {
        switch (type) {
            case SIMPLE_DISTRIBUTOR: return new Distributor(id,
                    contractLength,
                    initialBudget,
                    initialInfrastructureCost,
                    initialProductionCost);
            default: return null;
        }
    }
}
