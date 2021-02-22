package com.rku.blinksales.Roomdatabase;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {
        UserTable.class,
        CategoryTable.class,
        ExpenseType.class,
        ExpenseTable.class
}, version = 1,exportSchema = false)








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
            Dao.insertCategory(new CategoryTable("Fruits1"));
            Dao.insertCategory(new CategoryTable("Grocery2"));
            Dao.insertCategory(new CategoryTable("Vegetables3"));
            Dao.insertCategory(new CategoryTable("Fruits4"));
            Dao.insertCategory(new CategoryTable("Grocery5"));
            Dao.insertCategory(new CategoryTable("Vegetables6"));
            return null;
        }
    }
}
