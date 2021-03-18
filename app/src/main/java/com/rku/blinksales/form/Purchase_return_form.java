package com.rku.blinksales.form;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.BuildConfig;
import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.Main_Full_screen;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PurchaseReturnTable;
import com.rku.blinksales.Roomdatabase.PurchaseTable;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Purchase_return_form extends AppCompatActivity {

    TextInputEditText id_pur_re_purAmount, id_pur_re_pending_amount, id_pur_re_net_amount;
    TextView id_pur_re_purdate, id_pur_re_redate, id_pur_re_ven_name;
    ImageView img_pur_return_bill, id_back_arrow, img_pur_bill;
    Button id_pur_re_btn_save, id_upload_bill;
    ImageButton search_purchase_id;
    Date createDate,returnDate;
    EditText purchase_return_search;
    ScrollView scrollview_purchase_return;
    private static final int PICK_IMAGE = 100;
    DatabaseDao db;
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_return_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        scrollview_purchase_return = findViewById(R.id.scrollview_purchase_return);
        scrollview_purchase_return.setVisibility(View.GONE);
        id_pur_re_ven_name = findViewById(R.id.id_pur_re_ven_name);
        id_pur_re_purAmount = findViewById(R.id.id_pur_re_purAmount);
        id_pur_re_pending_amount = findViewById(R.id.id_pur_re_pending_amount);
        id_pur_re_purdate = findViewById(R.id.id_pur_re_purdate);
        id_pur_re_redate = findViewById(R.id.id_pur_re_redate);
        //img_pur_reciept = findViewById(R.id.img_pur_reciept);
        id_pur_re_btn_save = findViewById(R.id.id_pur_re_btn_save);
        id_upload_bill = findViewById(R.id.id_upload_bill);
        img_pur_return_bill = findViewById(R.id.img_pur_return_bill);
        id_pur_re_net_amount = findViewById(R.id.id_pur_re_net_amount);
        purchase_return_search = findViewById(R.id.purchase_return_search);
        search_purchase_id = findViewById(R.id.search_purchase_id);

        id_pur_re_ven_name.setOnClickListener(v -> {
            new ADDFragment(id_pur_re_ven_name, 22).show(getSupportFragmentManager(), "Dialog");

        });

        id_upload_bill.setOnClickListener(v -> {
            if (StoragePermissionGranted()) {
                openGallery();
            }

        });

        id_pur_re_purdate.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    createDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String df_medium_us_str = formatter.format(createDate);
                    id_pur_re_purdate.setText(df_medium_us_str);
                }

            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        id_pur_re_redate.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    returnDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String df_medium_us_str = formatter.format(returnDate);
                    id_pur_re_redate.setText(df_medium_us_str);
                }

            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

