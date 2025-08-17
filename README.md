
---

# 📊 Budget Tracker

A **console-based Java application** designed to help users **manage their personal finances** by tracking income, expenses, and budgets. This project demonstrates **core Java concepts, software design principles, and practical problem-solving**, built as part of the **FlexiSAF Java Internship Final Project**.

---

## 🚀 Features

✅ **User Management**

* Secure registration & login system
* Persistent user data storage

✅ **Transaction Tracking**

* Record income & expenses with categories
* View all transactions at any time

✅ **Budget Management**

* Set monthly budget goals
* Alerts when spending exceeds limits

✅ **Reports & Analytics**

* Monthly summary (income vs expenses)
* Category-wise expense breakdown
* Total monthly income & expenses

✅ **Data Persistence**

* Saves data locally (files/serialization)
* Ensures financial records are not lost

✅ **Simple & Offline**

* Runs on console, no internet required
* Lightweight and beginner-friendly

---

## 🎯 Pain Points Addressed

The **Budget Tracker** directly solves the following challenges faced in personal finance:

1. **Unawareness of Spending Patterns** → Clear categorization & reporting
2. **Difficulty Sticking to Budgets** → Budget goals with alerts
3. **Error-Prone Manual Tracking** → Automated, structured recording
4. **No Financial Overview** → Quick monthly summaries
5. **Risk of Data Loss** → Persistent local storage
6. **Overly Complex Apps** → Lightweight, offline & user-friendly

---

## 🛠️ Tech Stack

* **Language:** Java (JDK 17+)
* **Paradigm:** Object-Oriented Programming
* **Architecture:** Layered (Model → Service → Controller → UI)
* **Persistence:** File-based (Serializable storage)

---

## 📂 Project Structure

```
BudgetTracker/
│── main/
│   └── Main.java              # Entry point of the application
│
│── model/
│   ├── User.java              # User entity (auth)
│   ├── Transaction.java       # Expense/Income entity
│   └── Budget.java            # Budget entity
│
│── service/
│   ├── UserManager.java       # Handles user registration & login
│   ├── TransactionManager.java# CRUD for transactions
│   ├── ReportManager.java     # Monthly reports & summaries
│   └── DataPersistence.java   # File I/O operations
│
│── controller/
│   └── BudgetTrackerController.java  # Coordinates services with UI
│
│── ui/
│   └── cli/
│       └── BudgetTrackerUI.java      # Console user interface
```

---

## 📖 How It Works

1. **User registers or logs in**
2. **Dashboard options appear:**

    * Add Income / Expense
    * View Transactions
    * Set Budget
    * Generate Monthly Report
    * Exit
3. **All data is saved automatically** for next session

---

## 📸 Sample Console Flow

```
Welcome to Budget Tracker!
1. Register
2. Login
Choose option: 1

Enter username: bilyaminu
Enter password: *****
Registration successful ✅

--- Dashboard ---
1. Add Income
2. Add Expense
3. View Transactions
4. Set Budget
5. Monthly Report
6. Exit
Choose option: 1
Enter amount: 50000
Enter category: Salary
Income added successfully 🎉
```

---

## 🔥 Why This Project Stands Out

* Built with **clean, layered architecture** (professional software design)
* Showcases **core Java OOP principles** (Encapsulation, Abstraction, Interfaces, etc.)
* Includes **data persistence** (real-world feature, not just in-memory)
* Demonstrates **problem-solving** with budgeting, tracking, and reporting
* Written with **future extensibility in mind** (can add DB, GUI, REST API later)

---

## 📌 Future Enhancements

* ✅ **Database Integration (PostgreSQL/MySQL)** for scalable storage
* ✅ **GUI with JavaFX or Swing** for a modern look
* ✅ **Export Reports to PDF/Excel** for analysis
* ✅ **Cloud Sync / Multi-user Support**

---

## 👨‍💻 Author

**Bilyaminu Ahmad**
📍 Federal University of Kashere, Gombe
🎓 FlexiSAF Java Internship – Final Project

---

## 🏆 Internship Relevance

This project demonstrates the knowledge gained during the internship:

* ✅ Core Java fundamentals
* ✅ OOP design & packaging
* ✅ File handling & serialization
* ✅ Layered project structure
* ✅ Problem-solving with real-world use case

---

⚡ In short: **The Budget Tracker is more than just a project – it’s a practical, extensible financial tool that showcases my ability to design, develop, and structure professional Java applications.**

---