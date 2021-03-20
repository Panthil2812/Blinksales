package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "SalesReturnTable")
public class SalesReturnTable {
    @PrimaryKey(autoGenerate = true)
    int return_id;
    int bill_id;
    Double bill_amount;
    Double return_amount;
    String note;
    Date create_date;

    public SalesReturnTable(int bill_id, Double bill_amount, Double return_amount, String note, Date create_date) {
        this.bill_id = bill_id;
        this.bill_amount = bill_amount;
        this.return_amount = return_amount;
        this.note = note;
        this.create_date = create_date;
    }

    public int getReturn_id() {
        return return_id;
    }

    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public Double getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(Double bill_amount) {
        this.bill_amount = bill_amount;
    }

    public Double getReturn_amount() {
        return return_amount;
    }

    public void setReturn_amount(Double return_amount) {
        this.return_amount = return_amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        create_date = create_date;
    }
}
