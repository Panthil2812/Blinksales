package com.rku.blinksales.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rku.blinksales.InstanceClass.CardInstance;
import com.rku.blinksales.form.List_Category;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Products.db";
    public static final String TABLE_NAME = "product_table";
    public static final String TABLE_NAME2 = "category_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "PRODUCT_NAME";
    public static final String COL_3 = "PRICE";
    public static final String COL_4 = "CATEGORY";
    public static final String COL_5 = "IMAGE";
    public DatabaseHelper(@Nullable Context context) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS "  + TABLE_NAME + "("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" INTEGER,"+COL_4+" TEXT,"+COL_5+" INTEGER)");
        db.execSQL( "CREATE TABLE IF NOT EXISTS "  + TABLE_NAME2 + "("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_4+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public void insertData(String product_name, Integer price, String category,Integer image ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,product_name);
        contentValues.put(COL_3,price);
        contentValues.put(COL_4,category);
        contentValues.put(COL_5,image);

        db.insert( TABLE_NAME,null,contentValues);

    }
    public void insertCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,category);

        db.insert( TABLE_NAME2,null,contentValues);

    }

    public ArrayList<List_Category> ALL_Category_Bottomsheet(){
        ArrayList<List_Category> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select " + COL_4 + " from " + TABLE_NAME2 , null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
//                    arr = Arrays.copyOf(arr, arr.length + 1);
                    String str = cursor.getString(0);
                    List_Category card = new List_Category(str);
                    list.add(card);
//                    arr[arr.length - 1] = cursor.getString(0);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> ALL_Category(){
        SQLiteDatabase db = getReadableDatabase();
//        insertData();
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select " + COL_4 + " from " + TABLE_NAME2 + "", null);
            while(cursor.moveToNext()) {
            String name = cursor.getString(0);
            list.add(name);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<CardInstance> getAllData(String str) {
        ArrayList<CardInstance> cardlist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_4+"= '"+str+"' ORDER BY "+COL_2+" ASC",null);
        Log.i("query",res.toString());
//        Cursor res = db.rawQuery("Select * from "+TABLE_NAME,null);
        while(res.moveToNext()) {
            String name = res.getString(1);
            Integer price = res.getInt(2);
            String category = res.getString(3);
            Integer image = res.getInt(4);

            CardInstance card = new CardInstance(name,price.toString(),image);
            cardlist.add(card);
        }
        return cardlist;
    }
}
