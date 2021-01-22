package com.rku.blinksales;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Unit extends BottomSheetDialogFragment {


    TextView text;
    FlexboxLayout flexboxLayout;
    List<String> list_text;
    public Unit(TextView text) {
        this.text = text;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_unit, container, false);
        flexboxLayout = v.findViewById(R.id.unit_container);
        list_text = new ArrayList<>(Arrays.asList("piece","kg","gram","ml","liter","mm","ft","meter","sq. ft.",
                "sq.meter","km","set","hour","day","bunch","month","year",
                "service","work","packet","box","pound","dozen","gunta","pair","minute","quintal","ton","capsul","tablet","plate"));
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10,10,10,10);
        for(int i=0;i<list_text.size();i++){
            final TextView tv = new TextView(getContext());

            tv.setText(list_text.get(i));
            tv.setHeight(120);
            tv.setTextSize(18.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setBackground(getResources().getDrawable(R.drawable.border));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(30, 10, 30, 10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),tv.getText().toString(),Toast.LENGTH_SHORT).show();

                    text.setText(tv.getText().toString());
                    dismiss();
                }
            });
            flexboxLayout.addView(tv);
        }
        return v;
    }

}