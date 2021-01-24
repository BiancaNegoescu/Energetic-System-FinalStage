package strategies;

import fileinput.DistributorInputData;
import fileinput.ProducersInputData;
import main.Distributor;
import main.Producer;

import java.util.List;

public final class PriceStrategy implements EnergyStrategy {
    @Override
    public void applyStrategy(final List<ProducersInputData> producers,
                              final DistributorInputData distributor, final int round) {
        List<ProducersInputData> producersSortedByPrice = Sortings.getInstance()
                .sortProducersByPrice(producers);
        for (ProducersInputData producer: producersSortedByPrice) {
            if (distributor.getEnergyNeeded() > distributor.getEnergyAmount()
                    && producer.getDistributors().size() < producer.getMaxDistributors()) {
                Producer.getInstance().addContractWithDistributor(producer, distributor);
                Distributor.getInstance().addContractWithProducer(distributor, producer);
            }
        }
    }
}
