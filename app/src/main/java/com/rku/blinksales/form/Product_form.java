package com.rku.blinksales.form;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rku.blinksales.R;

public class Product_form extends AppCompatActivity {
    EditText id_pro_name,id_pro_selling_price,id_pro_mrp,id_pro_qty,id_pro_barcode,id_cgst_unit,id_sgst_unit,id_hsn_unit;
    TextView id_pro_unit,id_pro_category;
    ImageView img_product;
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

        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(Product_form.this);
            }
        });

        id_pro_category.setOnClickListener(v -> {
            new DisplayCategoryFragment(id_pro_category).show(getSupportFragmentManager(),"Dialog");
        });

        id_pro_unit.setOnClickListener(v -> {
            new ADDFragment(id_pro_unit).show(getSupportFragmentManager(),"Dialog");
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        img_product.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                img_product.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}