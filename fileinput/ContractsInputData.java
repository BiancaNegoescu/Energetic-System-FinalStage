package fileinput;

public final class ContractsInputData {

    private static ContractsInputData singleContractsInstance = null;

    private final long consumerId;
    private final long price;
    private long remainedContractMonths;


    public ContractsInputData(final long consumerId, final long price,
                              final long remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public long getPrice() {
        return price;
    }

    public long getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final long remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }


}
