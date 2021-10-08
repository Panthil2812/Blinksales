package com.rku.blinksales.Roomdatabase;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.rku.blinksales.R;
import com.rku.blinksales.mainfragment.Profile;

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
        VendorTable.class,
        CartTable.class,
        PendingCartTable.class,
        ProfileTable.class,
        SoldItemTable.class,
        BillTable.class,
        PurchaseTable.class,
        PurchaseReturnTable.class,
        CustomerTable.class,
        SalesReturnTable.class,
        ReturnItemsTable.class
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
                Dao.insertCategory(new CategoryTable("Balaji products"));
                Dao.insertCategory(new CategoryTable("Chocolate"));
                Dao.insertCategory(new CategoryTable("Beverages"));
                Dao.insertCategory(new CategoryTable("Dairy products"));
                Dao.insertCategory(new CategoryTable("Fruits"));
                Dao.insertCategory(new CategoryTable("Grocery"));
                Dao.insertCategory(new CategoryTable("Vegetable"));
                Dao.insertCategory(new CategoryTable("Biscuits"));

                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.apple,"Apple","Fruits",100.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.amuldark,"Amul Dark Chocolate","Chocolate",150.98,100.00,100.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.atta,"Atta","Grocery",80.98,60.00,60.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.banana,"Banana","Fruits",50.00,45.00,45.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.bottelgroud,"Bottle Groud","Vegetable",52.98,50.00,50.00,"100","kg","80/kg","18SOEIT11014",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.brinjal,"Brinjal","Vegetable",45.43,40.00,40.00,"100","kg","80/kg","18SOEIT11028",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.butter,"Butter","Dairy products",110.98,80.68,80.68,"100","kg","80/kg","18SOEIT11017",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.cabbage,"Cabbage","Vegetable",30.98,25.00,25.00,"100","kg","80/kg","18SOEIT11014",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.cheese,"Cheese","Dairy products",650.98,600.00,600.00,"100","kg","80/kg","18SOEIT11017",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.chillipowder,"Chillipowder","Grocery",200.98,190.00,190.00,"100","kg","80/kg","18SOEIT11014",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.cocacola,"CocaCola","Beverages",49.99,49.99,49.99,"100","kg","80/kg","18SOEIT11028",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.davat,"Davat","Beverages",10.00,10.00,10.00,"100","kg","80/kg","18SOEIT11028",true,true,8.9,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.flaminhot,"Flaminhot","Balaji products",20.00,15.00,15.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.fulkobi,"Cauli Flower","Vegetable",46.98,40.00,40.68,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.ghee,"Ghee","Dairy products",650.98,600.00,600.68,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.goodday,"GoodDay","Biscuits",73.98,70.00,70.00,"100","kg","80/kg","18SOEIT11019",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.grapes,"Grapes","Fruits",70.98,60.00,60.00,"100","kg","80/kg","18SOEIT11019",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.guvar,"Cluster Bean","Vegetable",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));

                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.kajukatri,"Kaju Katli","Dairy products",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.kitkat,"Kit-Kat","Chocolate",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.kiwi,"Kiwi","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.lemon,"Lemon","Vegetable",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.mango,"Mango","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.masala,"Masala","Beverages",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.milk,"Milk","Dairy products",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.monco,"Monaco","Biscuits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.oil,"Oil","Grocery",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.orange,"Orange","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11019",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.paneer,"Paneer","Dairy products",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.pepsi,"Pepsi","Beverages",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.parleg,"Parle-G","Biscuits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.pineapple,"Pineapple","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.potato,"Potato","Vegetable",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" +  R.drawable.ratalamisev,"Ratalami Sev","Balaji products",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.salt,"Salt","Grocery",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.shrikhand,"Shrikhand","Dairy products",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.sitafal,"Custedapple","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.sprite,"Sprite","Beverages",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.star,"5-Star","Chocolate",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.strawberry,"Strawberry","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.sugar,"Sugar","Grocery",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11019",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.thumsup,"Thums-up","Beverages",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11028",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.tomato,"Tomato","Vegetable",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11017",true,true,8.0,90.0,0.0,"R356FHR"));
                Dao.insertProductTable(new ProductTable("android.resource://com.rku.blinksales/" + R.drawable.watermelon,"Watermelon","Fruits",80.98,80.00,80.00,"100","kg","80/kg","18SOEIT11014",true,true,8.0,90.0,0.0,"R356FHR"));

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
    public static String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
