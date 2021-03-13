package com.rku.blinksales.Roomdatabase;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "PendingCartTable")
public class PendingCartTable {
    @PrimaryKey(autoGenerate = true)
    int cart_id;
    int cart_status;
    String cart_name ;
    Date  cart_create;

    public PendingCartTable(int cart_status, String cart_name, Date cart_create) {
        this.cart_status = cart_status;
        this.cart_name = cart_name;
        this.cart_create = cart_create;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCart_status() {
        return cart_status;
    }

    public void setCart_status(int cart_status) {
        this.cart_status = cart_status;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public Date getCart_create() {
        return cart_create;
    }

    public void setCart_create(Date cart_create) {
        this.cart_create = cart_create;
    }
}
