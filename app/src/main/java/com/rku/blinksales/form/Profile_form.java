package com.rku.blinksales.form;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
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
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProfileTable;
import com.rku.blinksales.mainfragment.Profile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;


public class Profile_form extends AppCompatActivity {

    ImageButton id_back_arrow;
    ImageView img_profile_form, id_Rotate_image;
    TextInputEditText id_profile_form_name, id_profile_form__phone, id_profile_email,
            id_profile_form_company_name, id_profile_form_company_address, id_profile_form__company_email;
    Button btn_profile_form_save;
    private static final int PICK_IMAGE = 100;
    DatabaseDao db;
    int editCount = -1;

    String last_image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        img_profile_form = findViewById(R.id.img_profile_form);
        id_Rotate_image = findViewById(R.id.id_profile_Rotate_image);
        id_profile_form_name = findViewById(R.id.id_profile_form_name);
        id_profile_email = findViewById(R.id.id_profile_form_email);
        id_profile_form_company_name = findViewById(R.id.id_profile_form_company_name);
        id_profile_form_company_address = findViewById(R.id.id_profile_form_company_address);
        id_profile_form__company_email = findViewById(R.id.id_profile_form__company_email);
        id_profile_form__phone = findViewById(R.id.id_profile_form__phone);
        btn_profile_form_save = findViewById(R.id.btn_profile_form_save);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();

        if (db.getProfileId() == 1) {
            ProfileTable profileTable = db.getProfileData();

            File file = new File(profileTable.getProfile_image());
            Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_products).into(img_profile_form);
            id_profile_form_name.setText(profileTable.getProfile_name());
            id_profile_form__phone.setText(profileTable.getProfile_phone());
            id_profile_email.setText(profileTable.getProfile_email());
            id_profile_form_company_name.setText(profileTable.getCompany_name());
            id_profile_form__company_email.setText(profileTable.getCompany_email());
            id_profile_form_company_address.setText(profileTable.getCompany_address());
        }


        //save date in database
        btn_profile_form_save.setOnClickListener(v -> {
            String pro_name = id_profile_form_name.getText().toString().trim();
            String pro_phone = id_profile_form__phone.getText().toString().trim();
            String pro_email = id_profile_email.getText().toString().trim();
            String pro_com_name = id_profile_form_company_name.getText().toString().trim();
            String pro_com_email = id_profile_form__company_email.getText().toString().trim();
            String pro_com_address = id_profile_form_company_address.getText().toString().trim();

            if (img_profile_form.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_profile).getConstantState()) {
                Toast.makeText(getApplicationContext(), "Please select Profile image in Gallery", Toast.LENGTH_SHORT).show();
            } else if (pro_name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Profile Name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pro_phone) || !Patterns.PHONE.matcher(pro_phone).matches() || pro_phone.length() != 10) {
                Toast.makeText(getApplicationContext(), "You must have 10 number in your phone", Toast.LENGTH_SHORT).show();
            } else if (pro_email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(pro_email).matches()) {
                Toast.makeText(getApplicationContext(), "enter valid email id", Toast.LENGTH_SHORT).show();
            } else if (pro_com_name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Company name", Toast.LENGTH_SHORT).show();
            } else if (pro_com_email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Company Email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(pro_com_email).matches()) {
                Toast.makeText(getApplicationContext(), "enter valid email id", Toast.LENGTH_SHORT).show();
            } else if (pro_com_address.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Company Address", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap myImg = ((BitmapDrawable) img_profile_form.getDrawable()).getBitmap();
                String image_uri = saveToExternalStorage(myImg);
                ProfileTable profileTable = new ProfileTable(image_uri, pro_name, pro_phone, pro_email, pro_com_name, pro_com_address, pro_com_email);
                if (db.getProfileId() == 0) {
                    db.insertProfileTable(profileTable);
                    Profile.img_profile.setImageBitmap(myImg);
                    Profile.id_profile_name.setText(pro_name);
                    Profile.id_profile_email.setText(pro_email);
                    Profile.id_profile_phone.setText(pro_phone);
                    Profile.id_profile_form_company_name.setText(pro_com_name);
                    Profile.id_profile_company_email.setText(pro_com_email);
                    Profile.id_profile_form_company_address.setText(pro_com_address);
                } else {
                    profileTable.setProfile_id(db.getProfileId());
                    db.updateProfileTable(profileTable);
                    Profile.img_profile.setImageBitmap(myImg);
                    Profile.id_profile_name.setText(pro_name);
                    Profile.id_profile_email.setText(pro_email);
                    Profile.id_profile_phone.setText(pro_phone);
                    Profile.id_profile_form_company_name.setText(pro_com_name);
                    Profile.id_profile_company_email.setText(pro_com_email);
                    Profile.id_profile_form_company_address.setText(pro_com_address);
                }

                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

        });

        //onBack to fragment
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        id_Rotate_image.setOnClickListener(v -> {
            if (img_profile_form.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_products).getConstantState()) {
                Toast.makeText(getApplicationContext(), "Please select Product image in Gallery", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap myImg = ((BitmapDrawable) img_profile_form.getDrawable()).getBitmap();

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                        matrix, true);

                img_profile_form.setImageBitmap(rotated);
            }
        });
        // open gallery select image
        img_profile_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StoragePermissionGranted()) {
                    openGallery();
                }
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
                        Glide.with(getApplicationContext()).load(file).placeholder(R.drawable.ic_products).into(img_profile_form);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Image size less than 3MB", Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext()).load(R.drawable.ic_products).into(img_profile_form);
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
}
