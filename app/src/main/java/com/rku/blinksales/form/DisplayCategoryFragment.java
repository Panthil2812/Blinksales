package com.rku.blinksales.form;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.Database.DatabaseHelper;
import com.rku.blinksales.R;

import java.util.List;

public class DisplayCategoryFragment extends BottomSheetDialogFragment {

    List<List_Category> list_text;
    DatabaseHelper db;
    FlexboxLayout container1;
    Button add,btn_add;
    TextInputEditText edit_name;
    TextView id_pro_category;

    public DisplayCategoryFragment(TextView id_pro_category) {
        this.id_pro_category = id_pro_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
        db = new DatabaseHelper(getContext());
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottomsheet_category, container, false);

        add = v.findViewById(R.id.id_cat_btn_save);
        container1=v.findViewById(R.id.v_container);
        add.setOnClickListener(v1 -> {
            View view1 = getLayoutInflater().inflate(R.layout.catagory_form_bottom_sheet,null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v1.getContext());
            edit_name = view1.findViewById(R.id.id_cat_name);
            btn_add = view1.findViewById(R.id.id_cat_btn_save);
            btn_add.setOnClickListener(v2 -> {
                String str = edit_name.getText().toString().trim();
                db.insertCategory(str);;
                bottomSheetDialog.dismiss();
                dismiss();
                new DisplayCategoryFragment(id_pro_category).show(getFragmentManager(),"d1");
            });
            bottomSheetDialog.setContentView(view1);
            bottomSheetDialog.show();
        });
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10,10,10,10);
        list_text = db.ALL_Category_Bottomsheet();
        for(int i=0;i<list_text.size();i++){
            final TextView tv = new TextView(getContext());

            tv.setText(list_text.get(i).getC_name());
            tv.setHeight(100);
            tv.setTextSize(18.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setBackground(getResources().getDrawable(R.drawable.unit_border));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(20, 10, 20, 10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),tv.getText().toString(),Toast.LENGTH_SHORT).show();

                    id_pro_category.setText(tv.getText().toString());
                    dismiss();
                }
            });
            container1.addView(tv);
        }
        return v;
    }

}