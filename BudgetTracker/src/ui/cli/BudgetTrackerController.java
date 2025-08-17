package ui.cli;

import model.Category;
import service.TransactionManager;
import service.ReportManager;

import java.time.LocalDate;
import java.util.Scanner;

public class BudgetTrackerController {
    private final TransactionManager transactionManager;
    private final ReportManager reportManager;
    private final Scanner scanner;

    public BudgetTrackerController(TransactionManager transactionManager, ReportManager reportManager, Scanner scanner) {
        this.transactionManager = transactionManager;
        this.reportManager = reportManager;
        this.scanner = scanner;
    }

    public void addIncome() {
        System.out.println("\nAdding Income:");
        double amount = readAmount();
        String description = readText("Enter description: ");
        LocalDate date = readDate();
        Category category = readCategory();

        transactionManager.addIncome(date, description, amount, category);
        System.out.println("Income added successfully.");
    }

    public void addExpense() {
        System.out.println("\nAdding Expense:");
        double amount = readAmount();
        String description = readText("Enter description: ");
        LocalDate date = readDate();
        Category category = readCategory();

        transactionManager.addExpense(date, description, amount, category);
        System.out.println("Expense added successfully.");
    }

    public void viewSummary() {
        System.out.println("\nSummary:");
        System.out.printf("Total Income: %.2f%n", transactionManager.getTotalIncome());
        System.out.printf("Total Expenses: %.2f%n", transactionManager.getTotalExpenses());
        System.out.printf("Balance: %.2f%n", transactionManager.getBalance());
    }

    public void viewAllTransactions() {
        System.out.println("\nAll Transactions:");
        transactionManager.getAllTransactions().forEach(System.out::println);
    }

    public void filterByCategory() {
        Category category = readCategory();
        System.out.println("\nTransactions in Category " + category + ":");
        transactionManager.getTransactionsByCategory(category).forEach(System.out::println);
    }

    public void filterByDate() {
        LocalDate date = readDate();
        System.out.println("\nTransactions on " + date + ":");
        transactionManager.getTransactionsByDate(date).forEach(System.out::println);
    }

    public void viewMonthlySummary() {
        int year = readInt("Enter year (e.g., 2025): ");
        int monthNumber = readInt("Enter month (1-12): ");
        java.time.Month month = java.time.Month.of(monthNumber);

        String report = reportManager.getMonthlySummary(year, month);
        System.out.println("\nMonthly Summary Report for " + month + " " + year + ":");
        System.out.println(report);
    }

    public void viewCategoryAnalysis() {
        System.out.println("\nCategory-wise Analysis:");
        reportManager.getCategoryWiseAnalysis()
                .forEach((category, total) -> System.out.printf("%-15s : %.2f%n", category, total));
    }

    public void exportMonthlyReport() {
        int year = readInt("Enter year (e.g., 2025): ");
        int monthNumber = readInt("Enter month (1-12): ");
        java.time.Month month = java.time.Month.of(monthNumber);

        String report = reportManager.getMonthlySummary(year, month);
        reportManager.exportReportToFile(report, "MonthlyReport_" + month + "_" + year + ".txt");
        System.out.println("Monthly report exported successfully.");
    }

    public void exportCategoryReport() {
        StringBuilder report = new StringBuilder("Category-wise Analysis:\n");
        reportManager.getCategoryWiseAnalysis()
                .forEach((category, total) -> report.append(String.format("%-15s : %.2f%n", category, total)));

        reportManager.exportReportToFile(report.toString(), "CategoryAnalysisReport.txt");
        System.out.println("Category report exported successfully.");
    }

    // ================== HELPER METHODS ====================
    private double readAmount() {
        while (true) {
            try {
                System.out.print("Enter amount: ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private Category readCategory() {
        System.out.println("Choose a category:");
        for (Category c : Category.values()) {
            System.out.println("- " + c);
        }
        while (true) {
            try {
                System.out.print("Category: ");
                return Category.valueOf(scanner.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid category. Try again.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

}
