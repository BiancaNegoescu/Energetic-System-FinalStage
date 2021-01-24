package rounds;

import common.Constants;
import fileinput.ConsumerInputData;
import fileinput.DistributorInputData;
import fileinput.Input;
import fileinput.ProducersInputData;
import main.Consumer;
import main.Distributor;
import main.Factory;
import main.MonthlyStats;
import main.Producer;
import strategies.EnergyStrategyFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class OtherRounds {

    private Input input;
    private int numberofRound;

    public OtherRounds(final Input input, final int numberofRound) {
        this.input = input;
        this.numberofRound = numberofRound;
    }

    /**
     * metoda updateaza noile costuri ale distribuitorilor la inceput de runda si adauga si noii
     * consumeri la lista de consumeri
     */
    public void update() {
        for (int j = 0; j < input.getMonthlyUpdatesData().get(numberofRound - 1)
                .getDistributorChanges().size(); j++) {
            if (input.getMonthlyUpdatesData().get(numberofRound - 1).getDistributorChanges()
                    .get(j) != null) {
                Distributor.getInstance().updateCostChanges(input.getMonthlyUpdatesData()
                        .get(numberofRound - 1)
                        .getDistributorChanges()
                        .get(j), input.getDistributorsData());
            }
        }
        Consumer.getInstance().updateConsumers(input.getMonthlyUpdatesData().get(numberofRound - 1)
                .getNewConsumers(), input.getConsumersData());

    }

    /**
     * metoda updateaza noile valori pentru producatori
     */
    public void updateProducers() {
        for (int i = 0; i < input.getMonthlyUpdatesData().get(numberofRound - 1)
        .getProducerChanges().size(); i++) {
            if (input.getMonthlyUpdatesData().get(numberofRound - 1)
                    .getProducerChanges().get(i) != null) {
                Producer.getInstance().updateEnergyChanges(input.getMonthlyUpdatesData()
                        .get(numberofRound - 1)
                        .getProducerChanges()
                        .get(i), input.getProducersData());
            }
        }
    }

    /**
     * metoda recalculeaza noile preturi de contracte pentru toti distribuitorii
     */
    public void updateContractPrices() {
        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            Distributor.getInstance().calculateContractPrice(input.getDistributorsData().get(j),
                    input.getConsumersData());
        }
    }

    /**
     * metoda reziliaza contracte pentru distribuitori si pentru consumatori
     */
    public void eraseContract() {
        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            Factory.getInstance().eraseContract("distributor").eraseContract(input
                    .getDistributorsData().get(j), input.getConsumersData(), null);
        }
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            Factory.getInstance().eraseContract("consumer").eraseContract(null,
                    null,
                    input.getConsumersData().get(j));
        }
    }

    /**
     * metoda alege distribuitorul cu rata cea mai mica si il consumerilor care nu mai au
     * niciun contract sau consumerilor noi si adauga distribuitorul pentru consumerul respectiv,
     * iar distribuitorului ii adauga contract cu consumerul
     */
    public void chooseDistributor() {
        DistributorInputData distributor = Distributor.getInstance().distributorWithMinRate(input
                .getDistributorsData());
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            if (!input.getConsumersData().get(j).isBankrupt() && input
                    .getConsumersData().get(j).getContract() == null) {
                Distributor.getInstance().addContract(distributor, input.getConsumersData().get(j));
                Consumer.getInstance().addDistributor(input.getConsumersData().get(j), distributor);
            }
        }
    }

    /**
     * metoda calculeaza bugetul lunar pentru consumeri si pentru distribuitori
     */
    public void calculateBudget() {
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            Factory.getInstance().computeMonthlyBudget(Constants.CONSUMER)
                    .calculateMonthlyBudget(null, input.getConsumersData().get(j)
                            .getDistribuitor(), input.getConsumersData().get(j));
        }
        for (int k = 0; k < input.getDistributorsData().size(); k++) {
            Factory.getInstance().computeMonthlyBudget(Constants.DISTRIBUTOR)
                    .calculateMonthlyBudget(input.getConsumersData(),
                            input.getDistributorsData().get(k), null);
        }
    }

    /**
     * metoda scade cate o luna din durata contractului si pentru consumeri si pentru
     * distribuitori
     */
    public void deleteMonth() {
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            if (!input.getConsumersData().get(j).isBankrupt()
                    && input.getConsumersData().get(j).getContract() != null
                    && input.getConsumersData().get(j).getContract()
                    .getRemainedContractMonths() != 0) {
                input.getConsumersData().get(j).getContract()
                        .setRemainedContractMonths(input.getConsumersData()
                                .get(j).getContract().getRemainedContractMonths() - 1);
            }
        }

        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            for (int k = 0; k < input.getDistributorsData().get(j).getContracts().size(); k++) {
                if (input.getDistributorsData().get(j).getContracts().get(k) != null
                        && input.getDistributorsData().get(j).getContracts().get(k)
                        .getRemainedContractMonths() != 0) {
                    input.getDistributorsData().get(j).getContracts().get(k)
                            .setRemainedContractMonths(input.getDistributorsData()
                                    .get(j).getContracts().get(k).getRemainedContractMonths() - 1);
                }
            }
        }
    }

    /**
     * metoda sterge contractele consumatorilor care sunt bankupt din lista de contracte ale
     * distribuitorului lor
     */
    public void deleteBankruptConsumers() {
        for (int k = 0; k < input.getConsumersData().size(); k++) {
            if (input.getConsumersData().get(k).isBankrupt()) {
                for (int j = 0; j < input.getConsumersData().get(k).getDistribuitor().getContracts()
                        .size(); j++) {
                    if (Long.compare(input.getConsumersData().get(k).getId(),
                            input.getConsumersData().get(k).getDistribuitor().getContracts().get(j)
                                    .getConsumerId()) == 0) {
                        input.getConsumersData().get(k).getDistribuitor().getContracts()
                                .remove(input.getConsumersData().get(k).getDistribuitor()
                                        .getContracts().get(j));
                        j--;
                    }
                }
            }
        }
    }

    /**
     *  metoda seteaza contractul si distribuitorul unui consumator pe null, pentru consumatorii
     *  ai caror distribuitori au dat faliment
     */
    public void consumersWithoutDistributor() {
        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            for (int k = 0; k < input.getConsumersData().size(); k++) {
                if (input.getDistributorsData().get(j).isBankrupt()) {
                    int finalJ = j;
                    List<ConsumerInputData> consumersWithoutDistributor = input
                            .getConsumersData().stream()
                            .filter(o1 -> input.getDistributorsData().get(finalJ)
                                    .getContracts().stream()
                                    .anyMatch(o2 -> o2.getConsumerId() == o1.getId()))
                            .collect(Collectors.toList());
                    for (int l = 0; l < consumersWithoutDistributor.size(); l++) {
                        consumersWithoutDistributor.get(l).setContract(null);
                        consumersWithoutDistributor.get(l).setDistribuitor(null);
                    }
                }
            }
        }
    }

    /**
     * metoda alege producatori pentru toti distribuitorii
     */
    public void chooseProducer() {
        List<ProducersInputData> orderedProducers = input.getProducersData().stream()
                .sorted(Comparator.comparingLong(ProducersInputData::getId))
                .collect(Collectors.toList());
        for (DistributorInputData distributor: input.getDistributorsData()) {
            if (distributor.getProducerChangedEnergy() == 1) {
                //isi alege producer nou daca si-a modificat energia
                EnergyStrategyFactory.getInstance().createStategy(distributor.getProducerStrategy())
                        .applyStrategy(orderedProducers, distributor, numberofRound);
               // energyStrategy.applyStrategy(orderedProducers, distributor, numberofRound);
            }
        }
    }

    /**
     * metoda calculeaza costul de productie pentru toti producatorii
     */
    public void calculateProductionCost() {
        for (DistributorInputData distributor: input.getDistributorsData()) {
            float cost = 0;
            for (int i = 0; i < distributor.getProducers().size(); i++) {
                cost = cost + distributor.getProducers().get(i).getEnergyPerDistributor()
                        * distributor.getProducers().get(i).getPriceKW();
            }
            long productionCost = Math.round(Math.floor(cost / 10));
            distributor.setProductionCost(productionCost);
        }
    }

    /**
     * metoda scrie la final de luna pentru fiecare producator luna care a trecut si id-urile
     * distribuitorilor care au contract cu el
     */
    public void updateMonthlyStats() {
        for (ProducersInputData producer : input.getProducersData()) {
            MonthlyStats monthlyStat = new MonthlyStats(numberofRound);
            for (DistributorInputData distributor : producer.getDistributors()) {
                monthlyStat.setDistributorsIDs(distributor.getId());
            }
            monthlyStat.sort();
            producer.setMonthlyStats(monthlyStat);
        }
    }

    /**
     * metoda alege noi producatori pentru toti distribuitorii care au macar un producator care si-a
     * updatat cantitatea de energie
     */
    public void chooseNewProducers() {
        for (DistributorInputData distributor: input.getDistributorsData()) {
            if (distributor.getProducerChangedEnergy() == 1) {
                deleteDistributorFromProducers(distributor);
                deleteProducersFromDistributor(distributor);
                distributorChoosesProducers(distributor);
                resetDistributor(distributor);
                calculateNewProductionCost(distributor);
            }
        }
    }

    /**
     * metoda cauta distribuitorul in listele de distribuitori ale tuturor producatorilor si daca
     * il gaseste, il sterge
     * @param distributor distribuitorul al carui producator si a updatat energia
     */
    public void deleteDistributorFromProducers(DistributorInputData distributor) {
        for (ProducersInputData producer: input.getProducersData()) {
            for (int i = 0; i < producer.getDistributors().size(); i++) {
                if (Long.compare(producer.getDistributors().get(i).getId(),
                        distributor.getId()) == 0) {
                    producer.getDistributors().remove(producer.getDistributors().get(i));
                    i--;
                }
            }
        }
    }

    /**
     * metoda sterge toti producatorii pentru distribuitor
     * @param distributor distribuitorul al carui producator si-a updatat energia
     */
    public void deleteProducersFromDistributor(DistributorInputData distributor) {
        distributor.getProducers().removeAll(distributor.getProducers());
        distributor.setEnergyAmount(0);
    }

    /**
     * metoda alege noi producatori pentru distribuitor
     * @param distributor distribuitorul al carui producator si-a updatat energia
     */
    public void distributorChoosesProducers(DistributorInputData distributor) {
        List<ProducersInputData> orderedProducers = input.getProducersData().stream()
                .sorted(Comparator.comparingLong(ProducersInputData::getId))
                .collect(Collectors.toList());
        EnergyStrategyFactory.getInstance().createStategy(distributor.getProducerStrategy())
                .applyStrategy(orderedProducers, distributor, numberofRound);
    }

    /**
     * metoda seteaza inapoi pe 0 flagul care anunta daca producatorul unui distribuitor
     * si-a modificat energia sau nu
     * @param distributor distribuitorul al carui producator si-a updatat energia
     */
    public void resetDistributor(DistributorInputData distributor) {
        distributor.setBackProducerChangedEnergy();
    }

    /**
     * metoda calculeaza noul cost de productie pentru distribuitor
     * @param distributor distribuitorul al carui producator si-a updatat energia
     */
    public void calculateNewProductionCost(DistributorInputData distributor) {
        float cost = 0;
        for (int i = 0; i < distributor.getProducers().size(); i++) {
            cost = cost + distributor.getProducers().get(i).getEnergyPerDistributor()
                    * distributor.getProducers().get(i).getPriceKW();
        }
        long productionCost = Math.round(Math.floor(cost / 10));
        distributor.setProductionCost(productionCost);
    }
}



