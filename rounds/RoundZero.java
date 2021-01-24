package rounds;

import fileinput.DistributorInputData;
import fileinput.Input;
import main.Consumer;
import main.Distributor;
import main.Factory;
import strategies.EnergyStrategyFactory;

public final class RoundZero {

    private Input input;

    public RoundZero(Input input) {
        this.input = input;
    }

    /**
     * metoda alege produceri pentru toti distribuitorii
     */
    public void chooseProducer() {
        for (DistributorInputData distributor: input.getDistributorsData()) {
            EnergyStrategyFactory.getInstance().createStategy(distributor.getProducerStrategy())
                    .applyStrategy(input.getProducersData(), distributor, 0);
        }
    }

    /**
     * metoda calculeaza costul de productie pentru toti distribuitorii
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
     * metoda calculeaza preturile de contracte pentru toti distribuitorii
     */
    public void calculateContractPriceAll() {
        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            Distributor.getInstance().calculateContractPrice(input.getDistributorsData().get(j),
                    input.getConsumersData());
        }
    }

    /**
     * metoda alege distribuitorul cu rata cea mai mica si il adauga tuturor consumerilor deoarece
     * este runda 0, si la randul lui isi face contracte cu toti consumerii
     */
    public void chooseDistributor() {
        DistributorInputData distributor = Distributor.getInstance().distributorWithMinRate(input
                .getDistributorsData());
        Consumer.getInstance().addFirstDistributor(input.getConsumersData(), distributor);
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            Distributor.getInstance().addContract(distributor, input.getConsumersData().get(j));
        }
    }

    /**
     * metoda calculeaza bugetul lunar pentru consumeri si pentru distribuitori
     */
    public void calculateBudget() {
        for (int k = 0; k < input.getConsumersData().size(); k++) {
            Factory.getInstance().computeMonthlyBudget("consumer")
                    .calculateMonthlyBudget(input.getConsumersData(),
                            null, input.getConsumersData().get(k));
        }
        for (int k = 0; k < input.getDistributorsData().size(); k++) {
            Factory.getInstance().computeMonthlyBudget("distributor")
                    .calculateMonthlyBudget(input.getConsumersData(), input.getDistributorsData()
                            .get(k), null);
        }
    }

    /**
     * metoda scade cate o luna din durata contractului si pentru consumeri si pentru
     * distribuitori
     */
    public void deleteMonth() {
        for (int j = 0; j < input.getConsumersData().size(); j++) {
            if (!input.getConsumersData().get(j).isBankrupt()
                    && input.getConsumersData().get(j).getContract() != null) {
                input.getConsumersData().get(j).getContract()
                        .setRemainedContractMonths(input.getConsumersData()
                                .get(j).getContract().getRemainedContractMonths() - 1);
            }
        }
        for (int j = 0; j < input.getDistributorsData().size(); j++) {
            for (int k = 0; k < input.getDistributorsData().get(j).getContracts()
                    .size(); k++) {
                if (input.getDistributorsData().get(j).getContracts().get(k) != null
                        && input.getDistributorsData().get(j).getContracts().get(k)
                        .getRemainedContractMonths() != 0) {
                    input.getDistributorsData().get(j).getContracts().get(k)
                            .setRemainedContractMonths(input.getDistributorsData()
                                    .get(j)
                                    .getContracts().get(k).getRemainedContractMonths() - 1);
                }
            }
        }
    }

}
