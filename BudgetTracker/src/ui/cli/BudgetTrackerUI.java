package ui.cli;

import model.UserProfile;
import service.TransactionManager;
import service.UserManager;
import service.ReportManager;

import java.util.Scanner;

public class BudgetTrackerUI {
    private TransactionManager transactionManager;
    private ReportManager reportManager;
    private BudgetTrackerController controller;
    private final UserManager userManager;
    private final Scanner scanner;
    private String currentUser;

    public BudgetTrackerUI() {
        userManager = new UserManager();
        scanner = new Scanner(System.in);
    }

    // START MENU (Login/Register)
    public void start() {
        System.out.println("Welcome to Budget Tracker!");

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\n===== LOGIN MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleRegister();
                case "2" -> loggedIn = handleLogin();
                case "3" -> {
                    System.out.println("Exiting Budget Tracker. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        // Initialize managers and controller
        transactionManager = new TransactionManager(currentUser);
        reportManager = new ReportManager(transactionManager.getAllTransactions());
        controller = new BudgetTrackerController(transactionManager, reportManager, scanner);

        // Load main tracker only after successful login
        showMainMenu();
    }

    private void handleRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userManager.register(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists!");
        }
    }

    private boolean handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            System.out.println("Login successful! Welcome, " + username);
            currentUser = username; // Store logged-in user
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // MAIN MENU
    private void showMainMenu() {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> controller.addIncome();
                case "2" -> controller.addExpense();
                case "3" -> controller.viewSummary();
                case "4" -> controller.viewAllTransactions();
                case "5" -> controller.filterByCategory();
                case "6" -> controller.filterByDate();
                case "7" -> controller.viewMonthlySummary();
                case "8" -> controller.viewCategoryAnalysis();
                case "9" -> controller.exportMonthlyReport();
                case "10" -> controller.exportCategoryReport();
                case "11" -> viewFullReport();
                case "12" -> viewDailyReport();
                case "13" -> manageProfile();
                case "0" -> {
                    System.out.println("Exiting Budget Tracker. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Summary");
        System.out.println("4. View All Transactions");
        System.out.println("5. Filter by Category");
        System.out.println("6. Filter by Date");
        System.out.println("7. Monthly Summary");
        System.out.println("8. Category-Wise Analysis");
        System.out.println("9. Export Monthly Summary to File");
        System.out.println("10. Export Category Analysis to File");
        System.out.println("11. View Full Financial Report");
        System.out.println("12. View Daily Report for Selected Month");
        System.out.println("13. Profile Settings");
        System.out.println("0. Exit");
    }

    // Keep only features that are not in controller

    private void viewFullReport() {
        System.out.println("\nFULL FINANCIAL REPORT:");
        System.out.println(transactionManager.getFullFinancialReport());
    }

    private void viewDailyReport() {
        System.out.print("Enter year (e.g., 2025): ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter month (1-12): ");
        int monthNumber = Integer.parseInt(scanner.nextLine());
        java.time.Month month = java.time.Month.of(monthNumber);

        var report = transactionManager.getDailyReportForMonth(year, month);

        System.out.println("\nDaily Report for " + month + " " + year + ":");
        if (report.isEmpty()) {
            System.out.println("No transactions found for this month.");
            return;
        }

        report.forEach((date, data) -> {
            double income = data.getOrDefault("Income", 0.0);
            double expense = data.getOrDefault("Expense", 0.0);
            System.out.printf("%s -> Income: %.2f | Expense: %.2f | Balance: %.2f%n",
                    date, income, expense, (income - expense));
        });
    }

    private void manageProfile() {
        UserProfile profile = userManager.getUserProfile(currentUser);

        System.out.println("\n===== PROFILE SETTINGS =====");
        System.out.println("1. Change Currency Symbol");
        System.out.println("2. Set Monthly Budget Goal");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter new currency symbol (e.g., $, ₦, €): ");
                profile.setCurrencySymbol(scanner.nextLine());
                System.out.println("Currency updated successfully!");
            }
            case "2" -> {
                System.out.print("Enter monthly budget goal: ");
                try {
                    profile.setMonthlyBudgetGoal(Double.parseDouble(scanner.nextLine()));
                    System.out.println("Monthly budget goal updated successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                }
            }
            default -> System.out.println("Invalid choice.");
        }

        userManager.saveProfiles();
    }
}
