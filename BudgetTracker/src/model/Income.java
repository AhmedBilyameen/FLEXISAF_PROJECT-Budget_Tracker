package model;

import java.io.Serial;
import java.time.LocalDate;

public class Income extends Transaction {
    @Serial
    private static final long serialVersionUID = 1L;

    public Income(LocalDate date, String description, double amount, Category category) {
        super(date, description, amount, category);
    }

    @Override
    public String getType() {
        return "Income";
    }
}
