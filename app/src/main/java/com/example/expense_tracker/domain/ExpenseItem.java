package com.example.expense_tracker.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExpenseItem implements Parcelable {
    private long id;
    String title;
    String description;
    double amountSpent;
    String category;
    private String date; // Added for grouping by date
    private List<ExpenseItem> itemsForDate; // Added for grouping by date

    protected ExpenseItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        amountSpent = in.readDouble();
        category = in.readString();
        date = in.readString();
        itemsForDate = in.createTypedArrayList(CREATOR);
    }

    public static final Creator<ExpenseItem> CREATOR = new Creator<ExpenseItem>() {
        @Override
        public ExpenseItem createFromParcel(Parcel in) {
            return new ExpenseItem(in);
        }

        @Override
        public ExpenseItem[] newArray(int size) {
            return new ExpenseItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(amountSpent);
        dest.writeString(category);
        dest.writeString(date);
        dest.writeTypedList(itemsForDate);
    }
}
