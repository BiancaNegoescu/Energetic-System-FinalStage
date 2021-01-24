package main;

import fileinput.ConsumerInputData;
import fileinput.DistributorInputData;

import java.util.List;

public interface CommonMethods {

    /**
     * antetul unei metode comune celor 2 entitati
     * @param consumer un consumator
     * @param distributor un distribuitor
     * @return true or false, daca este bankrupt sau nu
     */
    boolean isBankrupt(ConsumerInputData consumer, DistributorInputData distributor);

    /**
     *antetul unei metode comune celor 2 entitati
     * @param consumers lista cu toti consumatorii
     * @param distributor un distribuitor
     * @param consumer un consumator
     */
    void calculateMonthlyBudget(List<ConsumerInputData> consumers,
                                       DistributorInputData distributor,
                                       ConsumerInputData consumer);

    /**
     * antetul unei metode comune celor 2 entitati
     * @param distributor lista cu toti distribuitorii
     * @param consumers lista cu toti consumerii
     * @param consumer un consumer
     */
    void eraseContract(DistributorInputData distributor,
                       List<ConsumerInputData> consumers, ConsumerInputData consumer);


}
