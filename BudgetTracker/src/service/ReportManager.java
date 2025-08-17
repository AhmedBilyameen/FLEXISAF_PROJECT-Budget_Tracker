package service;

import model.Category;
import model.Expense;
import model.Income;
import model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ReportManager {

    private final List<Transaction> transactions;

    public ReportManager(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public String getMonthlySummary(int year, Month month) {
        double income = transactions.stream()
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month && t instanceof Income)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month && t instanceof Expense)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return String.format("%s %d Summary:\nTotal Income: %.2f\nTotal Expenses: %.2f\nNet Balance: %.2f",
                month, year, income, expense, income - expense);
    }

    public Map<Category, Double> getCategoryWiseAnalysis() {
        Map<Category, Double> categoryMap = new HashMap<>();
        for (Transaction t : transactions) {
            categoryMap.put(t.getCategory(),
                    categoryMap.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        return categoryMap;
    }

    public void exportReportToFile(String reportContent, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(reportContent);
            System.out.println("Report successfully exported to " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to export report: " + e.getMessage());
        }
    }
}
