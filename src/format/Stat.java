package format;

import java.util.ArrayList;

public class Stat {
    private int month;
    private ArrayList<Integer> distributorsIds;

    public Stat(int month, ArrayList<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }
    /**
     * get the month
     */
    public int getMonth() {
        return month;
    }
    /**
     * set month
     */
    public void setMonth(int month) {
        this.month = month;
    }
    /**
     * get the distributors
     */
    public ArrayList<Integer> getDistributorsIds() {
        return distributorsIds;
    }
    /**
     * set diustributors
     */
    public void setDistributorsIds(ArrayList<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
