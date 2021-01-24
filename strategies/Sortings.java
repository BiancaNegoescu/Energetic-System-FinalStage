package strategies;

import entities.EnergyType;
import fileinput.DistributorInputData;
import fileinput.ProducersInputData;
import main.Distributor;
import main.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Sortings {

    private static Sortings singleSortingsInstance = null;

    private Sortings() {

    }

    /**
     * se creaza un obiect al clasei atunci cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static Sortings getInstance() {
        if (singleSortingsInstance == null) {
            singleSortingsInstance = new Sortings();
        }
        return singleSortingsInstance;
    }

    /**
     * metoda sorteaza producatorii dupa energie
     * @param producers lista cu toti producatorii
     * @param distributor distribuitorul pentru care se aleg producatori
     * @param round numarul rundei
     */
    public void sortByEnergy(final List<ProducersInputData> producers,
                             final DistributorInputData distributor, final int round) {
        List<ProducersInputData> greenProducers = new ArrayList<>();
        for (ProducersInputData producer : producers) {
            if (EnergyType.valueOf(producer.getEnergyType()).isRenewable()) {
                greenProducers.add(producer);
            }
        }

        List<ProducersInputData> otherProducers = sortProducersByPrice(producers);
        if (distributor.getEnergyNeeded() > distributor.getEnergyAmount()) {
            int k = 0;
            while (distributor.getEnergyNeeded() > distributor.getEnergyAmount()
                    && k < otherProducers.size()) {
                if (greenProducers.contains(otherProducers.get(k))
                        && (!distributor.getProducers().contains(otherProducers.get(k)))) {
                    if (otherProducers.get(k).getDistributors().size() < otherProducers.get(k)
                            .getMaxDistributors()) {
                        Distributor.getInstance().addContractWithProducer(distributor,
                                otherProducers.get(k));
                        Producer.getInstance().addContractWithDistributor(otherProducers.get(k),
                                distributor);
                    }
                }
                k++;
            }
        }
        if (distributor.getEnergyNeeded() > distributor.getEnergyAmount()) {
            int k = 0;
            while (distributor.getEnergyNeeded() > distributor.getEnergyAmount()
                    && k < otherProducers.size()) {
                if (otherProducers.get(k).getDistributors().size() < otherProducers.get(k)
                        .getMaxDistributors()) {
                    if (!distributor.getProducers().contains(otherProducers.get(k))) {
                        Distributor.getInstance().addContractWithProducer(distributor,
                                otherProducers.get(k));
                        Producer.getInstance().addContractWithDistributor(otherProducers.get(k),
                                distributor);
                    }
                }
                k++;
            }
        }
    }

    /**
     * metoda sorteaza producatorii dupa pret
     * @param producers lista cu toti producatorii
     * @return o lista de producatori sortati dupa strategia Price
     */
    public List sortProducersByPrice(final List<ProducersInputData> producers) {
        Comparator<ProducersInputData> sortByQuantity = (o1, o2) -> (int)
                (o2.getEnergyPerDistributor() - o1.getEnergyPerDistributor());
        List<ProducersInputData> otherProducers = producers.stream()
                .sorted(Comparator.comparing(ProducersInputData::getPriceKW)
                        .thenComparing(sortByQuantity)).collect(Collectors.toList());
        return otherProducers;
    }

    /**
     * metoda sorteaza producatorii dupa cantitate
     * @param producers lista cu toti producatorii
     * @return o lista de producatori sortati dupa strategia Quantity
     */
    public List sortProducersByQuantity(final List<ProducersInputData> producers) {
        List<ProducersInputData> otherProducers = producers.stream().sorted(Comparator
                .comparingLong(ProducersInputData::getEnergyPerDistributor).reversed())
                .collect(Collectors.toList());
        return otherProducers;
    }
}
