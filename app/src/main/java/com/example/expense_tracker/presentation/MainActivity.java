package com.example.expense_tracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expense_tracker.presentation.adapters.ExpenseAdapterParent;
import com.example.expense_tracker.domain.ExpenseItemGroup;
import com.example.expense_tracker.domain.ExpenseEntity;
import com.example.expense_tracker.presentation.viewmodel.ExpenseViewModel;
import com.example.expense_tracker.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMain;
    private ExpenseAdapterParent ET_AdapterMain;
    private List<ExpenseItemGroup> groupedExpenseList;
    private ExpenseViewModel expenseViewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMain = findViewById(R.id.recyclerViewMain);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        ImageView addIcon = findViewById(R.id.addIcon);
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseAddForm.class);
                startActivityForResult(intent, 1);
            }
        });
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the data based on the search query
                ET_AdapterMain.filterData(newText);
                return false;
            }
        });
        // Inside your activity or fragment
        Button deleteAllButton = findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseViewModel.deleteAllExpenses();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        ET_AdapterMain = new ExpenseAdapterParent(expenseViewModel, this, fragmentManager);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(ET_AdapterMain);

        expenseViewModel.getAllExpenses().observe(this, new Observer<List<ExpenseEntity>>() {
            @Override
            public void onChanged(List<ExpenseEntity> expenses) {
                groupedExpenseList = ExpenseItemGroup.groupItems(expenses);
                ET_AdapterMain.setGroupedExpenseList(groupedExpenseList);
                updateUIVisibility();
                for (ExpenseEntity expense : expenses) {
                    Log.i("DatabaseUpdate", "Expense: " + expense.getTitle());
                }
            }
        });
    }

    private void updateUIVisibility() {
        TextView noItemsText = findViewById(R.id.noItemsText);
        Button deleteAllButton = findViewById(R.id.deleteAllButton);
        ImageView noItemsImage = findViewById(R.id.noItemsImage);
        SearchView searchView = findViewById(R.id.searchView);
        if (groupedExpenseList.isEmpty()) {
            noItemsText.setVisibility(View.VISIBLE);
            deleteAllButton.setVisibility(View.GONE);
            noItemsImage.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.GONE);
        } else {
            noItemsText.setVisibility(View.GONE);
            deleteAllButton.setVisibility(View.VISIBLE);
            noItemsImage.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isBottomSheetShown = false;

    public void updateMainScreenBackground(boolean isBottomSheetShown) {
        this.isBottomSheetShown = isBottomSheetShown;
        // Update the background color based on the bottom sheet visibility
        int backgroundColor = isBottomSheetShown
                ? ContextCompat.getColor(this, R.color.light_background)
                : ContextCompat.getColor(this, R.color.white);

        findViewById(R.id.mainLayout).setBackgroundColor(backgroundColor);
    }
}
