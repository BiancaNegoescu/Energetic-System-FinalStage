package fileoutput;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileinput.ContractsInputData;

import java.util.ArrayList;

@JsonPropertyOrder({"id", "energyNeededKW", "contractCost", "budget", "producerStrategy",
        "isBankrupt"})
public final class DistributorOutputData {
    private long id;
    private long energyNeededKW;
    private long contractCost;
    private long budget;
    private String producerStrategy;
    private final boolean isBankrupt;
    private ArrayList<ContractsInputData> contracts;

    public DistributorOutputData(final long id, final long energyNeededKW, final long contractCost,
                                 final long budget, final String producerStrategy,
                                 final boolean bankrupt,
                                 final ArrayList<ContractsInputData> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = bankrupt;
        this.contracts = contracts;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(long energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public long getContractCost() {
        return contractCost;
    }

    public void setContractCost(long contractCost) {
        this.contractCost = contractCost;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    /**
     * getter pentru a verifica daca distribuitorul a dat faliment sau nu
     *
     * @return true daca a dat faliment, false contrat
     */
    public boolean getisBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean isBankrupt) {
        isBankrupt = isBankrupt;
    }

    public ArrayList<ContractsInputData> getContracts() {
        return contracts;
    }

    public void setContracts(final ArrayList<ContractsInputData> contracts) {
        this.contracts = contracts;
    }

}
