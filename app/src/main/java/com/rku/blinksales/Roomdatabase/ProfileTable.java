package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProfileTable")
public class ProfileTable {
    @PrimaryKey(autoGenerate = true)
    int  profile_id;

    String profile_image;
    String profile_name;
    String profile_phone;
    String profile_email;
    String company_name;
    String company_address;
    String company_email;

    public ProfileTable(String profile_image, String profile_name, String profile_phone, String profile_email, String company_name, String company_address, String company_email) {
        this.profile_image = profile_image;
        this.profile_name = profile_name;
        this.profile_phone = profile_phone;
        this.profile_email = profile_email;
        this.company_name = company_name;
        this.company_address = company_address;
        this.company_email = company_email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getProfile_phone() {
        return profile_phone;
    }

    public void setProfile_phone(String profile_phone) {
        this.profile_phone = profile_phone;
    }

    public String getProfile_email() {
        return profile_email;
    }

    public void setProfile_email(String profile_email) {
        this.profile_email = profile_email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }
}
