package com.rku.blinksales;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.Result;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.form.Product_form;
import com.rku.blinksales.mainfragment.Cart;
import com.rku.blinksales.mainfragment.Dashboard;
import com.rku.blinksales.mainfragment.Products;

import java.util.Date;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    ZXingScannerView scannerView;
    DatabaseDao db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();

    }

    @Override
    public void handleResult(Result result) {
        Intent intent = getIntent();

        if (intent.hasExtra("key")) {
            int i = intent.getIntExtra("key", -1);
            if (i == 11) {
                Product_form.id_pro_barcode.setText(result.getText());
                Intent data = new Intent();
                data.putExtra("barcode", result.getText());
                setResult(0, data);
            }else if(i == 22){
                Products.product_searchView.setQuery(result.getText(),false);
                Intent data = new Intent();
                data.putExtra("barcode", result.getText());
                setResult(1, data);
            }else if(i == 33){
                Cart.search_view.setText(result.getText());
                Intent data = new Intent();
                data.putExtra("barcode", result.getText());
                setResult(RESULT_OK, data);
            }
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.setResultHandler(this);
//        scannerView.startCamera();
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}