package com.rku.blinksales.form;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.ScanCodeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Product_form extends AppCompatActivity {

    CheckBox ck_gst_included;
    EditText id_pro_name, id_pro_selling_price, id_pro_mrp, id_pro_qty, id_gst_unit, id_hsn_unit;
    public static EditText id_pro_barcode;
    TextView id_pro_unit, id_pro_category, porductpageTite;
    ImageView img_product, id_Rotate_image;
    ImageButton id_back_arrow;
    Switch id_Switch_Stock;
    Button id_pro_btn_add;
    TextInputLayout id_pro_form_barcode_layout;
    private static final int PICK_IMAGE = 100;
    DatabaseDao db;
    int editCount = -1;

    String last_image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        porductpageTite = findViewById(R.id.porductpageTite);
        id_pro_name = findViewById(R.id.id_cat_name);
        id_pro_category = findViewById(R.id.id_pro_category);
        id_pro_selling_price = findViewById(R.id.id_pro_selling_price);
        id_pro_mrp = findViewById(R.id.id_pro_mrp);
        id_pro_qty = findViewById(R.id.id_pro_qty);
        id_pro_unit = findViewById(R.id.id_pro_unit);
        id_pro_barcode = findViewById(R.id.id_pro_barcode);
        id_gst_unit = findViewById(R.id.id_gst_unit);
        id_hsn_unit = findViewById(R.id.id_hsn_unit);
        img_product = findViewById(R.id.img_product_form);
        id_Switch_Stock = findViewById(R.id.id_Switch_Stock);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        ck_gst_included = findViewById(R.id.product_gst_slab);
        id_pro_btn_add = findViewById(R.id.id_pro_btn_add);
        id_Rotate_image = findViewById(R.id.id_Rotate_image);
        id_pro_form_barcode_layout = findViewById(R.id.id_pro_form_barcode_layout);
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            editCount = intent.getIntExtra("id", -1);
            porductpageTite.setText("Edit Expense");
            last_image_uri = intent.getStringExtra("image");
            File file = new File(last_image_uri);
            Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_products).into(img_product);
            id_pro_name.setText(intent.getStringExtra("pro_name"));
            id_pro_category.setText(intent.getStringExtra("category"));
            id_pro_mrp.setText(intent.getStringExtra("mrp"));
            id_pro_selling_price.setText(intent.getStringExtra("price"));
            id_pro_qty.setText(intent.getStringExtra("qty"));
            id_pro_unit.setText(intent.getStringExtra("unit"));
            id_pro_barcode.setText(intent.getStringExtra("barcode"));

            id_hsn_unit.setText(intent.getStringExtra("hsn"));
            if (intent.getStringExtra("gst").equals("0.0")) {
                ck_gst_included.setChecked(true);
                id_gst_unit.setText("0");
                id_gst_unit.setTextColor(getResources().getColor(R.color.colorAccent));
                disableEditText(id_gst_unit);
            } else {
                id_gst_unit.setText(intent.getStringExtra("gst"));
            }
            if (intent.getBooleanExtra("stock", false)) {
                id_Switch_Stock.setChecked(true);
            } else {
                id_Switch_Stock.setChecked(false);
            }
        } else {
            porductpageTite.setText("Add Expense");
        }

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
                if (StoragePermissionGranted()) {
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

        //barcode intent layout
        id_pro_form_barcode_layout.setEndIconOnClickListener(v -> {
            Intent intent_barcode = new Intent(Product_form.this, ScanCodeActivity.class);
            intent_barcode.putExtra("key", 11);
            startActivity(intent_barcode);
            id_pro_barcode.clearFocus();
        });

        // save to product in database
        id_pro_btn_add.setOnClickListener(v -> {
            // id_pro_btn_add.requestFocus();
            String pro_name = id_pro_name.getText().toString().trim();
            String pro_category = id_pro_category.getText().toString().trim();
            String pro_price = id_pro_selling_price.getText().toString().trim();
            String pro_mrp = id_pro_mrp.getText().toString().trim();
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
            } else if (pro_mrp.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product MRP", Toast.LENGTH_SHORT).show();
            } else if (pro_price.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Selling Price", Toast.LENGTH_SHORT).show();
            } else if (Double.valueOf(id_pro_selling_price.getText().toString().trim()).doubleValue() > Double.valueOf(id_pro_mrp.getText().toString().trim()).doubleValue()) {
                Toast.makeText(getApplicationContext(), "Please product Selling price less than MRP", Toast.LENGTH_SHORT).show();
            } else if (pro_qty.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Qty", Toast.LENGTH_SHORT).show();
            } else if (pro_unit.equals("Unit")) {
                Toast.makeText(getApplicationContext(), "Please Select Product Unit", Toast.LENGTH_SHORT).show();
            } else if (pro_Barcode.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Barcode", Toast.LENGTH_SHORT).show();
            } else if (pro_price.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Product Total GST", Toast.LENGTH_SHORT).show();
            } else if (editCount != -1 && !pro_Barcode.equals(intent.getStringExtra("barcode")) && db.countBarcode(pro_Barcode) != 0) {
                Toast.makeText(getApplicationContext(), "Barcode Exists", Toast.LENGTH_SHORT).show();
            } else if (editCount == -1 && db.countBarcode(pro_Barcode) != 0) {
                Toast.makeText(getApplicationContext(), "Barcode Exists", Toast.LENGTH_SHORT).show();
            }else if (editCount != -1 && !pro_name.equals(intent.getStringExtra("pro_name")) && db.countProductName(pro_name) != 0) {
                Toast.makeText(getApplicationContext(), "Product Name Exists", Toast.LENGTH_SHORT).show();
            } else if (editCount == -1 && db.countProductName(pro_name) != 0) {
                Toast.makeText(getApplicationContext(), "Product Name Exists", Toast.LENGTH_SHORT).show();
            } else {
                // int count = db.countBarcode(pro_Barcode);

                Double GST = Double.valueOf(id_gst_unit.getText().toString().trim()).doubleValue();
                Double PRICE = Double.valueOf(id_pro_selling_price.getText().toString().trim()).doubleValue();
                Double MRP = Double.valueOf(id_pro_mrp.getText().toString().trim()).doubleValue();
                String product_name = pro_name.substring(0, 1).toUpperCase() + pro_name.substring(1).toLowerCase();
//                ProgressDialog dialog = new ProgressDialog(Product_form.this);
//                dialog.setTitle(" Please Wait");
//                dialog.setMessage("Loading . . .");

                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
//                            dialog.show();
                            Double DISCOUNT = ((MRP - PRICE) / MRP) * 100;
                            Double GST_AMOUNT = (PRICE * GST) / 100;
                            Bitmap bitmap = ((BitmapDrawable) img_product.getDrawable()).getBitmap();
                            String price_unit = pro_price + " ₹/" + pro_unit;
                            String imagepath = saveToExternalStorage(bitmap);

                            ProductTable productTable = new ProductTable(imagepath,
                                    product_name, pro_category, MRP,
                                    PRICE, pro_qty, pro_unit, price_unit,
                                    pro_Barcode, pro_stock, pro_is_included,
                                    GST, GST_AMOUNT, DISCOUNT, pro_hsn);

                            if (editCount == -1) {
                                db.insertProductTable(productTable);
                            } else {

                                File fdelete = new File(last_image_uri);
                                if (fdelete.exists()) {
                                    if (fdelete.delete()) {
                                        System.out.println("file Deleted........................................................");
                                        productTable.setProduct_id(editCount);
                                        db.updateProductTable(productTable);
                                    } else {
                                        System.out.println("file not Deleted :");
                                    }
                                }
                            }

                            System.out.println("ADD............................................................");
//                            dialog.dismiss();

//                            Toast.makeText(getApplicationContext(), "Name : " + pro_name + "\n"
//                                    + "Price :" + PRICE + "\n"
//                                    + "MRP : " + MRP + "\n"
//                                    + "GST AMOUNT : " + GST_AMOUNT + "\n"
//                                    + "Discount : " + DISCOUNT + "\n"
//                                    , Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                    }
                };
                mThread.start();
                Toast.makeText(getApplicationContext(), "Product Successfully Added.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


        // Rotate image select after gallery click button
        id_Rotate_image.setOnClickListener(v -> {
            if (img_product.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_products).getConstantState()) {
                Toast.makeText(getApplicationContext(), "Please select Product image in Gallery", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap myImg = ((BitmapDrawable) img_product.getDrawable()).getBitmap();

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                        matrix, true);

                img_product.setImageBitmap(rotated);
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
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fos);
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


    // PERMISSION RESULT
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // CALL();
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //READ AND WRITE STORAGE PERMISSION
    public boolean StoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                //Toast.makeText(Product_form.this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                //  Toast.makeText(MainActivity.this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            //Toast.makeText(MainActivity.this, "Permission is granted1", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //OPEN GALLERY IN APP
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //SELETED IMAGE SHOW INSIDE APP IMAGE VIEW
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
                        Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_products).into(img_product);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Image size less than 3MB", Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext()).load(R.drawable.ic_products).into(img_product);
                    }
                    cursor.close();
                }
            }
        }
    }

    //IMAGE STORAGE (SAVE) IN EXTERNAL STORAGE
    private String saveToExternalStorage(Bitmap bitmapImage) {
        String imageUri = null;
        String folderName = "Shopooze";
        File root = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), folderName);
        if (!root.exists()) {
            root.mkdirs();
        }
        File path = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + folderName, "image");
        if (path.exists()) {
//            Toast.makeText(getApplicationContext(), "Directory is already exist", Toast.LENGTH_SHORT).show();
            String imageName = UUID.randomUUID().toString() + ".jpg";
            File imageFile = new File(path, imageName);
            if (!imageFile.exists()) {
                //Store.....
                imageUri = imageFile.toString();
                //txt_image_uri.setText(imageFile.toString());
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 25, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            path.mkdir();
            if (path.isDirectory()) {
//                Toast.makeText(getApplicationContext(), "Directory is created successfully", Toast.LENGTH_SHORT).show();
                String imageName = UUID.randomUUID().toString() + ".jpg";
                File imagefile = new File(path, imageName);
                if (!imagefile.exists()) {
                    //Store.....
                    imageUri = imagefile.toString();
                    //  txt_image_uri.setText(imagefile.toString());
                    try {
                        FileOutputStream fos = new FileOutputStream(imagefile);
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 25, fos);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                String sMessage = "Message : failed to create directory" + "\nPath : " + Environment.getExternalStorageDirectory() +
//                        "\nmkdirs : " + path.mkdir();
//                builder.setMessage(sMessage);
//                builder.show();
            }
        }
        return imageUri;
    }


    // CLOSE KEYBOARD
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // EDIT TEXT ENABLE
    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.setEnabled(true);
    }

    // EDIT TEXT DISABLE
    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}