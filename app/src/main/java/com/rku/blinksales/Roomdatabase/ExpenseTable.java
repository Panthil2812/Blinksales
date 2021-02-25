package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ExpenseTable")
public class ExpenseTable {
    @PrimaryKey(autoGenerate = true)
    int id;

    String exp_name;
    String exp_amount;
    Date exp_date;
    String exp_type;

    public ExpenseTable(String exp_name, String exp_amount, Date exp_date, String exp_type) {
        this.exp_name = exp_name;
        this.exp_amount = exp_amount;
        this.exp_date = exp_date;
        this.exp_type = exp_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExp_name() {
        return exp_name;
    }

    public void setExp_name(String exp_name) {
        this.exp_name = exp_name;
    }

    public String getExp_amount() {
        return exp_amount;
    }

    public void setExp_amount(String exp_amount) {
        this.exp_amount = exp_amount;
    }

    public Date getExp_date() {
        return exp_date;
    }

    public void setExp_date(Date exp_date) {
        this.exp_date = exp_date;
    }

    public String getExp_type() {
        return exp_type;
    }

    public void setExp_type(String exp_type) {
        this.exp_type = exp_type;
    }
}
