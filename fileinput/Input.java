package fileinput;

import java.util.List;

/**
 * The class contains information about input
 * <p>
 * DO NOT MODIFY
 */
public final class Input {

    private static Input singleInputInstance = null;

    private final long numberofTurns;
    /**
     * List of consumers
     */
    private final List<ConsumerInputData> consumersData;
    /**
     * List of distributors
     */
    private final List<DistributorInputData> distributorsData;
    private final List<ProducersInputData> producersData;
    /**
     * List of monthly updates
     */
    private final List<MonthlyUpdateInputData> monthlyUpdatesData;

    private static Input getInstance() {
        if (singleInputInstance == null) {
            singleInputInstance = new Input();
        }
        return singleInputInstance;
    }

    public Input() {
        this.numberofTurns = 0;
        this.consumersData = null;
        this.distributorsData = null;
        this.producersData = null;
        this.monthlyUpdatesData = null;


    }


    public Input(final long numberofTurns, final List<ConsumerInputData> consumersData,
                 final List<DistributorInputData> distributorsData,
                 final List<ProducersInputData> producersData,
                 final List<MonthlyUpdateInputData> monthlyUpdates) {
        this.numberofTurns = numberofTurns;
        this.consumersData = consumersData;
        this.distributorsData = distributorsData;
        this.producersData = producersData;
        this.monthlyUpdatesData = monthlyUpdates;
    }

    public List<ConsumerInputData> getConsumersData() {
        return consumersData;
    }

    public List<DistributorInputData> getDistributorsData() {
        return distributorsData;
    }

    public long getNumberofTurns() {
        return numberofTurns;
    }

    public List<ProducersInputData> getProducersData() {
        return producersData;
    }

    public List<MonthlyUpdateInputData> getMonthlyUpdatesData() {
        return monthlyUpdatesData;
    }

}
