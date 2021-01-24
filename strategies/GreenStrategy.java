package strategies;

import fileinput.DistributorInputData;
import fileinput.ProducersInputData;

import java.util.List;

public final class GreenStrategy implements EnergyStrategy {
    @Override
    public void applyStrategy(final List<ProducersInputData> producers,
                              final DistributorInputData distributor, final int round) {
        Sortings.getInstance().sortByEnergy(producers, distributor, round);
    }
}
