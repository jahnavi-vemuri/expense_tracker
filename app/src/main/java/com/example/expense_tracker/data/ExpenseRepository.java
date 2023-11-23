package com.example.expense_tracker.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.expense_tracker.data.ExpenseDao;
import com.example.expense_tracker.data.ExpenseDatabase;
import com.example.expense_tracker.domain.ExpenseEntity;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<List<ExpenseEntity>> allExpenses;

    public ExpenseRepository(Application application) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
    }

    public LiveData<List<ExpenseEntity>> getAllExpenses() {
        return allExpenses;
    }

    public void insertExpense(ExpenseEntity expense) {
        new InsertAsyncTask(expenseDao).execute(expense);
    }

    public void updateExpense(ExpenseEntity expense) {
        new UpdateAsyncTask(expenseDao).execute(expense);
    }

    public void deleteExpense(ExpenseEntity expense) {
        new DeleteAsyncTask(expenseDao).execute(expense);
    }

    public void deleteAllExpenses() {
        new DeleteAllAsyncTask(expenseDao).execute();
    }

    public LiveData<ExpenseEntity> getExpenseById(long expenseId) {
        return expenseDao.getExpenseById(expenseId);
    }

    private static class InsertAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private final ExpenseDao expenseDao;

        private InsertAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {
            expenseDao.insertExpense(expenses[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private ExpenseDao expenseDao;

        private DeleteAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {
            expenseDao.deleteExpense(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenseDao expenseDao;

        private DeleteAllAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.deleteAllExpenses();
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ExpenseEntity, Void, Void> {
        private final ExpenseDao expenseDao;

        private UpdateAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(ExpenseEntity... expenses) {

            expenseDao.updateExpense(expenses[0]);
            return null;
        }
    }
}
