package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CustomerTable")
public class CustomerTable {
    @PrimaryKey(autoGenerate = true)
    int customer_id;
    String c_name;
    String c_job;
    String c_number;
    String c_email;
    String c_gst;
    String c_address;
    String c_country;
    String c_region;
    String c_city;
    String c_code;
    String c_description;

    public CustomerTable(String c_name, String c_job, String c_number, String c_email, String c_gst, String c_address, String c_country, String c_region, String c_city, String c_code, String c_description) {
        this.c_name = c_name;
        this.c_job = c_job;
        this.c_number = c_number;
        this.c_email = c_email;
        this.c_gst = c_gst;
        this.c_address = c_address;
        this.c_country = c_country;
        this.c_region = c_region;
        this.c_city = c_city;
        this.c_code = c_code;
        this.c_description = c_description;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_job() {
        return c_job;
    }

    public void setC_job(String c_job) {
        this.c_job = c_job;
    }

    public String getC_number() {
        return c_number;
    }

    public void setC_number(String c_number) {
        this.c_number = c_number;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_gst() {
        return c_gst;
    }

    public void setC_gst(String c_gst) {
        this.c_gst = c_gst;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_country() {
        return c_country;
    }

    public void setC_country(String c_country) {
        this.c_country = c_country;
    }

    public String getC_region() {
        return c_region;
    }

    public void setC_region(String c_region) {
        this.c_region = c_region;
    }

    public String getC_city() {
        return c_city;
    }

    public void setC_city(String c_city) {
        this.c_city = c_city;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getC_description() {
        return c_description;
    }

    public void setC_description(String c_description) {
        this.c_description = c_description;
    }
}
