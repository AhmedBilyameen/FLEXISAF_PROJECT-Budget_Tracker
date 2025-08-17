package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    protected LocalDate date;
    protected String description;
    protected double amount;
    protected Category category;

    public Transaction(LocalDate date, String description, double amount, Category category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f (%s) on %s",
                getType(), description, amount, category, date);
    }
}
