package com.rku.blinksales.Roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProductTable")
public class ProductTable {
    @PrimaryKey(autoGenerate = true)
    int product_id;
    String product_image_uri;
    String product_name;
    String product_category;
    Double product_mrp;
    Double product_selling_price;
    Double good_value;
    String product_qty;
    String product_unit;
    String product_price_unit;
    String product_barcode;
    Boolean product_stock;
    Boolean product_is_include;
    Double gst;
    Double gst_amount;
    Double discount;
    String HSN = "";

    public ProductTable(String product_image_uri, String product_name, String product_category, Double product_mrp,
                        Double product_selling_price, Double good_value, String product_qty, String product_unit,
                        String product_price_unit, String product_barcode, Boolean product_stock, Boolean product_is_include,
                        Double gst, Double gst_amount, Double discount, String HSN) {
        this.product_image_uri = product_image_uri;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_mrp = product_mrp;
        this.product_selling_price = product_selling_price;
        this.good_value = good_value;
        this.product_qty = product_qty;
        this.product_unit = product_unit;
        this.product_price_unit = product_price_unit;
        this.product_barcode = product_barcode;
        this.product_stock = product_stock;
        this.product_is_include = product_is_include;
        this.gst = gst;
        this.gst_amount = gst_amount;
        this.discount = discount;
        this.HSN = HSN;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_image_uri() {
        return product_image_uri;
    }

    public void setProduct_image_uri(String product_image_uri) {
        this.product_image_uri = product_image_uri;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public Double getProduct_mrp() {
        return product_mrp;
    }

    public void setProduct_mrp(Double product_mrp) {
        this.product_mrp = product_mrp;
    }

    public Double getProduct_selling_price() {
        return product_selling_price;
    }

    public void setProduct_selling_price(Double product_selling_price) {
        this.product_selling_price = product_selling_price;
    }

    public Double getGood_value() {
        return good_value;
    }

    public void setGood_value(Double good_value) {
        this.good_value = good_value;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_price_unit() {
        return product_price_unit;
    }

    public void setProduct_price_unit(String product_price_unit) {
        this.product_price_unit = product_price_unit;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public Boolean getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(Boolean product_stock) {
        this.product_stock = product_stock;
    }

    public Boolean getProduct_is_include() {
        return product_is_include;
    }

    public void setProduct_is_include(Boolean product_is_include) {
        this.product_is_include = product_is_include;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(Double gst_amount) {
        this.gst_amount = gst_amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }
}
