package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProductTable")
public class ProductTable {
    @PrimaryKey(autoGenerate = true)
    int product_id;

    String product_name;
    String product_category;
    Double product_mrp;
    Double product_selling_price;

}
