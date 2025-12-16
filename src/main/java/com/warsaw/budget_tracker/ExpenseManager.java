package com.warsaw.budget_tracker;

import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ExpenseManager {
    private List<Expense> expenseList;
    private static final String FILE_NAME = "expenses.csv";

    public ExpenseManager() {
        this.expenseList = new ArrayList<>();
        loadFromFile();
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
        saveToFile(expense);
    }

    public List<Expense> getAllExpenses() {
        return this.expenseList;
    }


    public double getTotalPln() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        return total;
    }


    public double getTotalTry() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getTryAmount();
        }
        return total;
    }

    private void saveToFile(Expense expense) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter out = new PrintWriter(fw)) {
            // Format: Date,Desc,PLN,Cat,TRY
            out.println(expense.getDate() + "," + expense.getDescription() + "," +
                    expense.getAmount() + "," + expense.getCategory() + "," + expense.getTryAmount());
        } catch (IOException e) {
            System.out.println("❌ Kayıt hatası: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    this.expenseList.add(new Expense(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[4]), // TRY sütunu
                            parts[3],
                            LocalDate.parse(parts[0])
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Okuma hatası: " + e.getMessage());
        }
    }
}