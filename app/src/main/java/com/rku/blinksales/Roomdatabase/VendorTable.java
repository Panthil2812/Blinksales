package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VendorTable")
public class VendorTable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String company_name;
    String vendor_name;
    String phone_number;
    String v_email_id;
    String v_address;
    String v_gst_number;

    public VendorTable(String company_name, String vendor_name, String phone_number, String v_email_id, String v_address, String v_gst_number) {
        this.company_name = company_name;
        this.vendor_name = vendor_name;
        this.phone_number = phone_number;
        this.v_email_id = v_email_id;
        this.v_address = v_address;
        this.v_gst_number = v_gst_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getV_email_id() {
        return v_email_id;
    }

    public void setV_email_id(String v_email_id) {
        this.v_email_id = v_email_id;
    }

    public String getV_address() {
        return v_address;
    }

    public void setV_address(String v_address) {
        this.v_address = v_address;
    }

    public String getV_gst_number() {
        return v_gst_number;
    }

    public void setV_gst_number(String v_gst_number) {
        this.v_gst_number = v_gst_number;
    }
}
