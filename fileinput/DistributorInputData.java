package fileinput;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public final class DistributorInputData implements Observer {
    private long id;
    private long initialBudget;
    private long initialInfrastructureCost;
    private long contractLength;
    private boolean isBankrupt;
    private ArrayList<ContractsInputData> contracts;
    private long contractPrice;
    private String entityType;
    private int numberofConsumers;
    private int nrOldConsumers;
    private String producerStrategy;
    private long energyNeeded;
    private long energyAmount;
    private long productionCost;
    private int producerChangedEnergy;
    private List<ProducersInputData> producers;

    public DistributorInputData(final long id, final long initialBudget,
                                final long initialInfrastructureCost,
                                final long energyNeeded,
                                /*final long initialProductionCost, */final long contractLength,
                                final String producerStrategy,
                                final ArrayList<ContractsInputData> contracts,
                                final String entityType) {
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
      //  this.initialProductionCost = initialProductionCost;
        this.isBankrupt = false;
        this.contracts = new ArrayList<>();
        this.entityType = entityType;
        this.numberofConsumers = 0;
        this.nrOldConsumers = 0;
        this.producerStrategy = producerStrategy;
        this.energyNeeded = energyNeeded;
        this.producerChangedEnergy = 0; // initial flagul e setat pe 0
        this.producers = new ArrayList<>();
        this.energyAmount = 0;
    }

    public DistributorInputData() {

    }

    public long getId() {
        return id;
    }

    public long getInitialBudget() {
        return initialBudget;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public ArrayList<ContractsInputData> getContracts() {
        return contracts;
    }

    public int getNrOldConsumers() {
        return nrOldConsumers;
    }

    public void setBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public void setInitialBudget(final long initialBudget) {
        this.initialBudget = initialBudget;
    }

    public long getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final long initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    /**
     * getter pentru numarul de consumatori ai unui distributor
     * @return numarul de consumatori
     */
    public int getnumberofConsumers() {
        return numberofConsumers;
    }

    public void setNumberofContracts(final int numberofConsumers) {
        this.numberofConsumers = numberofConsumers;
    }

    public long getContractLength() {
        return contractLength;
    }

    public long getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(final long contractPrice) {
        this.contractPrice = contractPrice;
    }

    public String getEntityType() {
        return entityType;
    }

    /**
     * metoda care adauga la lista de contracte a distribuitorului cate un contract
     * @param contract un contract de adaugat la lista de contracte
     */
    public void setContracts(final ContractsInputData contract) {
        this.contracts.add(contract);
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public long getEnergyNeeded() {
        return energyNeeded;
    }

    public void setEnergyNeeded(long energyNeeded) {
        this.energyNeeded = energyNeeded;
    }

    public long getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(long energyAmount) {
        this.energyAmount = energyAmount;
    }

    public List<ProducersInputData> getProducers() {
        return producers;
    }

    public void setProducerChangedEnergy(int producerChangedEnergy) {
        this.producerChangedEnergy = producerChangedEnergy;
    }

    public long getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(long productionCost) {
        this.productionCost = productionCost;
    }

    public void setProducers(List<ProducersInputData> producers) {
        this.producers = producers;
    }

    public int getProducerChangedEnergy() {
        return producerChangedEnergy;
    }

    @Override
    public void update(Observable o, Object producer) {
        for (int i = 0; i < ((ProducersInputData) producer).getDistributors().size(); i++) {
            ((ProducersInputData) producer).getDistributors().get(i).setProducerChangedEnergy(1);
            //i am notificat ca producerul si a updatat energia
        }
    }

    /**
     * metoda seteaza inapoi pe 0 flagul care anunta daca producatorul distribuitorului si-a
     * updatat cantitatea de energie in luna respectiva
     */
    public void setBackProducerChangedEnergy() {
        this.producerChangedEnergy = 0;
    }
}
