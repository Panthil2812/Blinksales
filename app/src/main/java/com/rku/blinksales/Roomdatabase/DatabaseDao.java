package com.rku.blinksales.Roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface DatabaseDao {

//     ..............................      User Table Query    ..............................

    @Insert
    void insertUser (UserTable userTable);

    @Update
    void updateUser (UserTable userTable);

    @Query("SELECT * FROM usertable WHERE user_name ==:userName and user_password==:userPassword")
    boolean ValidateUser (String userName, String userPassword);

//    ..............................      Category Table Query    ..............................

    @Insert
    void insertCategory(CategoryTable categoryTable);

    @Update
    void updateCategory (CategoryTable categoryTable);

    @Delete
    void  deleteCategory(CategoryTable categoryTable);

    @Query("SELECT * FROM CategoryTable ORDER BY category_name ")
    LiveData<List<CategoryTable>> getAllCategory();

    @Query("Select category_name from CategoryTable ORDER BY category_name ")
    List<String> getCategory();


    //    ..............................      Expense Type Query    ..............................

    @Insert
    void insertExpenseType(ExpenseType expenseType);

    @Query("DELETE  FROM ExpenseType")
    void deleteAllExpenseType();


    @Query("Select expense_type from ExpenseType ORDER BY expense_type")
    List<String> getExpenseType();


    //    ..............................  Expense List Table Query    ..............................

    @Insert
    void  insertExpenseTable(ExpenseTable expenseTable);

    @Update
    void  updateExpenseTable(ExpenseTable expenseTable);

    @Delete
    void  deleteExpenseList(ExpenseTable expenseTable);

    @Query("Select * from ExpenseTable ORDER BY exp_date DESC , exp_type")
    LiveData<List<ExpenseTable>> getAllExpenseList();


    //    ..............................  Product Table Query    ..............................
    @Insert
    void  insertProductTable(ProductTable productTable);

    @Update
    void  updateProductTable(ProductTable productTable);

    @Delete
    void  deleteProductTable(ProductTable productTable);

    @Query("Select * from ProductTable where product_category ==:name and product_stock ==:v1")
    LiveData<List<ProductTable>> getAllProduct(String name,Boolean v1);

    @Query("select * from ProductTable ORDER BY product_name")
    LiveData<List<ProductTable>> getCategoryProducts();

    @Query("Select * from ProductTable where product_name like :searchText or product_barcode like :searchText or product_category like :searchText")
    LiveData<List<ProductTable>> searchProducts(String searchText);

    @Query("select count(product_barcode) from ProductTable where product_barcode == :barcode;")
    int countBarcode(String barcode);

    @Query("select count(product_name) from ProductTable where product_name == :name;")
    int countProductName(String name);

    @Query("UPDATE ProductTable SET product_category =:name WHERE  product_category =:old")
    void updateCategoryProductTable(String name,String old);

    @Query("SELECT * FROM ProductTable WHERE product_id ==:id")
    LiveData<List<ProductTable>> getOneProduct(String id);


    //    ..............................  Vendor Table Query    ..............................

    @Insert
    void  insertVendorTable(VendorTable vendorTable);

    @Update
    void  updateVendorTable(VendorTable vendorTable);

    @Delete
    void  deleteVendorTable(VendorTable vendorTable);

    @Query("Select * from VendorTable ORDER BY vendor_name")
    LiveData<List<VendorTable>> getAllVendorTable();

    @Query("Select * from VendorTable where vendor_name like :searchText")
    LiveData<List<VendorTable>> getFilterVendorTable(String searchText);


    //    ..............................  Cart Table Query    ..............................

    @Insert
    void  insertCartTable(CartTable cartTable);

    @Update
    void  updateCartTable(CartTable cartTable);

    @Delete
    void  deleteCartTable(CartTable cartTable);

    @Query("Select * from CartTable where cart_id ==:id")
    LiveData<List<CartTable>> getDateCartTable(int id);

    @Query("UPDATE CartTable SET selected_qty=:qty and total_amount=:amount WHERE  cart_item_id =:id")
    void updateOneCartTable(Double qty,Double amount,int id);

    @Query("SELECT SUM(total_amount) FROM CartTable WHERE cart_id ==:id;")
    Double totalCartAmount(int id);

    //    .............................. Pending Cart Table Query    ..............................

    @Insert
    void  insertPendingCartTable(PendingCartTable pendingCartTable);

    @Update
    void  updatePendingCartTable(PendingCartTable pendingCartTable);

    @Delete
    void  deletePendingCartTable(PendingCartTable pendingCartTable);

    @Query("Select * from PendingCartTable where cart_id ==:id")
    LiveData<List<PendingCartTable>> getDatePendingCartTable(int id);

    @Query("select count(cart_id) from PendingCartTable where cart_status ==1")
    int countCart();

    @Query("select cart_id from PendingCartTable Where cart_status == 1")
    int findActivityIdCart();

}
