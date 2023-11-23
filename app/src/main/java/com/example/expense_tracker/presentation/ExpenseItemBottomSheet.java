package com.example.expense_tracker.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.expense_tracker.R;
import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.presentation.adapters.ExpenseAdapterChild;
import com.example.expense_tracker.presentation.viewmodel.ExpenseViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExpenseItemBottomSheet extends BottomSheetDialogFragment {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView categoryTextView;
    private TextView amountTextView;
    private TextView dateTextView;
    private Button deleteButton;
    private ImageView editButton;

    private ExpenseAdapterChild.OnEditClickListener onEditClickListener;
    private ExpenseEntity currentItem;
    private int position;

    public static ExpenseItemBottomSheet newInstance(ExpenseEntity expenseEntity, ExpenseAdapterChild.OnEditClickListener onEditClickListener, int position) {
        ExpenseItemBottomSheet fragment = new ExpenseItemBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable("EXPENSE_ENTITY", expenseEntity);
        fragment.setArguments(args);

        // Set additional parameters
        fragment.onEditClickListener = onEditClickListener;
        fragment.currentItem = expenseEntity;
        fragment.position = position;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Notify the MainActivity that the bottom sheet is dismissed
        ((MainActivity) requireActivity()).updateMainScreenBackground(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        amountTextView = view.findViewById(R.id.amountTextView);
        dateTextView = view.findViewById(R.id.dateTextView);
        deleteButton = view.findViewById(R.id.deleteButton);
        editButton = view.findViewById(R.id.editButton);

        // Retrieve the ExpenseEntity from arguments
        ExpenseEntity expenseEntity = (ExpenseEntity) getArguments().getSerializable("EXPENSE_ENTITY");

        if (expenseEntity != null) {
            // Assign values to the respective views
            titleTextView.setText(expenseEntity.getTitle());
            descriptionTextView.setText(expenseEntity.getDescription());
            categoryTextView.setText(expenseEntity.getCategory());
            String formattedAmount = getString(R.string.rupee_symbol) + expenseEntity.getAmountSpent();
            amountTextView.setText(formattedAmount);
            dateTextView.setText(expenseEntity.getExpenseDate());
            deleteButton.setOnClickListener(v -> {
                // Delete the expenseEntity
                ExpenseViewModel expenseViewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);
                expenseViewModel.deleteExpense(expenseEntity);

                // Dismiss the bottom sheet after deletion
                dismiss();
            });
            editButton.setOnClickListener(v -> {
                if (onEditClickListener != null) {
                    Log.i("edit", onEditClickListener.toString());
                    Log.i("edit", "clicked");
                    // Handle edit button click
                    onEditClickListener.onEditClick(currentItem, position);
                }
            });
            ((MainActivity) requireActivity()).updateMainScreenBackground(true);
        }
    }
}


