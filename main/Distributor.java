package main;

import fileinput.ConsumerInputData;
import fileinput.ContractsInputData;
import fileinput.DistributorChangesInputData;
import fileinput.DistributorInputData;
import fileinput.ProducersInputData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Distributor implements CommonMethods {

   private static Distributor singleDistributorInstance = null;

    private Distributor() {
    }

    /**
     * se creaza un obiect al clasei atunci cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static Distributor getInstance() {
        if (singleDistributorInstance == null) {
            singleDistributorInstance = new Distributor();
        }
        return singleDistributorInstance;
    }

    @Override
    public boolean isBankrupt(final ConsumerInputData consumer,
                              final DistributorInputData distributor) {
       if (distributor.getInitialBudget() < 0) {
           distributor.setBankrupt(true);
           return true;
       }
       return false;
    }

    @Override
    public void calculateMonthlyBudget(final List<ConsumerInputData> consumers,
                                       final DistributorInputData distributor,
                                       final ConsumerInputData consumer) {
        if (!isBankrupt(null, distributor)) {
            long monthlyCosts = distributor.getInitialInfrastructureCost()
                    + distributor.getProductionCost() * distributor.getContracts().size();
            long numberOfPayers = countPayers(consumers);
            long income = calculateIncome(distributor.getContracts(), consumers);
            long monthlyBudget = distributor.getInitialBudget() + income - monthlyCosts;
            distributor.setInitialBudget(monthlyBudget);
            if (monthlyBudget < 0) {
                distributor.setBankrupt(true);
            }
        }
    }

    /**
     * metoda calculeaza venitul lunar pentru un distribuitor
     * se iau in calcul doar consumatorii care nu sunt datori si nici falimentati, deoarece
     * ei nu pot plati in luna respectiva rata
     * @param contracts lista de contracte a unui distribuitor
     * @param consumers lista cu toti consumatorii
     * @return venitul lunar
     */
    public long calculateIncome(final List<ContractsInputData> contracts,
                                final List<ConsumerInputData> consumers) {
        List<ConsumerInputData> nonDepters = consumers.stream().filter(o1 -> !o1.isDebtor())
                .filter(o1 -> !o1.isBankrupt()).collect(Collectors.toList());
        List<ConsumerInputData> hisNonDespters = nonDepters.stream().filter(o1 -> contracts.stream()
                .anyMatch(o2 -> o2.getConsumerId() == o1.getId())).collect(Collectors.toList());
        long income = hisNonDespters.stream().filter(o1 -> o1.getContract() != null)
                .mapToInt(o1 -> (int) o1.getContract().getPrice()).sum();
        return income;
    }

    /**
     * metoda adauga la lista de contracte a unui distribuitor inca un contract si se
     * incrementeaza numarul de contracte
     * @param distributor distribuitorul pentru care se adauga contract
     * @param consumer consumatorul care isi face contract la acel distributor
     */
    public void addContract(final DistributorInputData distributor,
                            final ConsumerInputData consumer) {
        distributor.setNumberofContracts(distributor.getnumberofConsumers() + 1);
        distributor.setContracts(new ContractsInputData(consumer.getId(),
                distributor.getContractPrice(), distributor.getContractLength()));
    }

    /**
     * metoda calculeaza cati platitori are un distribuitor in luna respectiva
     * @param consumers lista cu toti consumerii
     * @return numarul de consumatori platitori
     */
    public long countPayers(final List<ConsumerInputData> consumers) {
        long count = consumers.stream().filter(o1 -> !o1.isDebtor())
                .filter(o1 -> !o1.isBankrupt()).count();
        return count;
    }

    /**
     * metoda calculeaza pretul contractului in functie de numarul de consumatori ai
     * distribuitorului
     * @param distributor distribuitorul pentru care se calculeaza contractul
     */
    public void calculateContractPrice(final DistributorInputData distributor,
                                       final List<ConsumerInputData> consumers) {
        if (!distributor.isBankrupt()) {
            if (distributor.getContracts().size() == 0) {
                long contractPrice = Math.round(distributor.getInitialInfrastructureCost()
                        + distributor.getProductionCost()
                        + Math.round(Math.floor(distributor.getProductionCost() * 0.2)));
                distributor.setContractPrice(contractPrice);
            } else {
                long contractPrice = Math.round(Math.floor(distributor
                        .getInitialInfrastructureCost()
                        / distributor.getContracts().size())
                        + distributor.getProductionCost()
                        + Math.round(Math.floor(distributor.getProductionCost() * 0.2)));
                distributor.setContractPrice(contractPrice);
            }
        }
    }

    /**
     * metoda sterge pentru un distribuitor contractele care au ajuns la 0 luni de contract,
     * adica cele care au expirat si mai sterge si contractele consumatorilor care au dat
     * faliment
     * @param distributor un distributor
     * @param consumers lista de consumatori
     */
    public void eraseContract(final DistributorInputData distributor,
                              final List<ConsumerInputData> consumers,
                              final ConsumerInputData consumer) {
        if (distributor.getContracts() != null) {
            for (int i = 0; i < distributor.getContracts().size(); i++) {
                if (distributor.getContracts().get(i) != null
                        && distributor.getContracts().get(i).getRemainedContractMonths() == 0) {
                    distributor.getContracts().remove(distributor.getContracts().get(i));
                    i--;
                }
            }
            for (int i = 0; i < consumers.size(); i++) {
                for (int j = 0; j < distributor.getContracts().size(); j++) {
                    if (consumers.get(i).isBankrupt()) {
                    if (Long.compare(distributor.getContracts().get(j).getConsumerId(),
                            consumers.get(i).getId()) == 0) {
                        distributor.getContracts().remove(distributor.getContracts().get(j));
                        j--;
                    }
                    }
                }
            }
        }
    }

    /**
     * metoda calculeaza distribuitorul cu cea mai mica rata si daca 2 distribuitori au rate
     * egale, il intoarce pe primul din input
     * @param distributors lista de distribuitori
     * @return un distribuitor cu cea mai mica rata
     */
    public DistributorInputData distributorWithMinRate(final List<DistributorInputData>
                                                               distributors) {
        Comparator<DistributorInputData> sortByPrice = (o1, o2) -> (int) (o1.getContractPrice()
                - o2.getContractPrice());
        Comparator<DistributorInputData> sortByID = (o1, o2) -> (int) (o1.getId() - o2.getId());
        List<DistributorInputData> sortedDistributors = distributors.stream()
                .filter(o1 -> !o1.isBankrupt())
                .sorted(sortByPrice.thenComparing(sortByID)).filter(o1 -> !o1.isBankrupt())
                .collect(Collectors.toList());
        return sortedDistributors.get(0);
    }

    /**
     * metoda updateaza lista de costuri in fiecare luna
     * @param costChanges lista cu schimbari de costuri
     * @param distributors lista de distribuitori
     */
    public void updateCostChanges(final DistributorChangesInputData distributorChanges,
                                  final List<DistributorInputData> distributors) {
        for (int i = 0; i < distributors.size(); i++) {
            if (Integer.compare((int) distributors.get(i).getId(),
                    (int) distributorChanges.getId()) == 0) {
                distributors.get(i).setInitialInfrastructureCost(distributorChanges
                        .getInfrastructureCost());
            }
        }
    }

    /**
     * metoda adauga unui distribuitor contract cu un producator, adica adauga producatorul in
     * lista lui de producatori
     * @param distributor distribuitorul pentru care se adauga producatorul
     * @param producer producatorul pe care il adauga in lista de producatori
     */
    public void addContractWithProducer(DistributorInputData distributor,
                                        ProducersInputData producer) {
        distributor.getProducers().add(producer);
        distributor.setEnergyAmount(distributor.getEnergyAmount()
                + producer.getEnergyPerDistributor());
    }

    /**
     * metoda calculeaza costul de productie pentru toti distribuitorii
     * @param distributors lista cu toti distribuitorii
     */
    public void calculateProductionCost(final List<DistributorInputData> distributors) {
        for (DistributorInputData distributor : distributors) {
            float cost = 0;
            for (int i = 0; i < distributor.getProducers().size(); i++) {
                cost = cost + distributor.getProducers().get(i).getEnergyPerDistributor()
                        * distributor.getProducers().get(i).getPriceKW();
            }
            long productionCost = Math.round(Math.floor(cost / 10));
            distributor.setProductionCost(productionCost);
        }
    }

}
