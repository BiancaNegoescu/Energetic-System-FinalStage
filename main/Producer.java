package main;

import fileinput.DistributorInputData;
import fileinput.ProducerChangesInputData;
import fileinput.ProducersInputData;

import java.util.List;

public final class Producer {

    private static Producer singleProducerInstance = null;
    private Producer() {

    }

    /**
     * se creaza un obiect al clasei atunci cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static Producer getInstance() {
        if (singleProducerInstance == null) {
            singleProducerInstance = new Producer();
        }
        return singleProducerInstance;
    }

    /**
     *  metoda adauga unui producator contract cu un distribuitor, adica adauga distribuitorul in
     *  lista lui de distribuitori
     * @param producer producatorul pentru care adaug contract
     * @param distributor distribuitorul pe care il adauga la lista de distribuitori
     */
    public void addContractWithDistributor(ProducersInputData producer,
                                            DistributorInputData distributor) {
        producer.getDistributors().add(distributor);
        producer.addObserver(distributor);
    }

    /**
     * metoda updateaza noua cantitate de energie pentru toti producatorii care au primit
     * monnthly update
     * @param producerChanges lista cu id-urile producatorilor si cantitatea noua de energie
     * @param producers lista cu toti producatorii
     */
    public void updateEnergyChanges(final ProducerChangesInputData producerChanges,
                                    final List<ProducersInputData> producers) {
        for (int i = 0; i < producers.size(); i++) {
            if (Integer.compare((int) producers.get(i).getId(),
                    (int) producerChanges.getId()) == 0) {
                producers.get(i).setEnergyPerDistributor(producerChanges
                        .getEnergyPerDistributor(), producers.get(i));
            }
        }
    }
}
