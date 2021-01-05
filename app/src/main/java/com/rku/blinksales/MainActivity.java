package com.rku.blinksales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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
//        id_weight.setVisibility(View.GONE);
//        id_btn_refresh.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
                id_weight.setVisibility(View.VISIBLE);
                id_btn_refresh.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                Toast.makeText(this,R.string.nav_dashboard, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cart:
                getSupportActionBar().setTitle(R.string.nav_cart);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Pending_cart()).commit();
                Toast.makeText(this, R.string.nav_cart, Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_category:
//                getSupportActionBar().setTitle(R.string.nav_category);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new Category()).commit();
//                Toast.makeText(this, R.string.nav_category, Toast.LENGTH_SHORT).show();
//                break;
            case R.id.nav_products:
                getSupportActionBar().setTitle(R.string.nav_products);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Category_Products()).commit();
                Toast.makeText(this, R.string.nav_products, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this,R.string.nav_profile, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
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

}