//        img_pur_return_bill.setOnClickListener(v -> {
//            if (img_pur_return_bill.getDrawable() != null) {
//                Intent intent = new Intent(getApplicationContext(), Main_Full_screen.class);
//                img_pur_return_bill.buildDrawingCache();
//                Bitmap image= img_pur_return_bill.getDrawingCache();
//                Bundle extras = new Bundle();
//                extras.putParcelable("imagebitmap", image);
//                intent.putExtras(extras);
//                startActivity(intent);
//            }
//        });

        search_purchase_id.setOnClickListener(v -> {
            closeKeyboard();
            purchase_return_search.clearFocus();
            if (purchase_return_search.getText().toString().trim().isEmpty()) {
                scrollview_purchase_return.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Enter purchase id", Toast.LENGTH_SHORT).show();
            } else {
                int id = Integer.valueOf(purchase_return_search.getText().toString().trim());
                if (db.getcountPurchaseTable(id) == 1) {
                    PurchaseTable note = db.getPurchaseTable(id);
                    scrollview_purchase_return.setVisibility(View.VISIBLE);
                    id_pur_re_ven_name.setText(note.getVendor_name());
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        createDate = note.getDate();
                        String date = simpleDateFormat.format(createDate);
                        id_pur_re_purdate.setText(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    id_pur_re_purAmount.setText(note.getAmount().toString());
//                    id_pur_re_pending_amount.setText("0.0");
                    File f = new File(note.getBill_image());
                    Glide.with(getApplicationContext()).load(f).placeholder(R.drawable.ic_bill_list).into(img_pur_return_bill);
                    img_pur_return_bill.setVisibility(View.VISIBLE);

                    // Toast.makeText(getApplicationContext(),note.getVendor_name(),Toast.LENGTH_SHORT).show();
                } else {
                    scrollview_purchase_return.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Id Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            purchase_return_search.clearFocus();
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
                        Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_bill_list).into(img_pur_return_bill);
                        img_pur_return_bill.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Image size less than 3MB", Toast.LENGTH_SHORT).show();
//                        Glide.with(getApplicationContext()).load(R.drawable.ic_bill_list).into(img_pur_bill);
//
                        img_pur_return_bill.setVisibility(View.GONE);
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
            String imageName = "Purchase_return_bill_" + UUID.randomUUID().toString() + ".jpg";
            File imageFile = new File(path, imageName);
            if (!imageFile.exists()) {
                //Store.....
                imageUri = imageFile.toString();
                //txt_image_uri.setText(imageFile.toString());
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
            }
        }
        return imageUri;
    }

    public void pur_re_lst_btn_save(View view) {
        String VendorName = id_pur_re_ven_name.getText().toString().trim();
        String PurchaseAmount = id_pur_re_purAmount.getText().toString().trim();
        String ReturnAmount = id_pur_re_pending_amount.getText().toString().trim();
        String PurDate = id_pur_re_purdate.getText().toString().trim();
        String Pur_Re_Date = id_pur_re_redate.getText().toString().trim();
        String NetAmount = id_pur_re_net_amount.getText().toString().trim();


        if (VendorName.equals("Vendor Name")) {
            Toast.makeText(this, "Please enter Vendor Name", Toast.LENGTH_SHORT).show();
        } else if (PurDate.equals("Purchase Date")) {
            Toast.makeText(this, "Please select Purchase Date", Toast.LENGTH_SHORT).show();
        } else if (Pur_Re_Date.equals("Return Date")) {
            Toast.makeText(this, "Please select Return Date", Toast.LENGTH_SHORT).show();
        } else if (PurchaseAmount.isEmpty()) {
            Toast.makeText(this, "Please enter Purchase Amount", Toast.LENGTH_SHORT).show();
        } else if (ReturnAmount.isEmpty()) {
            Toast.makeText(this, "Please enter Return Amount", Toast.LENGTH_SHORT).show();
        } else if ((Double.valueOf(ReturnAmount).doubleValue() > Double.valueOf(PurchaseAmount).doubleValue())) {
            Toast.makeText(getApplicationContext(), "Please ReturnAmount less than PurchaseAmount", Toast.LENGTH_SHORT).show();
        } else if (NetAmount.isEmpty()) {
            Toast.makeText(this, "Please enter Net Amount", Toast.LENGTH_SHORT).show();
        } else if (img_pur_return_bill.getDrawable() == null) {
            Toast.makeText(this, "Please upload Bill", Toast.LENGTH_SHORT).show();
        } else {
            Double R_Amount = Double.valueOf(ReturnAmount).doubleValue();
            Double T_Amount = Double.valueOf(PurchaseAmount).doubleValue();
            Bitmap bitmap = ((BitmapDrawable) img_pur_return_bill.getDrawable()).getBitmap();
            String imagepath = saveToExternalStorage(bitmap);
            PurchaseReturnTable purchaseReturnTable = new PurchaseReturnTable(VendorName,T_Amount,R_Amount,createDate,returnDate,imagepath);
            db.insertPurchaseReturnTable(purchaseReturnTable);
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }


}