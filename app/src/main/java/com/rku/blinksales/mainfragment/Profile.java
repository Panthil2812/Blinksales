package com.rku.blinksales.mainfragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.rku.blinksales.R;


public class Profile  extends Fragment {
    private static final int RESULT_CANCELED = 0;
    private static final int RESULT_OK = -1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        id_img_profile = view.findViewById(R.id.id_img_profile);
//        id_img_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return view;
    }

}