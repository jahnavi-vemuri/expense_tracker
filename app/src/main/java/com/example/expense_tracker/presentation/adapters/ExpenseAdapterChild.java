package com.example.expense_tracker.presentation.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.presentation.viewmodel.ExpenseViewModel;
import com.example.expense_tracker.presentation.ExpenseItemBottomSheet;
import com.example.expense_tracker.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ExpenseAdapterChild extends RecyclerView.Adapter<ExpenseAdapterChild.ExpenseViewHolder> {
    private List<ExpenseEntity> expenseList;
    private ExpenseAdapterChild.OnDeleteClickListener onDeleteClickListener;
    private ExpenseAdapterChild.OnEditClickListener onEditClickListener;
    private ExpenseViewModel expenseViewModel;
    private Context context;
    private FragmentManager fragmentManager;

    public interface OnDeleteClickListener {
        void onDeleteClick(ExpenseEntity expenseEntity);
    }

    public interface OnEditClickListener {
        void onEditClick(ExpenseEntity expenseEntity, int position);
    }

    public ExpenseAdapterChild(List<ExpenseEntity> expenseList, ExpenseViewModel expenseViewModel, Context context, FragmentManager fragmentManager, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        this.expenseList = expenseList;
        this.expenseViewModel = expenseViewModel;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.onDeleteClickListener = onDeleteClickListener != null ? onDeleteClickListener : defaultDeleteClickListener;
        this.onEditClickListener = onEditClickListener != null ? onEditClickListener : defaultEditClickListener;
    }
    private OnDeleteClickListener defaultDeleteClickListener = expenseEntity -> {};
    private OnEditClickListener defaultEditClickListener = (expenseEntity, position) -> {};
    @NonNull
    @Override
    public ExpenseAdapterChild.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseAdapterChild.ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapterChild.ExpenseViewHolder holder, int position) {
        AtomicReference<ExpenseEntity> currentItem = new AtomicReference<>(expenseList.get(position));
        holder.titleTextView.setText(currentItem.get().getTitle());
        holder.categoryTextView.setText(currentItem.get().getCategory());
        String formattedAmount = context.getString(R.string.rupee_symbol) + currentItem.get().getAmountSpent();
        holder.amountTextView.setText(formattedAmount);

        holder.deleteButton.setOnClickListener(v -> {
            // Handle delete button click
            ExpenseEntity current = expenseList.get(position);
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(current);
            }
            notifyDataSetChanged();
        });

        holder.editButton.setOnClickListener(v -> {
            Log.i("edit", onEditClickListener.toString());
            Log.i("edit","clicked");
            // Handle edit button click
            if (onEditClickListener != null) {
                onEditClickListener.onEditClick(currentItem.get(), position);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            // Show bottom sheet when item is clicked
            ExpenseEntity selectedExpense = expenseList.get(position);
            ExpenseItemBottomSheet bottomSheetFragment = ExpenseItemBottomSheet.newInstance(selectedExpense, onEditClickListener, position);
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return expenseList != null ? expenseList.size() : 0;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView categoryTextView;
        public TextView amountTextView;
        public ImageView editButton;
        public ImageView deleteButton;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

