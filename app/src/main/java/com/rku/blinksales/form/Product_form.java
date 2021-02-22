package com.rku.blinksales.form;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class Product_form extends AppCompatActivity {
    EditText id_pro_name,id_pro_selling_price,id_pro_mrp,id_pro_qty,id_pro_barcode,id_cgst_unit,id_sgst_unit,id_hsn_unit;
    TextView id_pro_unit,id_pro_category;
    ImageView img_product;
    ImageButton id_back_arrow;
    Switch id_Switch_Stock;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        id_pro_name = findViewById(R.id.id_cat_name);
        id_pro_category = findViewById(R.id.id_pro_category);
        id_pro_selling_price = findViewById(R.id.id_pro_selling_price);
        id_pro_mrp = findViewById(R.id.id_pro_mrp);
        id_pro_qty = findViewById(R.id.id_pro_qty);
        id_pro_unit = findViewById(R.id.id_pro_unit);
        id_pro_barcode = findViewById(R.id.id_pro_barcode);
        id_cgst_unit = findViewById(R.id.id_cgst_unit);
        id_sgst_unit = findViewById(R.id.id_sgst_unit);
        id_hsn_unit = findViewById(R.id.id_hsn_unit);
        img_product = findViewById(R.id.img_product);
        id_Switch_Stock = findViewById(R.id.id_Switch_Stock);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ReadStoragePermissionGranted()) {
                    openGallery();
                }
            }
        });

        id_pro_category.setOnClickListener(v -> {
            new ADDFragment(id_pro_category,11).show(getSupportFragmentManager(),"Dialog");
        });

        id_pro_unit.setOnClickListener(v -> {
            new ADDFragment(id_pro_unit,12).show(getSupportFragmentManager(),"Dialog");
        });
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        // Create imageDir
        String imageName = UUID.randomUUID().toString()+".jpg";
        File mypath = new File(directory, imageName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getPath()+"/"+imageName;
//        return mypath.length();
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (imageUri != null) {
                Cursor cursor = getContentResolver().query(imageUri,filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    File file = new File(picturePath);
                    long l = file.length();
                    float i = (float)l/1048576;
                    if(i<3)
                    {
                        System.out.println("Size of Image : "+i+" MP");
                       // imagePath.setText("URL");
                        img_product.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                    }else{
                        System.out.println("Size of Image is 3 MP");
                      //  imagePath.setText("Size of Image : 3 MP");
                        img_product.setImageResource(R.drawable.ic_products);
                    }
                    cursor.close();
                }
            }
        }
    }

    public boolean ReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                 Log.v("TAG","Permission is granted");
                //Toast.makeText(Product_form.this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                // Toast.makeText(MainActivity.this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            //Toast.makeText(MainActivity.this, "Permission is granted1", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 11:
                if(grantResults.length >0 &&
                        grantResults[0]  == PackageManager.PERMISSION_GRANTED){
                    // CALL();
                    Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}