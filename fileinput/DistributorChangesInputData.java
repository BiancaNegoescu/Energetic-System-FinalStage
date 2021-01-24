package fileinput;

public final class DistributorChangesInputData {
    private long id;
    private long infrastructureCost;

    public DistributorChangesInputData(final long id, final long infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
}
