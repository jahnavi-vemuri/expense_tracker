package com.example.expense_tracker.domain;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "expenses")
public class ExpenseEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;
    public double amountSpent;
    public String category;
    public String expenseDate;

    public ExpenseEntity(String title, String description, double amountSpent, String category, String currentDate) {
        this.title = title;
        this.description = description;
        this.amountSpent = amountSpent;
        this.category = category;
        this.expenseDate = currentDate;

        logValues();
    }

    public ExpenseEntity() {
    }

    private void logValues() {
        Log.i("ExpenseEntity", "Stored Date Value: " + expenseDate);
        Log.i("ExpenseEntity", "Stored Date Value: " + title);
        Log.i("ExpenseEntity", "Stored Date Value: " + amountSpent);
        Log.i("ExpenseEntity", "Stored Date Value: " + category);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAmountSpent() {
        return String.valueOf((long) amountSpent);
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpenseDate(String currentDate) {
        this.expenseDate = currentDate;
    }
}
