package fileoutput;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({"consumers", "distributors", "energyProducers"})
public final class OutputFormat {
    private ArrayList<ConsumerOutputData> consumers;
    private ArrayList<DistributorOutputData> distributors;
    private ArrayList<ProducerOutputData> energyProducers;

    public OutputFormat() {
        this.consumers = new ArrayList<>();
        this.distributors = new ArrayList<>();
        this.energyProducers = new ArrayList<>();
    }

    public ArrayList<ConsumerOutputData> getConsumers() {
        return consumers;
    }

    /**
     * adauga la lista de consumatori cate un consumator
     * @param consumer un consumator
     */
    public void setConsumers(final ConsumerOutputData consumer) {
        this.consumers.add(consumer);
    }

    public ArrayList<DistributorOutputData> getDistributors() {
        return distributors;
    }

    /**
     * adauga la lista de distribuitori cate un distribuitor
     * @param distributor un distribuitor
     */
    public void setDistributors(final DistributorOutputData distributor) {
        this.distributors.add(distributor);
    }

    /**
     * metoda returneaza lista finala cu toti producatorii si datele care trebuie afisate
     * @return lista cu toti producatorii
     */
    public ArrayList<ProducerOutputData> getenergyProducers() {
        return energyProducers;
    }

    /**
     * metoda adauga un producer la lista finala de produceri
     * @param producer un producer cu datele finale pentru output file
     */
    public void setProducers(final ProducerOutputData producer) {
        this.energyProducers.add(producer);
    }
}
