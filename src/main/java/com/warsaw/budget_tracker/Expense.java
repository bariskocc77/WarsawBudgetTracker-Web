package com.warsaw.budget_tracker;

import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private double tryAmount;
    private String category;
    private LocalDate date;


    public Expense(String description, double amount, double tryAmount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.tryAmount = tryAmount;
        this.category = category;
        this.date = date;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public double getTryAmount() { return tryAmount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
}