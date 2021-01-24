package fileinput;

public final class ProducerChangesInputData {

    private final long id;
    private final long energyPerDistributor;

    public ProducerChangesInputData(final long id, final long energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public long getId() {
        return id;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }
}
