package com.rku.blinksales.form;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Product_form extends AppCompatActivity {
    CheckBox ck_gst_included;
    EditText id_pro_name, id_pro_selling_price, id_pro_qty, id_pro_barcode, id_gst_unit, id_hsn_unit;
    TextView id_pro_unit, id_pro_category;
    ImageView img_product;
    ImageButton id_back_arrow;
    Switch id_Switch_Stock;
    Button id_pro_btn_add;
    private static final int PICK_IMAGE = 100;
    DatabaseDao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        id_pro_name = findViewById(R.id.id_cat_name);
        id_pro_category = findViewById(R.id.id_pro_category);
        id_pro_selling_price = findViewById(R.id.id_pro_selling_price);
        id_pro_qty = findViewById(R.id.id_pro_qty);
        id_pro_unit = findViewById(R.id.id_pro_unit);
        id_pro_barcode = findViewById(R.id.id_pro_barcode);
        id_gst_unit = findViewById(R.id.id_gst_unit);
        id_hsn_unit = findViewById(R.id.id_hsn_unit);
        img_product = findViewById(R.id.img_product);
        id_Switch_Stock = findViewById(R.id.id_Switch_Stock);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        ck_gst_included = findViewById(R.id.ck_gst_included);
        id_pro_btn_add = findViewById(R.id.id_pro_btn_add);

        // checkbox of is gst included or not
        ck_gst_included.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ck_gst_included.isChecked()) {
                    closeKeyboard();
                    id_gst_unit.setText("0");
                    id_gst_unit.setTextColor(getResources().getColor(R.color.colorAccent));
                    disableEditText(id_gst_unit);

                } else {
                    closeKeyboard();
                    id_gst_unit.setText(null);
                    enableEditText(id_gst_unit);

                }
            }
        });

        // open gallery select image
        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ReadStoragePermissionGranted()) {
                    openGallery();
                }
            }
        });


        // bottom sheet of category name
        id_pro_category.setOnClickListener(v -> {
            new ADDFragment(id_pro_category, 11).show(getSupportFragmentManager(), "Dialog");
        });

        //bottom sheet of unit name
        id_pro_unit.setOnClickListener(v -> {

            new ADDFragment(id_pro_unit, 12).show(getSupportFragmentManager(), "Dialog");
        });

        //back activity call
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });


        // save to product in database
        id_pro_btn_add.setOnClickListener(v -> {
            id_pro_btn_add.requestFocus();
            String pro_name = id_pro_name.getText().toString().trim();
            String pro_category = id_pro_category.getText().toString().trim();
            String pro_price = id_pro_selling_price.getText().toString().trim();
            String pro_qty = id_pro_qty.getText().toString().trim();
            String pro_unit = id_pro_unit.getText().toString().trim();
            String pro_Barcode = id_pro_barcode.getText().toString().trim();
            String pro_gst = id_gst_unit.getText().toString().trim();
            String pro_hsn = id_hsn_unit.getText().toString().trim();
            Boolean pro_stock = id_Switch_Stock.isChecked();
            Boolean pro_is_included = ck_gst_included.isChecked();

            if (img_product.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_products).getConstantState()) {
                Toast.makeText(getApplicationContext(), "Please select Product image in Gallery", Toast.LENGTH_SHORT).show();
            } else if (pro_name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Product Name", Toast.LENGTH_SHORT).show();
            } else if (pro_category.equals("Category")) {
                Toast.makeText(getApplicationContext(), "Please Select Product Category", Toast.LENGTH_SHORT).show();
            } else if (pro_price.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Selling Price", Toast.LENGTH_SHORT).show();
            } else if (pro_qty.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Qty", Toast.LENGTH_SHORT).show();
            } else if (pro_unit.equals("Unit")) {
                Toast.makeText(getApplicationContext(), "Please Select Product Unit", Toast.LENGTH_SHORT).show();
            } else if (pro_Barcode.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Barcode", Toast.LENGTH_SHORT).show();
            } else if (pro_price.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Total GST", Toast.LENGTH_SHORT).show();
            } else {
                Double GST = Double.valueOf(id_gst_unit.getText().toString().trim()).doubleValue();
                Double PRICE = Double.valueOf(id_pro_selling_price.getText().toString().trim()).doubleValue();

                ProgressDialog dialog = new ProgressDialog(Product_form.this);
                dialog.setTitle(" Please Wait");
                dialog.setMessage("Loading . . .");
                dialog.show();
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Double GST_AMOUNT = (PRICE * GST) / 100;
                            Bitmap bitmap = ((BitmapDrawable) img_product.getDrawable()).getBitmap();
                            String price_unit = pro_price + "/" + pro_unit;
                            String UriImage = saveToInternalStorage(bitmap);
//                            if (pro_hsn.isEmpty()) {
//                                pro_hsn = null;
//                            }

                            ProductTable productTable = new ProductTable(UriImage,
                                    pro_name, pro_category,
                                    PRICE, pro_qty, pro_unit, price_unit,
                                    pro_Barcode, pro_stock, pro_is_included,
                                    GST, GST_AMOUNT, pro_hsn);
                            db.insertProductTable(productTable);
                            System.out.println("ADD............................................................");
                            dialog.dismiss();

//                            Toast.makeText(getApplicationContext(), "Name : " + pro_name + "\n"
//                                    + "Category : " + pro_category + "\n"
//                                    + "Selling price : " + PRICE + "\n"
//                                    + "Qty : " + pro_qty + "\n"
//                                    + "Unit : " + pro_unit + "\n"
//                                    + "price/unit: " + price_unit + "\n"
//                                    + "Barcode : " + pro_Barcode + "\n"
//                                    + "stock : " + pro_stock.toString() + "\n"
//                                    + "GST :" + GST + "\n"
//                                    + "HSN : " + pro_hsn + "\n"
//                                    + "GST AMOUNT : " + GST_AMOUNT + "\n"
//                                    + "Uri image : " + UriImage, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                };
                mThread.start();


                // Toast.makeText(getApplication(),UriImage,Toast.LENGTH_LONG).show();
            }
        });
    }

    // image storage in internal Storage and return uri for image
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        // Create imageDir
        String imageName = UUID.randomUUID().toString() + ".jpg";
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
        return directory.getPath() + "/" + imageName;
//        return mypath.length();
    }

    // open gallery
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //gallery to image display in product form
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (imageUri != null) {
                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    File file = new File(picturePath);
                    long l = file.length();
                    float i = (float) l / 1048576;
                    if (i < 3) {
                        System.out.println("Size of Image : " + i + " MB");
                        // imagePath.setText("URL");
                        img_product.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    } else {
                        System.out.println("Size of Image is 3 MP");
                        Toast.makeText(getApplicationContext(), "Please Image size less than 3MB", Toast.LENGTH_SHORT).show();
                        img_product.setImageResource(R.drawable.ic_products);
                    }
                    cursor.close();
                }
            }
        }
    }


    //storage permission dialog box
    public boolean ReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                //Toast.makeText(Product_form.this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                // Toast.makeText(MainActivity.this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            //Toast.makeText(MainActivity.this, "Permission is granted1", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    // permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 11:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // CALL();
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.setEnabled(true);
    }

    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}