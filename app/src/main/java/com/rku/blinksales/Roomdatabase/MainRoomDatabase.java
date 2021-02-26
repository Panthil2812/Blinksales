package com.rku.blinksales.Roomdatabase;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Database(entities = {
        UserTable.class,
        CategoryTable.class,
        ExpenseType.class,
        ExpenseTable.class,
        ProductTable.class,
        VendorTable.class
}, version = 1,exportSchema = false)



@TypeConverters({Converters.class})




public abstract class MainRoomDatabase extends RoomDatabase {
    private static MainRoomDatabase instance = null;
    public  abstract  DatabaseDao getDao();

    public static  MainRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MainRoomDatabase.class, "Shopooze.db")
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };



    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DatabaseDao Dao;
        private PopulateDbAsyncTask(MainRoomDatabase db) {
            Dao = db.getDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            noteDao.insert(new Note("Title 1", "Description 1", 1));

            try {
                Dao.insertCategory(new CategoryTable("Fruits1"));
                Dao.insertCategory(new CategoryTable("Grocery2"));
                Dao.insertCategory(new CategoryTable("Vegetables3"));
                Dao.insertCategory(new CategoryTable("Fruits4"));
                Dao.insertCategory(new CategoryTable("Grocery5"));
                Dao.insertCategory(new CategoryTable("Vegetables6"));
                Dao.insertExpenseType(new ExpenseType("Tea"));
                Dao.insertExpenseType(new ExpenseType("Travel"));
                Dao.insertExpenseType(new ExpenseType("Wages"));
                Dao.insertExpenseTable(new ExpenseTable("exp_name","1000", Calendar.getInstance().getTime(),"Tea"));
                Dao.insertExpenseTable(new ExpenseTable("panthil","100",new SimpleDateFormat("dd-MMM-yyyy").parse("23-Feb-2020"),"Travel"));
                Dao.insertVendorTable(new VendorTable("RKU","Abhi Malaviya","6353980453","pmalaviya356@rku.ac.in","Surat","22AA0ARAQ1Z0"));
                Dao.insertVendorTable(new VendorTable("RKU1","Baka Malaviya","6353980453","jmalaviya288@rku.ac.in","Rajkot","22BB0ARAQ1Z0"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
