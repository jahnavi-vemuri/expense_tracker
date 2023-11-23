package com.example.expense_tracker.data;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expense_tracker.domain.ExpenseEntity;

@Database(entities = {ExpenseEntity.class}, version = 1, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static volatile ExpenseDatabase INSTANCE;

    public abstract ExpenseDao expenseDao();

    public static ExpenseDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ExpenseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application.getApplicationContext(),
                                    ExpenseDatabase.class, "expense_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static ExpenseDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ExpenseDatabase.class, "expense_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
