package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "BillTable")
public class BillTable {
    @PrimaryKey(autoGenerate = true)
    int bill_id;
    Date bill_date;
    Double amount;
    Double bill_amount;
    Double total_discount;
    Double discount_amount;
    Double total_get;
    String bill_method;
    String customer_name;
    String customer_number;
    String customer_gst;
    String gst_number;
    Double packing_charge;
    Double delivery_charge;

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(Double bill_amount) {
        this.bill_amount = bill_amount;
    }

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }

    public Double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(Double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public Double getTotal_get() {
        return total_get;
    }

    public void setTotal_get(Double total_get) {
        this.total_get = total_get;
    }

    public String getBill_method() {
        return bill_method;
    }

    public void setBill_method(String bill_method) {
        this.bill_method = bill_method;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getCustomer_gst() {
        return customer_gst;
    }

    public void setCustomer_gst(String customer_gst) {
        this.customer_gst = customer_gst;
    }

    public String getGst_number() {
        return gst_number;
    }

    public void setGst_number(String gst_number) {
        this.gst_number = gst_number;
    }

    public Double getPacking_charge() {
        return packing_charge;
    }

    public void setPacking_charge(Double packing_charge) {
        this.packing_charge = packing_charge;
    }

    public Double getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(Double delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public BillTable(Date bill_date, Double amount, Double bill_amount, Double total_discount, Double discount_amount, Double total_get, String bill_method, String customer_name, String customer_number, String customer_gst, String gst_number, Double packing_charge, Double delivery_charge) {
        this.bill_date = bill_date;
        this.amount = amount;
        this.bill_amount = bill_amount;
        this.total_discount = total_discount;
        this.discount_amount = discount_amount;
        this.total_get = total_get;
        this.bill_method = bill_method;
        this.customer_name = customer_name;
        this.customer_number = customer_number;
        this.customer_gst = customer_gst;
        this.gst_number = gst_number;
        this.packing_charge = packing_charge;
        this.delivery_charge = delivery_charge;
    }
}
