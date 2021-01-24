package fileinput;

public class Consumer {

    private long id;
    private long initialBudget;
    private long monthlyIncome;

    public Consumer(final long id, final long initialBudget,
                    final long monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * getter pentru Id al unui consumator
     * @return id-ul
     */
    public long getId() {
        return id;
    }

    /**
     * getter pentru initialBudget al unui consumator
     * @return initialBudget
     */
    public long getInitialBudget() {
        return initialBudget;
    }

    /**
     * getter pentru monthlyIncome al unui consumator
     * @return monthlyIncome
     */
    public long getMonthlyIncome() {
        return monthlyIncome;
    }


}
