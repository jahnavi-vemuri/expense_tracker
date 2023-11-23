package com.example.expense_tracker.presentation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ExpenseFormValidation {
    public static void setupFormValidation(
            EditText editTextTitle,
            EditText editTextDescription,
            EditText editTextAmountSpent,
            Spinner spinnerCategory,
            Button addButton) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed, but you must override it
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed, but you must override it
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if all required fields are filled out
                boolean allFieldsFilled = !editTextTitle.getText().toString().isEmpty()
                        && !editTextDescription.getText().toString().isEmpty()
                        && !editTextAmountSpent.getText().toString().isEmpty()
                        && spinnerCategory.getSelectedItemPosition() > 0; // 0 means nothing is selected

                // Enable or disable the "Add" button based on the condition
                addButton.setEnabled(allFieldsFilled);
            }
        };

        editTextTitle.addTextChangedListener(textWatcher);
        editTextDescription.addTextChangedListener(textWatcher);
        editTextAmountSpent.addTextChangedListener(textWatcher);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // When a category is selected, call the textWatcher to check if the "Add" button should be enabled
                textWatcher.afterTextChanged(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not needed, but you must override it
            }
        });
    }
}
