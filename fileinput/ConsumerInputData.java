package fileinput;

public final class ConsumerInputData extends Consumer {

    private boolean isBankrupt;
    private DistributorInputData distribuitor;
    private String entityType;
    private ContractsInputData contract;
    private long debt;
    private boolean debtor;
    private long budget;
    private long id;

    public ConsumerInputData(final long id, final long initialBudget, final long monthlyIncome,
                             final String entityType) {
        super(id, initialBudget, monthlyIncome);
        this.isBankrupt = false;
        this.entityType = entityType;
        this.debt = 0;
        this.debtor = false;
        this.budget = initialBudget;
        this.id = id;
        this.contract = null;
    }

    @Override
    public long getId() {
        return id;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public long getDebt() {
        return debt;
    }

    public void setBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public boolean isDebtor() {
        return debtor;
    }

    public void setDebtor(final boolean debtor) {
        this.debtor = debtor;
    }

    public void setDebt(final long debt) {
        this.debt = debt;
    }

    public DistributorInputData getDistribuitor() {
        return distribuitor;
    }

    public void setDistribuitor(final DistributorInputData distribuitor) {
        this.distribuitor = distribuitor;
    }

    public String getEntityType() {
        return entityType;
    }

    public ContractsInputData getContract() {
        return contract;
    }

    public void setContract(final ContractsInputData contract) {
        this.contract = contract;
    }


}
