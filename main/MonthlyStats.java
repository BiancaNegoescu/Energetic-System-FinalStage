package main;

import java.util.ArrayList;
import java.util.Collections;

public final class MonthlyStats {
    private int month;
    private ArrayList<Long> distributorsIDs = new ArrayList<Long>();

    public MonthlyStats(final int month, final Long distributorID) {
        this.month = month;
        this.distributorsIDs.add(distributorID);
    }

    public MonthlyStats(final int month) {

        this.month = month;
    }

    /**
     * metoda sorteaza lista de id-uri crescator
     */
    public void sort() {
        Collections.sort(distributorsIDs);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<Long> getDistributorsIds() {
        return distributorsIDs;
    }

    /**
     * metoda adauga un nou id la lista de id-uri
     * @param distributorsIDs un nou id de adaugat la lista de id-uri
     */
    public void setDistributorsIDs(Long distributorsIDs) {
        this.distributorsIDs.add(distributorsIDs);
    }

}
