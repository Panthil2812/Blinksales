package com.rku.blinksales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rku.blinksales.login.Login_page;
import com.rku.blinksales.mainfragment.App_manual;
import com.rku.blinksales.mainfragment.Bill_list;
import com.rku.blinksales.mainfragment.Cart;
import com.rku.blinksales.mainfragment.Category;
import com.rku.blinksales.mainfragment.Category_Products;
import com.rku.blinksales.mainfragment.Contact_us;
import com.rku.blinksales.mainfragment.Customers;
import com.rku.blinksales.mainfragment.Dashboard;
import com.rku.blinksales.mainfragment.Expense_list;
import com.rku.blinksales.mainfragment.Payment_list;
import com.rku.blinksales.mainfragment.Pending_cart;
import com.rku.blinksales.mainfragment.Profile;
import com.rku.blinksales.mainfragment.Purchase_list;
import com.rku.blinksales.mainfragment.Purchase_return;
import com.rku.blinksales.mainfragment.Remain_payment_list;
import com.rku.blinksales.mainfragment.Report;
import com.rku.blinksales.mainfragment.Sales_return;
import com.rku.blinksales.mainfragment.Settings;
import com.rku.blinksales.mainfragment.Vendor_list;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView id_weight;
    ImageButton id_btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        id_weight = findViewById(R.id.id_weight);
        id_btn_refresh = findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Dashboard()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
        id_btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    id_weight.setText("0.0000");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        id_btn_refresh.setVisibility(View.GONE);
        id_weight.setVisibility(View.GONE);
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                getSupportActionBar().setTitle(R.string.nav_dashboard);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                Toast.makeText(this, R.string.nav_dashboard, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cart:
                getSupportActionBar().setTitle(R.string.nav_cart);
                id_weight.setVisibility(View.VISIBLE);
                id_btn_refresh.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Cart()).commit();
                Toast.makeText(this, R.string.nav_cart, Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_pending_cart:
//                getSupportActionBar().setTitle(R.string.nav_pending_cart);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new Pending_cart()).commit();
//                Toast.makeText(this, R.string.nav_pending_cart, Toast.LENGTH_SHORT).show();
//                break;

            case R.id.nav_Category:
                getSupportActionBar().setTitle(R.string.nav_category);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category()).commit();
                Toast.makeText(this, R.string.nav_category_products, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_category_products:
                getSupportActionBar().setTitle(R.string.nav_category_products);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category_Products()).commit();
                Toast.makeText(this, R.string.nav_category_products, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_bill_list:
                getSupportActionBar().setTitle(R.string.nav_bill_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Bill_list()).commit();
                Toast.makeText(this, R.string.nav_bill_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_remain_payment_list:
                getSupportActionBar().setTitle(R.string.nav_remain_payment_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Remain_payment_list()).commit();
                Toast.makeText(this, R.string.nav_remain_payment_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_payment_list:
                getSupportActionBar().setTitle(R.string.nav_payment_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Payment_list()).commit();
                Toast.makeText(this, R.string.nav_payment_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_expense_list:
                getSupportActionBar().setTitle(R.string.nav_expense_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Expense_list()).commit();

                Toast.makeText(this, R.string.nav_expense_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sales_return:
                getSupportActionBar().setTitle(R.string.nav_sales_return);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Sales_return()).commit();
                Toast.makeText(this, R.string.nav_sales_return, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_customers:
                getSupportActionBar().setTitle(R.string.nav_customers);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Customers()).commit();
                Toast.makeText(this, R.string.nav_customers, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_vendor_list:
                getSupportActionBar().setTitle(R.string.nav_vendor_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Vendor_list()).commit();
                Toast.makeText(this, R.string.nav_vendor_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_purchase_list:
                getSupportActionBar().setTitle(R.string.nav_purchase_list);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Purchase_list()).commit();
                Toast.makeText(this, R.string.nav_purchase_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_purchase_return:
                getSupportActionBar().setTitle(R.string.nav_purchase_return);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Purchase_return()).commit();

                Toast.makeText(this, R.string.nav_purchase_return, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_report:
                getSupportActionBar().setTitle(R.string.nav_report);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Report()).commit();
                Toast.makeText(this, R.string.nav_report, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_app_manual:
                getSupportActionBar().setTitle(R.string.nav_app_manual);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new App_manual()).commit();
                Toast.makeText(this, R.string.nav_app_manual, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_contact_Us:
                getSupportActionBar().setTitle(R.string.nav_contact_Us);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Contact_us()).commit();
                Toast.makeText(this, R.string.nav_contact_Us, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                getSupportActionBar().setTitle(R.string.nav_settings);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Settings()).commit();
                Toast.makeText(this, R.string.nav_settings, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                getSupportActionBar().setTitle(R.string.nav_profile);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profile()).commit();
                Toast.makeText(this, R.string.nav_profile, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Session session =new Session(this);
                session.setUsername("");
                startActivity(new Intent(MainActivity.this, Login_page.class));
                Toast.makeText(this, R.string.nav_logout, Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void printInvoice() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint mypaint = new Paint();

        String[] column = {"invoice number", "customer name", "contact no", "date", "qty", "amount"};

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1000, 900, 1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Canvas myCanvas = myPage.getCanvas();
        mypaint.setTextSize(50);
        myCanvas.drawText("INVOICE", 30, 80, mypaint);

        mypaint.setTextSize(30);
        myCanvas.drawText("this is a pdf bill", 30, 120, mypaint);

        mypaint.setTextAlign(Paint.Align.CENTER);
        myCanvas.drawText("Invoice No", myCanvas.getWidth() - 40, 40, mypaint);

        mypaint.setTextAlign(Paint.Align.LEFT);
        myCanvas.drawText("2", myCanvas.getWidth() - 40, 40, mypaint);

        mypaint.setColor(Color.rgb(150, 150, 150));
        myCanvas.drawRect(30, 150, myCanvas.getWidth() - 30, 160, mypaint);

        mypaint.setColor(Color.BLACK);
        myCanvas.drawText("Date", 50, 200, mypaint);
        myCanvas.drawText("12-12-2020", 250, 200, mypaint);

        myCanvas.drawText("Time", 620, 200, mypaint);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        myCanvas.drawText("12:12:00", myCanvas.getWidth() - 40, 200, mypaint);
        mypaint.setTextAlign(Paint.Align.LEFT);

        mypaint.setColor(Color.rgb(150, 150, 150));
        myCanvas.drawRect(30, 250, 250, 300, mypaint);

        mypaint.setColor(Color.WHITE);
        myCanvas.drawText("Bill No: ", 50, 285, mypaint);

        mypaint.setColor(Color.BLACK);
        myCanvas.drawText("Customer Name: ", 30, 350, mypaint);
        myCanvas.drawText("John", 280, 350, mypaint);

        myCanvas.drawText("Phone", 620, 350, mypaint);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        myCanvas.drawText("1234567890", myCanvas.getWidth() - 40, 350, mypaint);
        mypaint.setTextAlign(Paint.Align.LEFT);

        mypaint.setColor(Color.rgb(150, 150, 150));
        myCanvas.drawRect(30, 400, myCanvas.getWidth() - 30, 450, mypaint);


        mypaint.setColor(Color.WHITE);
        myCanvas.drawText("Item", 50, 435, mypaint);
        myCanvas.drawText("Qty", 550, 435, mypaint);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        myCanvas.drawText("Amount", myCanvas.getWidth() - 40, 435, mypaint);
        mypaint.setTextAlign(Paint.Align.LEFT);

        mypaint.setColor(Color.BLACK);
        myCanvas.drawText("Apple", 50, 480, mypaint);
        myCanvas.drawText("4", 550, 480, mypaint);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        myCanvas.drawText("200", myCanvas.getWidth() - 40, 435, mypaint);
        mypaint.setTextAlign(Paint.Align.RIGHT);


        mypaint.setColor(Color.rgb(150, 150, 150));
        myCanvas.drawRect(30, 550, myCanvas.getWidth() - 30, 560, mypaint);

        mypaint.setColor(Color.BLACK);
        myCanvas.drawText("SUBTOTAL", 550, 600, mypaint);
        myCanvas.drawText("Tax 4%", 550, 600, mypaint);
        mypaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        myCanvas.drawText("TOTAL", 550, 600, mypaint);
        mypaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        //10:58


    }

}