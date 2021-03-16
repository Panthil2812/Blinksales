package com.rku.blinksales.form;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Purchase_list_form extends AppCompatActivity {

    EditText id_pur_lst_ven_name, id_pur_lst_amount, id_pur_lst_tax_amount, id_pur_lst_address;
    TextView id_pur_date;
    Button id_pur_lst_btn_save, id_upload_bill;
    ImageButton id_back_arrow;
    ImageView img_pur_bill;
    Date chosenDate;
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
        id_pur_lst_ven_name = findViewById(R.id.id_pur_re_ven_name);
        id_pur_lst_amount = findViewById(R.id.id_pur_re_purAmount);
        id_pur_lst_tax_amount = findViewById(R.id.id_pur_re_pending_amount);
        id_pur_lst_address = findViewById(R.id.id_pur_lst_note);
        id_pur_lst_btn_save = findViewById(R.id.id_pur_re_btn_save);
        id_pur_date = findViewById(R.id.id_pur_date);
        id_upload_bill = findViewById(R.id.id_upload_bill);
        img_pur_bill = findViewById(R.id.img_pur_bill);

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



    public void pur_lst_btn_save(View view)
    {
        String VendorName= id_pur_lst_ven_name.getText().toString().trim();
        String Amount=id_pur_lst_amount.getText().toString().trim();
        String PendingAmount=id_pur_lst_tax_amount.getText().toString().trim();
        String PurDate=id_pur_date.getText().toString().trim();

        String PurAddress=id_pur_lst_address.getText().toString().trim();

        if(VendorName.isEmpty())
            Toast.makeText(this, "Please enter Vendor Name", Toast.LENGTH_SHORT).show();
        else if(Amount.isEmpty())
            Toast.makeText(this, "Please enter Amount", Toast.LENGTH_SHORT).show();
        else if(PendingAmount.isEmpty())
            Toast.makeText(this, "Please enter Pending Amount", Toast.LENGTH_SHORT).show();
        else if(PurDate.equals("Select Date"))
            Toast.makeText(this, "Please select Date", Toast.LENGTH_SHORT).show();
        else if(img_pur_bill.getDrawable()==null)
            Toast.makeText(this, "Please upload Bill", Toast.LENGTH_SHORT).show();
        else if(PurAddress.isEmpty())
            Toast.makeText(this, "Please enter Note", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();


    }
}