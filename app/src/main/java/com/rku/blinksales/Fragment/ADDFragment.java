package com.rku.blinksales.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.ExpenseType;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.mainfragment.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ADDFragment extends BottomSheetDialogFragment {

    TextView text;
    List<String> list_text = null;
    int i ;
    private static DatabaseDao db ;
    public ADDFragment(TextView id_pro_unit,int i) {

        this.text = id_pro_unit;
       this.i = i;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //db = MainRoomDatabase.getInstance(getContext()).getDao();
        View v = inflater.inflate(R.layout.activity_unit, container, false);
        FlexboxLayout flexboxLayout = v.findViewById(R.id.unit_container);
        TextView title = v.findViewById(R.id.txtTitleChoose);
        ImageButton id_Delete_type = v.findViewById(R.id.id_Delete_type);
        Button id_cat_btn_save = v.findViewById(R.id.id_cat_btn_save);
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        if(i == 12) {
            list_text = new ArrayList<>(Arrays.asList("Piece", "Kg", "Gram", "Ml", "Liter", "Mm", "Ft", "Meter", "Sq. Ft.",
                    "Sq.Meter", "km", "Set", "Hour", "Day", "Bunch", "Month", "Year",
                    "Packet", "Box", "Pound", "Dozen", "Gunta", "Pair", "Minute", "Quintal", "Ton", "Capsul", "Tablet", "Plate"));

            id_cat_btn_save.setVisibility(View.GONE);
            id_Delete_type.setVisibility(View.GONE);
        }else if(i == 11){
            id_Delete_type.setVisibility(View.GONE);
            list_text = db.getCategory();
            title.setText("Choose Product Category ");
        }else if (i == 13)
        {
            list_text  = db.getExpenseType();
            title.setText("Choose Expense Type");
        }
        id_cat_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryDialog(v);
            }
        });

        id_Delete_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog(v,getContext());
                dismiss();
                text.setText("Select Expense Type ");

            }
        });
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10, 10, 10, 10);
        for (int i = 0; i < list_text.size(); i++) {
            final TextView tv = new TextView(getContext());
            tv.setText(list_text.get(i). substring(0, 1). toUpperCase() +list_text.get(i).substring(1).toLowerCase());
            tv.setHeight(120);
            tv.setTextColor(Color.rgb(68,44,46));
            tv.setTextSize(14.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setBackground(getResources().getDrawable(R.drawable.unit_border));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(30, 10, 30, 10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();

                    text.setText(tv.getText().toString());
                    dismiss();
                }
            });
            flexboxLayout.addView(tv);
        }
        return v;
    }
    private void CategoryDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = v.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.catagory_form_bottom_sheet, viewGroup, false);
        builder.setView(dialogView);
        Button btnAdd = dialogView.findViewById(R.id.id_cat_btn_save);
        TextView titleDialog = dialogView.findViewById(R.id.texttitle);
        if(i == 13)
        {
            titleDialog.setText("ADD EXPENSE TYPE");
        }
        EditText editName = dialogView.findViewById(R.id.id_cat_name);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editName.getText().toString().trim();
                category = category. substring(0, 1). toUpperCase() +category.substring(1).toLowerCase();
                if (category.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_LONG).show();
                } else {
                    if(i == 13)
                    {
                        ExpenseType expenseType = new ExpenseType(category);
                        db.insertExpenseType(expenseType);
                    }else {
                        CategoryTable categoryTable = new CategoryTable(category);
                        db.insertCategory(categoryTable);
                    }
                    text.setText(category);
                   // Toast.makeText(getActivity(), "ADD : " + category, Toast.LENGTH_LONG).show();
                         dismiss();
                        alertDialog.dismiss();

                }
            }
        });
    }
    public static void DeleteDialog(View view, Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.delete_dialog, viewGroup, false);
        builder.setView(dialogView);
        TextView Dialog_title= dialogView.findViewById(R.id.Dialog_title);
        TextView Dialog_message= dialogView.findViewById(R.id.Dialog_message);
        Button OK = dialogView.findViewById(R.id.Dialog_ok);
        Button Cancel = dialogView.findViewById(R.id.Dialog_cancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.deleteAllExpenseType();
                Toast.makeText(c, "Category  deleted", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                 Toast.makeText(c, "Category Not deleted", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });
    }
}
