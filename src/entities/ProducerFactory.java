package entities;

public final class ProducerFactory {
    public enum TypesOfProducers {
        SIMPLE_PRODUCER
    }

    private ProducerFactory() { }
    /**
     * creates a new distributor
     */
    public static Producer createProducer(TypesOfProducers type,
                                          int id, EnergyType enType,
                                          int maxDistributors,
                                          double price,
                                          int energy) {
        switch (type) {
            case SIMPLE_PRODUCER: return new Producer(id, enType, maxDistributors, price, energy);
            default: return null;
        }
    }
}
