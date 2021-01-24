package fileoutput;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "isBankrupt", "budget"})
public final class ConsumerOutputData {
    private long id;
    private boolean isBankrupt;
    private long budget;

    public ConsumerOutputData(final long id, final boolean isBankrupt, final long budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    /**
     * getter pentru a vedea daca consumatorul este bankrupt sau nu
     * @return true, daca e bankrupt, false, contrar
     */
    public boolean getisBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

}
