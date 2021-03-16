package com.rku.blinksales.form;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.Main_Full_screen;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PurchaseTable;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Purchase_list_form extends AppCompatActivity {

    EditText id_pur_lst_amount, id_pur_re_pending_amount, id_pur_lst_address, id_pur_lst_note;
    TextView id_pur_date, id_pur_re_ven_name;
    Button id_pur_lst_btn_save, id_upload_bill;
    ImageButton id_back_arrow;
    ImageView img_pur_bill;
    Date chosenDate, editDate;
    private static final int PICK_IMAGE = 100;
    DatabaseDao db;
    int editCount = -1;
    String last_image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        id_pur_re_ven_name = findViewById(R.id.id_pur_re_ven_name);
        id_pur_lst_amount = findViewById(R.id.id_pur_re_purAmount);
        id_pur_re_pending_amount = findViewById(R.id.id_pur_re_pending_amount);
        id_pur_lst_btn_save = findViewById(R.id.id_pur_re_btn_save);
        id_pur_date = findViewById(R.id.id_pur_date);
        id_upload_bill = findViewById(R.id.id_upload_bill);
        img_pur_bill = findViewById(R.id.img_pur_bill);
        id_pur_lst_note = findViewById(R.id.id_pur_lst_note);
        id_pur_re_pending_amount.setText("0.0");
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                PurchaseTable note = db.getPurchaseTable(id);
                id_pur_re_ven_name.setText(note.getVendor_name());
                id_pur_lst_amount.setText(String.valueOf(note.getAmount()));
                id_pur_re_pending_amount.setText(String.valueOf(note.getPending_amount()));
                last_image_uri=note.getBill_image();
                File f = new File(last_image_uri);
                Glide.with(getApplicationContext()).load(f).placeholder(R.drawable.ic_bill_list).into(img_pur_bill);
                img_pur_bill.setVisibility(View.VISIBLE);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    chosenDate = note.getDate();
                    String date = simpleDateFormat.format(chosenDate);
                    id_pur_date.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                id_pur_lst_note.setText(note.getNote());
            }
        }

        id_pur_re_ven_name.setOnClickListener(v -> {
            new ADDFragment(id_pur_re_ven_name, 22).show(getSupportFragmentManager(), "Dialog");
        });

        id_upload_bill.setOnClickListener(v -> {
            if (StoragePermissionGranted()) {
                openGallery();
            }

        });
        //DATE DIALOG BOX SELECT
        id_pur_date.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    chosenDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String df_medium_us_str = formatter.format(chosenDate);
                    id_pur_date.setText(df_medium_us_str);
                }

            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

//        img_pur_bill.setOnClickListener(v -> {
//            if (img_pur_bill.getDrawable() != null) {
//                Intent intent_full = new Intent(getApplicationContext(), Main_Full_screen.class);
//                img_pur_bill.buildDrawingCache();
//                Bitmap image= img_pur_bill.getDrawingCache();
//                Bundle extras = new Bundle();
//                extras.putParcelable("imagebitmap", image);
//                intent.putExtras(extras);
//                startActivity(intent_full);
//            }
//        });
        id_pur_lst_btn_save.setOnClickListener(v -> {
            //String PendingAmount = id_pur_re_pending_amount.getText().toString().trim();

            if (id_pur_re_pending_amount.getText().toString().trim().isEmpty()) {
                id_pur_re_pending_amount.setText("0.0");
                // Toast.makeText(getApplicationContext(),id_pur_re_pending_amount.getText().toString().trim(),Toast.LENGTH_SHORT).show();
            }

            String VendorName = id_pur_re_ven_name.getText().toString().trim();
            String Amount = id_pur_lst_amount.getText().toString().trim();
            String PendingAmount = id_pur_re_pending_amount.getText().toString().trim();
            String PurDate = id_pur_date.getText().toString().trim();
            String note = id_pur_lst_note.getText().toString().trim();

            if (VendorName.equals("Vendor Name")) {
                Toast.makeText(this, "Please enter Vendor Name", Toast.LENGTH_SHORT).show();
            } else if (Amount.isEmpty()) {
                Toast.makeText(this, "Please enter Amount", Toast.LENGTH_SHORT).show();
            } else if ((Double.valueOf(PendingAmount).doubleValue() > Double.valueOf(Amount).doubleValue())) {
                Toast.makeText(getApplicationContext(), "Please Pending Amount less than Amount", Toast.LENGTH_SHORT).show();
            } else if (PurDate.equals("Select Date")) {
                Toast.makeText(this, "Please select Date", Toast.LENGTH_SHORT).show();
            } else if (img_pur_bill.getDrawable() == null) {
                Toast.makeText(this, "Please upload Bill", Toast.LENGTH_SHORT).show();
            } else if (note.isEmpty()) {
                Toast.makeText(this, "Please enter Note", Toast.LENGTH_SHORT).show();
            } else {
                Double P_Amount = Double.valueOf(PendingAmount).doubleValue();
                Double T_Amount = Double.valueOf(Amount).doubleValue();
                Bitmap bitmap = ((BitmapDrawable) img_pur_bill.getDrawable()).getBitmap();
                String imagepath = saveToExternalStorage(bitmap);

                if (intent.hasExtra("id")) {
                    File file = new File(last_image_uri);
                    if (file.exists()) {
                        file.delete();
                    }
                    PurchaseTable purchaseTable = new PurchaseTable(VendorName, T_Amount, P_Amount, chosenDate, imagepath, note);
                    purchaseTable.setPurchase_id(intent.getIntExtra("id", -1));
                    db.updatePurchaseTable(purchaseTable);
                    Toast.makeText(this, "Successfully Edit Bill", Toast.LENGTH_LONG).show();
                } else {
                    PurchaseTable purchaseTable = new PurchaseTable(VendorName, T_Amount, P_Amount, chosenDate, imagepath, note);
                    db.insertPurchaseTable(purchaseTable);

                    Toast.makeText(this, "Successfully Add Bill", Toast.LENGTH_LONG).show();
                }
                onBackPressed();
            }


        });
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
                        Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_bill_list).into(img_pur_bill);
                        img_pur_bill.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Image size less than 3MB", Toast.LENGTH_SHORT).show();
//                        Glide.with(getApplicationContext()).load(R.drawable.ic_bill_list).into(img_pur_bill);
//
                        img_pur_bill.setVisibility(View.GONE);
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
            String imageName = "Purchase_bill_" + UUID.randomUUID().toString() + ".jpg";
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
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                String sMessage = "Message : failed to create directory" + "\nPath : " + Environment.getExternalStorageDirectory() +
//                        "\nmkdirs : " + path.mkdir();
//                builder.setMessage(sMessage);
//                builder.show();
            }
        }
        return imageUri;
    }

}