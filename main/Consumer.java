package main;


import fileinput.ConsumerInputData;
import fileinput.ContractsInputData;
import fileinput.DistributorInputData;

import java.util.List;


public final class Consumer implements CommonMethods {

    private static Consumer singleConsumerInstance = null;

    private Consumer() {

    }

    /**
     * se creaza un obiect al clasei cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static Consumer getInstance() {
        if (singleConsumerInstance == null) {
            singleConsumerInstance = new Consumer();
        }
        return singleConsumerInstance;
    }

    @Override
    public boolean isBankrupt(final ConsumerInputData consumer,
                              final DistributorInputData distributor) {
        if (Boolean.compare(consumer.isDebtor(), true) == 0) {
            if (Boolean.compare(consumer.isBankrupt(), false) == 0) {
                long payment = 0;
                //plata cu penalizare
                long income = consumer.getBudget() + consumer.getMonthlyIncome();
                if (consumer.getContract() != null) {
                    payment = consumer.getDebt() + consumer.getContract().getPrice();
                }
                //daca nu poate plati datoria
                if (payment > income) {
                    if (Boolean.compare(consumer.isBankrupt(), false) == 0) {
                        consumer.setBudget(income);
                        consumer.setBankrupt(true);
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void calculateMonthlyBudget(final List<ConsumerInputData> consumers,
                                       final DistributorInputData distribuitor,
                                       final ConsumerInputData consumer) {
        if (Boolean.compare(isBankrupt(consumer, distribuitor), false) == 0
                && consumer.getContract() != null) {
            long monthlyBudget = consumer.getBudget() + consumer.getMonthlyIncome()
                    - consumer.getContract().getPrice();
            // daca nu isi permite sa plateasca rata, nu mai scad rata si l marchez ca datornic
            if (monthlyBudget < 0) {
                monthlyBudget = monthlyBudget + consumer.getContract().getPrice();
                calculateDebt(consumer);
            }
            // updatez noul budget
            consumer.setBudget(monthlyBudget);
        }
        }

    /**
     * metoda adauga primul distribuitor pentru toti consumerii fiind acelasi
     * @param consumers lista cu toti consumerii
     * @param distributor distribuitorul
     */
    public void addFirstDistributor(final List<ConsumerInputData> consumers,
                                    final DistributorInputData distributor) {
        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).setContract(new ContractsInputData(distributor.getId(),
                    distributor.getContractPrice(), distributor.getContractLength()));
            consumers.get(i).setDistribuitor(distributor);
        }
    }

    /**
     * metoda adauga un distribuitor doar pentru un consumer
     * @param consumer un consumer
     * @param distributor distribuitorul
     */
    public void addDistributor(final ConsumerInputData consumer,
                               final DistributorInputData distributor) {
        consumer.setDistribuitor(distributor);
        consumer.setContract(new ContractsInputData(distributor.getId(),
                distributor.getContractPrice(), distributor.getContractLength()));
        // setez nr de contracte
    }

    /**
     * metoda updateaza numarul de luni de contract ramase pentru fiecare consumer si daca si-a
     * terminat contractul (nr de luni ramase a ajuns la 0) se scade din numarul de contracte
     * al distribuitorului
     * @param consumers lista cu toti consumerii
     * @param distributor distribuitor
     */


    public void eraseContract(final DistributorInputData distributor,
                              final List<ConsumerInputData> consumers,
                              final ConsumerInputData consumer) {
        if (consumer.getContract() != null
                && consumer.getContract().getRemainedContractMonths() == 0
    || consumer.getContract() != null && consumer.isBankrupt()) {
            consumer.setContract(null);
        }
    }


    /**
     * metoda adauga noii consumeri primiti intr-o luna
     * @param newConsumers noii consumatori
     * @param oldConsumers lista cu vechii consumatori
     */
    public void updateConsumers(final List<fileinput.Consumer> newConsumers,
                                final List<ConsumerInputData> consumers) {
        for (int i = 0; i < newConsumers.size(); i++) {
            consumers.add(new ConsumerInputData(newConsumers.get(i).getId(), newConsumers.get(i)
                    .getInitialBudget(), newConsumers.get(i).getMonthlyIncome(),
                    "consumer"));
        }
    }

    /**
     * metoda calculeaza datoria unui consumator
     * @param consumer consumatorul pentru care se calculeaza datoria
     */
    public void calculateDebt(final ConsumerInputData consumer) {
        if (consumer.getContract() != null) {
            consumer.setDebt(Math.round((Math.floor(1.2 * consumer.getContract().getPrice()))));
            consumer.setDebtor(true);
        }
    }
}
