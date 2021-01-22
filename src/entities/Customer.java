package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Customer {
    private static final float RATIO = 1.2f;
    private int id;
    private float budget;
    private float monthlyIncome;
    private float debt = 0;
    private int debtId = -1;
    private int contractLength = 0;
    private int distributorId = -1;
    private float payment = 0;
    private boolean faliment = false;
    private int bkrTime = 0;

    public Customer(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.budget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }
    /**
     * sets the id
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * sets the budget
     */
    public void setBudget(final float budget) {
        this.budget = budget;
    }
    /**
     * sets the monthly income
     */
    public void setMonthlyIncome(final float monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
    /**
     * sets the contract length
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }
    /**
     * returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * returns the budget
     */
    public float getBudget() {
        return budget;
    }
    /**
     * returns the monthly income
     */
    public float getMonthlyIncome() {
        return monthlyIncome;
    }
    /**
     * returns the contract length
     */
    public int getContractLength() {
        return contractLength;
    }
    /**
     * returns the distributor id
     */
    public int getDistributorId() {
        return distributorId;
    }
    /**
     * returns the payment
     */
    public float getPayment() {
        return payment;
    }
    /**
     * returns the time since faliment
     */
    public int getBkrTime() {
        return bkrTime;
    }
    /**
     * checks for faliment
     */
    public boolean isFaliment() {
        return faliment;
    }
    /**
     * colects monthly pay
     */
    public void colect() {
        if (faliment) {
            return;
        }
        this.budget += monthlyIncome;
    }
    /**
     * finds a new contract among the still non bankrupt distr
     */
    public void findContract(final ArrayList<Distributor> distributors) {
        if (faliment) {
            return;
        }
        payment = Integer.MAX_VALUE;
        for (Distributor distributor : distributors) {
            if (distributor.getCurrentPrice() < payment && !distributor.isFaliment()) {
                payment = distributor.getCurrentPrice();
                contractLength = distributor.getContractLength();
                distributorId = distributor.getId();
            }
        }
    }
    /**
     * pays the current distributor
     */
    public void pay(final ArrayList<Distributor> distributors) {
        if (faliment) {
            bkrTime++;
            return;
        }
        if (debt != 0) {
            if (debt != 0 && distributors.get(debtId).isFaliment()) {
                debt = 0;
                debtId = -1;
            }
            if (contractLength == 0 || (distributorId != -1
                    && distributors.get(distributorId).isFaliment())) {
                payment = 0;
                distributorId = -1;
                findContract(distributors);
                if (distributorId == -1) {
                    contractLength = 0;
                    return;
                }
                if (payment + debt > budget) {
                    faliment = true;
                    bkrTime++;
                    return;
                } else {
                    distributors.get(distributorId).gainConsumer(id, this);
                    distributors.get(distributorId).colect(payment);
                    budget -= payment;
                    contractLength--;
                    if (debtId != -1) {
                        distributors.get(debtId).colect(debt);
                        budget -= debt;
                        debt = 0;
                        debtId = -1;
                    }
                    return;
                }
            } else {
                if (payment + debt > budget) {
                    faliment = true;
                    bkrTime++;
                    contractLength--;
                    return;
                } else {
                    distributors.get(distributorId).colect(payment);
                    budget -= payment;
                    contractLength--;
                    if (debtId != -1) {
                        distributors.get(debtId).colect(debt);
                        budget -= debt;
                        debt = 0;
                        debtId = -1;
                    }
                    return;
                }
            }

        }

        if (contractLength == 0 || (distributorId != -1
                && distributors.get(distributorId).isFaliment())) {
            payment = 0;
            distributorId = -1;
            findContract(distributors);
            if (distributorId == -1) {
                contractLength = 0;
                return;
            }
            distributors.get(distributorId).gainConsumer(id, this);
            pay(distributors);
        } else {
            if (payment > budget) {
                debtId = distributorId;
                debt = Math.round(Math.floor(payment * RATIO));
                contractLength--;
            } else {
                distributors.get(distributorId).colect(payment);
                budget -= payment;
                contractLength--;
            }
        }
    }
    /**
     * reads the set of customers
     */
    public static void addSet(final ArrayList<Customer> customers, final  JSONArray customersIn) {
        for (Object i : customersIn) {
            JSONObject customer = (JSONObject) i;
            customers.add(CustomerFactory.createCustomer(
                    CustomerFactory.TypesOfCustomers.SIMPLE_CUSTOMER,
                    (int) (long) customer.get("id"),
                    (int) (long) customer.get("initialBudget"),
                    (int) (long) customer.get("monthlyIncome")));

        }
    }
}
