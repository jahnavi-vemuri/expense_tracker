package com.example.expense_tracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expense_tracker.domain.ExpenseEntity;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    long insertExpense(ExpenseEntity expense);

    @Update
    void updateExpense(ExpenseEntity expense);

    @Query("SELECT * FROM expenses")
    LiveData<List<ExpenseEntity>> getAllExpenses();

    @Query("SELECT * FROM expenses")
    List<ExpenseEntity> getAllExpensesSync();

    @Delete
    void deleteExpense(ExpenseEntity expense);

    @Query("DELETE FROM expenses")
    void deleteAllExpenses();

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    LiveData<ExpenseEntity> getExpenseById(long expenseId);
}
