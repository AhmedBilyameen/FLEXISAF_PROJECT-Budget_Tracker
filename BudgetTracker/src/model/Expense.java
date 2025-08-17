package model;

import java.io.Serial;
import java.time.LocalDate;

public class Expense extends Transaction {
    @Serial
    private static final long serialVersionUID = 1L;

    public Expense(LocalDate date, String description, double amount, Category category) {
        super(date, description, amount, category);
    }

    @Override
    public String getType() {
        return "Expense";
    }
}
