package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExpenseType")
public class ExpenseType {
    @PrimaryKey(autoGenerate = true)
    int id;

    String expense_type;

    public ExpenseType(String expense_type) {
        this.expense_type = expense_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }
}
