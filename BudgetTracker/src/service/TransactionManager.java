package service;

import data.DataManager;
import model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
//import java.io.FileWriter;
//import java.io.IOException;

public class TransactionManager {
    private final List<Transaction> transactions;
    private String currentUser;

    public TransactionManager(String username) {
        this.currentUser = username;
        this.transactions = DataManager.load(username);
    }

    private void autoSave() {
        DataManager.save(transactions, currentUser);
    }

    public void addIncome(LocalDate date, String description, double amount, Category category) {
        transactions.add(new Income(date, description, amount, category));
        autoSave();
    }

    public void addExpense(LocalDate date, String description, double amount, Category category) {
        transactions.add(new Expense(date, description, amount, category));
        autoSave();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t instanceof Income)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t instanceof Expense)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    public List<Transaction> getTransactionsByCategory(Category category) {
        return transactions.stream()
                .filter(t -> t.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByDate(LocalDate date) {
        return transactions.stream()
                .filter(t -> t.getDate().equals(date))
                .collect(Collectors.toList());
    }

    // ✅ Generate a full financial report
    public String getFullFinancialReport() {
        double totalIncome = getTotalIncome();
        double totalExpenses = getTotalExpenses();
        double balance = getBalance();

        // Find the highest expense
        Transaction highestExpense = transactions.stream()
                .filter(t -> t instanceof Expense)
                .max((a, b) -> Double.compare(a.getAmount(), b.getAmount()))
                .orElse(null);

        // Find the highest income
        Transaction highestIncome = transactions.stream()
                .filter(t -> t instanceof Income)
                .max((a, b) -> Double.compare(a.getAmount(), b.getAmount()))
                .orElse(null);

        // Find most used category
        Map<Category, Long> categoryCount = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.counting()));

        Category mostUsedCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return String.format(
                "📊 FULL FINANCIAL REPORT 📊\n" +
                        "Total Income      : %.2f\n" +
                        "Total Expenses    : %.2f\n" +
                        "Net Balance       : %.2f\n" +
                        "Highest Income    : %s\n" +
                        "Highest Expense   : %s\n" +
                        "Most Used Category: %s",
                totalIncome,
                totalExpenses,
                balance,
                (highestIncome != null ? highestIncome.getDescription() + " (" + highestIncome.getAmount() + ")" : "None"),
                (highestExpense != null ? highestExpense.getDescription() + " (" + highestExpense.getAmount() + ")" : "None"),
                (mostUsedCategory != null ? mostUsedCategory : "None")
        );
    }

    // ✅ Get daily income and expense data for a specific month (for charts)
    public Map<LocalDate, Map<String, Double>> getDailyReportForMonth(int year, Month month) {
        Map<LocalDate, Map<String, Double>> dailyReport = new HashMap<>();

        transactions.stream()
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month)
                .forEach(t -> {
                    LocalDate date = t.getDate();
                    dailyReport.putIfAbsent(date, new HashMap<>());
                    Map<String, Double> dailyData = dailyReport.get(date);

                    if (t instanceof Income) {
                        dailyData.put("Income", dailyData.getOrDefault("Income", 0.0) + t.getAmount());
                    } else if (t instanceof Expense) {
                        dailyData.put("Expense", dailyData.getOrDefault("Expense", 0.0) + t.getAmount());
                    }
                });

        return dailyReport;
    }

//    public String getMonthlySummary(int year, Month month) {
//        double income = transactions.stream()
//                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month && t instanceof Income)
//                .mapToDouble(Transaction::getAmount)
//                .sum();
//
//        double expense = transactions.stream()
//                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month && t instanceof Expense)
//                .mapToDouble(Transaction::getAmount)
//                .sum();
//
//        return String.format("Total Income: %.2f\nTotal Expenses: %.2f\nNet Balance: %.2f",
//                income, expense, (income - expense));
//    }
//
//    public double getMonthlyTotalExpenses(int year, Month month) {
//        return transactions.stream()
//                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonth() == month && t instanceof Expense)
//                .mapToDouble(Transaction::getAmount)
//                .sum();
//    }

}
