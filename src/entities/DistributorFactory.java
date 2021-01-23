package entities;

import strategies.EnergyChoiceStrategyType;

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
                                                final int energyNeeded,
                                                final EnergyChoiceStrategyType strat) {
        switch (type) {
            case SIMPLE_DISTRIBUTOR: return new Distributor(id,
                    contractLength,
                    initialBudget,
                    initialInfrastructureCost,
                    energyNeeded,
                    strat);
            default: return null;
        }
    }
}
