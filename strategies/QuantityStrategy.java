package strategies;

import fileinput.DistributorInputData;
import fileinput.ProducersInputData;
import main.Distributor;
import main.Producer;

import java.util.List;

public final class QuantityStrategy implements EnergyStrategy {
    @Override
    public void applyStrategy(final List<ProducersInputData> producers,
                              final DistributorInputData distributor, final int round) {
        List<ProducersInputData> producersSortedByQuantity = Sortings.getInstance()
                .sortProducersByQuantity(producers);
        for (ProducersInputData producer: producersSortedByQuantity) {
            if (distributor.getEnergyAmount() < distributor.getEnergyNeeded()
                    && producer.getDistributors().size() < producer.getMaxDistributors()) {
                Producer.getInstance().addContractWithDistributor(producer, distributor);
                Distributor.getInstance().addContractWithProducer(distributor, producer);
            }
        }
    }
}
