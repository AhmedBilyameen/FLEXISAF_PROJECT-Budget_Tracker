package model;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String currencySymbol = "$";  // default
    private double monthlyBudgetGoal = 0.0; // default 0 = no goal set

    public UserProfile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getMonthlyBudgetGoal() {
        return monthlyBudgetGoal;
    }

    public void setMonthlyBudgetGoal(double monthlyBudgetGoal) {
        this.monthlyBudgetGoal = monthlyBudgetGoal;
    }
}
