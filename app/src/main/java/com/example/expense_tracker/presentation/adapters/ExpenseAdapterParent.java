package com.example.expense_tracker.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expense_tracker.domain.ExpenseItemGroup;
import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.presentation.viewmodel.ExpenseViewModel;
import com.example.expense_tracker.R;
import com.example.expense_tracker.presentation.ExpenseAddForm;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapterParent extends RecyclerView.Adapter<ExpenseAdapterParent.ViewHolder> implements ExpenseAdapterChild.OnDeleteClickListener, ExpenseAdapterChild.OnEditClickListener {
    private List<ExpenseItemGroup> groupedExpenseList;
    private List<ExpenseItemGroup> filteredGroupedExpenseList;
    private ExpenseViewModel expenseViewModel;
    private Context context;
    private FragmentManager fragmentManager;

    private List<ExpenseEntity> originalExpenseList;

    public ExpenseAdapterParent(ExpenseViewModel expenseViewModel, Context context, FragmentManager fragmentManager) {
        this.expenseViewModel = expenseViewModel;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.groupedExpenseList = ExpenseItemGroup.groupItems(expenseViewModel.getAllExpenses().getValue());
        this.filteredGroupedExpenseList = new ArrayList<>(groupedExpenseList);
    }

    @Override
    public void onDeleteClick(ExpenseEntity expenseEntity) {
        // Handle delete action here in ET_AdapterMain
        expenseViewModel.deleteExpense(expenseEntity);
    }

    @Override
    public void onEditClick(ExpenseEntity expenseEntity, int position) {
        // Handle edit action here in ET_AdapterMain
        Log.i("edit",expenseEntity.getAmountSpent());
        Intent intent = new Intent(context, ExpenseAddForm.class);
        intent.putExtra("EDIT_MODE", true);
        intent.putExtra("EDIT_TITLE", expenseEntity.getTitle());
        intent.putExtra("EXPENSE_ID", expenseEntity.getId());
        intent.putExtra("EDIT_DESCRIPTION", expenseEntity.getDescription());
        intent.putExtra("EDIT_AMOUNT", expenseEntity.getAmountSpent());
        intent.putExtra("EDIT_CATEGORY", expenseEntity.getCategory());
        intent.putExtra("EDIT_DATE", expenseEntity.getExpenseDate());
        intent.putExtra("ITEM_POSITION", position);
        // Start AddExpenses activity for result
        ((Activity) context).startActivityForResult(intent, 1);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseItemGroup currentItemGroup = filteredGroupedExpenseList.get(position);

        // Set data to views
        holder.headerDate.setText(currentItemGroup.date);
        ExpenseAdapterChild innerAdapter = new ExpenseAdapterChild(currentItemGroup.getItems(), expenseViewModel, context, fragmentManager, this,this);
        holder.recyclerView.setAdapter(innerAdapter);
    }

    @Override
    public int getItemCount() {
        return filteredGroupedExpenseList.size();
    }

    public void setGroupedExpenseList(List<ExpenseItemGroup> groupedExpenseList) {
        this.groupedExpenseList = groupedExpenseList;
        this.filteredGroupedExpenseList = new ArrayList<>(groupedExpenseList);
        notifyDataSetChanged();
    }

    public void filterData(String query) {
        filteredGroupedExpenseList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredGroupedExpenseList.addAll(groupedExpenseList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (ExpenseItemGroup group : groupedExpenseList) {
                List<ExpenseEntity> filteredItems = new ArrayList<>();
                for (ExpenseEntity item : group.getItems()) {
                    if (item.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                            item.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                        filteredItems.add(item);
                    }
                }
                if (!filteredItems.isEmpty()) {
                    ExpenseItemGroup filteredGroup = new ExpenseItemGroup(group.getDate(), filteredItems);
                    filteredGroupedExpenseList.add(filteredGroup);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headerDate;
        public RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headerDate = itemView.findViewById(R.id.headerDate);
            recyclerView = itemView.findViewById(R.id.recyclerViewChild);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}

