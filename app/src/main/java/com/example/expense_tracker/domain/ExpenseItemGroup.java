package com.example.expense_tracker.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpenseItemGroup {
    public String date;
    private List<ExpenseEntity> items;

    public ExpenseItemGroup(String date, List<ExpenseEntity> items) {
        this.date = date;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public List<ExpenseEntity> getItems() {
        return items;
    }

    public static List<ExpenseItemGroup> groupItems(List<ExpenseEntity> expenses) {
        if (expenses == null) {
            return new ArrayList<>(); // Return an empty list or handle it based on your logic
        }

        // Sort the expenses by date in descending order (recent first)
        Collections.sort(expenses, new Comparator<ExpenseEntity>() {
            @Override
            public int compare(ExpenseEntity expense1, ExpenseEntity expense2) {
                // Compare in reverse order for descending order
                return expense2.expenseDate.compareTo(expense1.expenseDate);
            }
        });

        List<ExpenseItemGroup> itemGroups = new ArrayList<>();

        for (ExpenseEntity expense : expenses) {
            boolean added = false;

            for (ExpenseItemGroup itemGroup : itemGroups) {
                // Use the correct field to get the expense date from ExpenseEntity
                if (itemGroup.getDate().equals(expense.expenseDate)) {
                    itemGroup.getItems().add(expense);
                    added = true;

                    // Log the expense date and other details
                    logExpenseDetails(expense);

                    break;
                }
            }

            if (!added) {
                List<ExpenseEntity> itemList = new ArrayList<>();
                itemList.add(expense);

                // Log the expense date and other details
                logExpenseDetails(expense);

                // Use the correct field to get the expense date from ExpenseEntity
                itemGroups.add(new ExpenseItemGroup(expense.expenseDate, itemList));
            }
        }

        return itemGroups;
    }

    private static void logExpenseDetails(ExpenseEntity expense) {
        Log.i("ET_ItemGroup", "Expense Date: " + expense.expenseDate);
        Log.i("ET_ItemGroup", "Title: " + expense.title);
        Log.i("ET_ItemGroup", "Amount Spent: " + expense.amountSpent);
        Log.i("ET_ItemGroup", "Category: " + expense.category);
    }

}


