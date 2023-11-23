package com.example.expense_tracker.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.expense_tracker.domain.ExpenseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExpenseDao_Impl implements ExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ExpenseEntity> __insertionAdapterOfExpenseEntity;

  private final EntityDeletionOrUpdateAdapter<ExpenseEntity> __deletionAdapterOfExpenseEntity;

  private final EntityDeletionOrUpdateAdapter<ExpenseEntity> __updateAdapterOfExpenseEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllExpenses;

  public ExpenseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpenseEntity = new EntityInsertionAdapter<ExpenseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `expenses` (`id`,`title`,`description`,`amountSpent`,`category`,`expenseDate`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ExpenseEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.description);
        }
        statement.bindDouble(4, entity.amountSpent);
        if (entity.category == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.category);
        }
        if (entity.expenseDate == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.expenseDate);
        }
      }
    };
    this.__deletionAdapterOfExpenseEntity = new EntityDeletionOrUpdateAdapter<ExpenseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `expenses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ExpenseEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfExpenseEntity = new EntityDeletionOrUpdateAdapter<ExpenseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `expenses` SET `id` = ?,`title` = ?,`description` = ?,`amountSpent` = ?,`category` = ?,`expenseDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ExpenseEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.title);
        }
        if (entity.description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.description);
        }
        statement.bindDouble(4, entity.amountSpent);
        if (entity.category == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.category);
        }
        if (entity.expenseDate == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.expenseDate);
        }
        statement.bindLong(7, entity.id);
      }
    };
    this.__preparedStmtOfDeleteAllExpenses = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM expenses";
        return _query;
      }
    };
  }

  @Override
  public long insertExpense(final ExpenseEntity expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfExpenseEntity.insertAndReturnId(expense);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteExpense(final ExpenseEntity expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExpenseEntity.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateExpense(final ExpenseEntity expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExpenseEntity.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllExpenses() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllExpenses.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllExpenses.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ExpenseEntity>> getAllExpenses() {
    final String _sql = "SELECT * FROM expenses";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"expenses"}, false, new Callable<List<ExpenseEntity>>() {
      @Override
      @Nullable
      public List<ExpenseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAmountSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "amountSpent");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expenseDate");
          final List<ExpenseEntity> _result = new ArrayList<ExpenseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ExpenseEntity _item;
            _item = new ExpenseEntity();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _item.title = null;
            } else {
              _item.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _item.description = null;
            } else {
              _item.description = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.amountSpent = _cursor.getDouble(_cursorIndexOfAmountSpent);
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _item.category = null;
            } else {
              _item.category = _cursor.getString(_cursorIndexOfCategory);
            }
            if (_cursor.isNull(_cursorIndexOfExpenseDate)) {
              _item.expenseDate = null;
            } else {
              _item.expenseDate = _cursor.getString(_cursorIndexOfExpenseDate);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<ExpenseEntity> getAllExpensesSync() {
    final String _sql = "SELECT * FROM expenses";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfAmountSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "amountSpent");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expenseDate");
      final List<ExpenseEntity> _result = new ArrayList<ExpenseEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ExpenseEntity _item;
        _item = new ExpenseEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _item.title = null;
        } else {
          _item.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _item.description = null;
        } else {
          _item.description = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.amountSpent = _cursor.getDouble(_cursorIndexOfAmountSpent);
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _item.category = null;
        } else {
          _item.category = _cursor.getString(_cursorIndexOfCategory);
        }
        if (_cursor.isNull(_cursorIndexOfExpenseDate)) {
          _item.expenseDate = null;
        } else {
          _item.expenseDate = _cursor.getString(_cursorIndexOfExpenseDate);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<ExpenseEntity> getExpenseById(final long expenseId) {
    final String _sql = "SELECT * FROM expenses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, expenseId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"expenses"}, false, new Callable<ExpenseEntity>() {
      @Override
      @Nullable
      public ExpenseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAmountSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "amountSpent");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfExpenseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expenseDate");
          final ExpenseEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new ExpenseEntity();
            _result.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _result.title = null;
            } else {
              _result.title = _cursor.getString(_cursorIndexOfTitle);
            }
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _result.description = null;
            } else {
              _result.description = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.amountSpent = _cursor.getDouble(_cursorIndexOfAmountSpent);
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _result.category = null;
            } else {
              _result.category = _cursor.getString(_cursorIndexOfCategory);
            }
            if (_cursor.isNull(_cursorIndexOfExpenseDate)) {
              _result.expenseDate = null;
            } else {
              _result.expenseDate = _cursor.getString(_cursorIndexOfExpenseDate);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
