package com.example.expense_tracker.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.data.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<List<ExpenseEntity>> allExpenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public LiveData<List<ExpenseEntity>> getAllExpenses() {
        return allExpenses;
    }

    public void deleteExpense(ExpenseEntity expense) {
        repository.deleteExpense(expense);
    }

    public void deleteAllExpenses() {
        repository.deleteAllExpenses();
    }

    public void updateExpense(ExpenseEntity expense) {
        repository.updateExpense(expense);
    }

    public LiveData<ExpenseEntity> getExpenseById(long expenseId) {
        return repository.getExpenseById(expenseId);
    }
    public void insertExpense(String title, String description, double amountSpent, String category, String currentDate) {
        // Create an instance of ExpenseEntity using the provided parameters
        ExpenseEntity expense = new ExpenseEntity(title, description, amountSpent, category, currentDate);

        // Call the repository to insert the expense into the database
        repository.insertExpense(expense);
    }
}
