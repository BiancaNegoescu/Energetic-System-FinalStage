package fileinput;

import main.MonthlyStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class ProducersInputData extends Observable {

    private long id;
    private String energyType;
    private long maxDistributors;
    private float priceKW;
    private long energyPerDistributor;
    private List<DistributorInputData> distributors;
    private ArrayList<MonthlyStats> monthlyStats;

    public ProducersInputData(final long id, final String energyType, final long maxDistributors,
                              final float priceKW, final long energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.distributors = new ArrayList<>();
        this.monthlyStats = new ArrayList<>();
    }

    public ProducersInputData() {

    }

    public long getId() {
        return id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<DistributorInputData> getDistributors() {
        return distributors;
    }

    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    /**
     * metoda adauga raportul unei luni la lista de "rapoarte" care va fi afisata pentru output
     * @param monthlyStat
     */
    public void setMonthlyStats(final MonthlyStats monthlyStat) {
        this.monthlyStats.add(monthlyStat);
    }

    /**
     * metoda seteaza noua cantitate de energie a unui producator, seteaza producatorul ca
     * modificat si notifica observatorii lui de schimbare
     * @param energyPerDistributor noua cantitate de energie
     * @param producer producatorul updatat
     */
    public void setEnergyPerDistributor(long energyPerDistributor,
                                        final ProducersInputData producer) {
        this.energyPerDistributor = energyPerDistributor;
        producer.setChanged(); //marcheaza producerul ca fiind modificat.
        notifyObservers(producer);
    }

}
