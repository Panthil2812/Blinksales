package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "PurchaseTable")
public class PurchaseTable {

    @PrimaryKey(autoGenerate = true)
    int purchase_id;
    String vendor_name;
    Double amount;
    Double pending_amount;
    Date  date;
    String bill_image;
    String note;

    public PurchaseTable(String vendor_name, Double amount, Double pending_amount, Date date, String bill_image, String note) {
        this.vendor_name = vendor_name;
        this.amount = amount;
        this.pending_amount = pending_amount;
        this.date = date;
        this.bill_image = bill_image;
        this.note = note;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPending_amount() {
        return pending_amount;
    }

    public void setPending_amount(Double pending_amount) {
        this.pending_amount = pending_amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBill_image() {
        return bill_image;
    }

    public void setBill_image(String bill_image) {
        this.bill_image = bill_image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
