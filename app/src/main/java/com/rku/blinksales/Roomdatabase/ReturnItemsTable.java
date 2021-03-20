package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ReturnItemsTable")
public class ReturnItemsTable {
    @PrimaryKey(autoGenerate = true)
    int r_item_id;
    int bill_id;
    int product_id;
    int check;
    Double qty;
    Double amount;
    Double good_amount;
    Double gst_amount;

    public ReturnItemsTable(int bill_id, int product_id, int check, Double qty, Double amount, Double good_amount, Double gst_amount) {
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.check = check;
        this.qty = qty;
        this.amount = amount;
        this.good_amount = good_amount;
        this.gst_amount = gst_amount;
    }

    public int getR_item_id() {
        return r_item_id;
    }

    public void setR_item_id(int r_item_id) {
        this.r_item_id = r_item_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getGood_amount() {
        return good_amount;
    }

    public void setGood_amount(Double good_amount) {
        this.good_amount = good_amount;
    }

    public Double getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(Double gst_amount) {
        this.gst_amount = gst_amount;
    }
}
