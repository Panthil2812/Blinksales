package com.rku.blinksales.Roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM CategoryTable")
    LiveData<List<CategoryTable>> getAllCategory();

    @Query("Select category_name from CategoryTable")
    List<String> getCategory();


    //    ..............................      Expense Type Query    ..............................

    @Insert
    void insertExpenseType(ExpenseType expenseType);

    @Query("DELETE  FROM ExpenseType")
    void deleteAllExpenseType();


    @Query("Select expense_type from ExpenseType")
    List<String> getExpenseType();


    //    ..............................      Expense List Table Query    ..............................

    @Insert
    void  insertExpenseTable(ExpenseTable expenseTable);

    @Update
    void  updateExpenseTable(ExpenseTable expenseTable);

    @Delete
    void  deleteExpenseList(ExpenseTable expenseTable);

    @Query("Select * from ExpenseTable")
    LiveData<List<ExpenseTable>> getAllExpenseList();
}
