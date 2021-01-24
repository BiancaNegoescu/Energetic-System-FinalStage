package fileinput;

import java.util.List;

public final class MonthlyUpdateInputData {

    private final List<Consumer> newConsumers;
    private final List<DistributorChangesInputData> distributorChanges;
    private final List<ProducerChangesInputData> producerChanges;

    public MonthlyUpdateInputData(final List<Consumer> newConsumers,
                                  final List<DistributorChangesInputData> distributorChanges,
                                  final List<ProducerChangesInputData> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<DistributorChangesInputData> getDistributorChanges() {
        return distributorChanges;
    }

    public List<ProducerChangesInputData> getProducerChanges() {
        return producerChanges;
    }

}
