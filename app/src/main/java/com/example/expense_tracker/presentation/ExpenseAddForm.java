package com.example.expense_tracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.presentation.viewmodel.ExpenseViewModel;
import com.example.expense_tracker.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ExpenseAddForm extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    private boolean editMode = false;
    private int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expenses);
        ImageView navigationButton = findViewById(R.id.navigationButton);
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUpToFromChild(ExpenseAddForm.this, new Intent(ExpenseAddForm.this, MainActivity.class));
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);

        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextAmountSpent = findViewById(R.id.editTextAmountSpent);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        String[] categories = {"", "Gadgets", "Food", "Groceries", "Entertainment", "Transportation", "Rent & EB", "Others"};

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ExpenseFormValidation.setupFormValidation(editTextTitle, editTextDescription, editTextAmountSpent, spinnerCategory, addButton);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("EDIT_MODE", false);
        itemPosition = intent.getIntExtra("ITEM_POSITION", -1);

        if (editMode) {
            Log.i("EditMode", "Edit mode: " + editMode);
            Log.i("EditMode", "Item position: " + itemPosition);

            String editTitle = intent.getStringExtra("EDIT_TITLE");
            String editDescription = intent.getStringExtra("EDIT_DESCRIPTION");
            String editAmount = intent.getStringExtra("EDIT_AMOUNT");
            Log.i("EditMode", "Edit amount: " + editAmount);
            String editCategory = intent.getStringExtra("EDIT_CATEGORY");

            editTextTitle.setText(editTitle);
            editTextDescription.setText(editDescription);
            editTextAmountSpent.setText(String.valueOf(editAmount));

            int categoryPosition = Arrays.asList(categories).indexOf(editCategory);
            spinnerCategory.setSelection(categoryPosition >= 0 ? categoryPosition : 0);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                String amountSpentString = editTextAmountSpent.getText().toString();
                double amountSpent = amountSpentString.isEmpty() ? 0.0 : Double.parseDouble(amountSpentString);
                String category = spinnerCategory.getSelectedItem().toString();
                String currentDate = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }

                if (editMode) {
                    // If in edit mode, observe the existing item
                    Log.i("EditMode", "Clicked in edit mode");
                    long expenseId = intent.getLongExtra("EXPENSE_ID", -1);
                    Log.i("expenseee", String.valueOf(expenseId));
                    String finalCurrentDate = currentDate;
                    expenseViewModel.getExpenseById(expenseId).observe(ExpenseAddForm.this, new Observer<ExpenseEntity>() {
                        @Override
                        public void onChanged(ExpenseEntity existingExpense) {
                            // Handle the updated data
                            Log.i("expenseee", String.valueOf(existingExpense));

                            if (existingExpense != null) {
                                Log.i("EditMode", "Existing expense found: " + existingExpense.getTitle());

                                existingExpense.setTitle(title);
                                existingExpense.setDescription(description);
                                existingExpense.setAmountSpent(amountSpent);
                                existingExpense.setCategory(category);
                                existingExpense.setExpenseDate(finalCurrentDate);

                                Log.i("EditMode", "Updating expense: " + existingExpense.getTitle());
                                expenseViewModel.updateExpense(existingExpense);

                                Log.i("EditMode", "Expense updated");
                            } else {
                                Log.e("EditMode", "Existing expense is null");
                            }
                        }
                    });
                } else {
                    // If not in edit mode, insert a new item
                    expenseViewModel.insertExpense(title, description, amountSpent, category, currentDate);
                    Log.i("EditMode", "Clicked in insert mode");
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("ITEM_POSITION", itemPosition);
                resultIntent.putExtra("TITLE", title);
                resultIntent.putExtra("DESCRIPTION", description);
                resultIntent.putExtra("AMOUNT_SPENT", amountSpent);
                resultIntent.putExtra("CATEGORY", category);
                resultIntent.putExtra("DATE", currentDate);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
