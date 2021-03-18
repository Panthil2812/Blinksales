package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "PurchaseReturnTable")
public class PurchaseReturnTable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String vendor_name;
    Double amount;
    Double return_amount;
    Date date;
    Date re_date;
    String bill_image;


    public PurchaseReturnTable(String vendor_name, Double amount, Double return_amount, Date date, Date re_date, String bill_image) {
        this.vendor_name = vendor_name;
        this.amount = amount;
        this.return_amount = return_amount;
        this.date = date;
        this.re_date = re_date;
        this.bill_image = bill_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Double getReturn_amount() {
        return return_amount;
    }

    public void setReturn_amount(Double return_amount) {
        this.return_amount = return_amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getRe_date() {
        return re_date;
    }

    public void setRe_date(Date re_date) {
        this.re_date = re_date;
    }

    public String getBill_image() {
        return bill_image;
    }

    public void setBill_image(String bill_image) {
        this.bill_image = bill_image;
    }

}
