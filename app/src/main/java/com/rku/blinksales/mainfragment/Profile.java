package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProfileTable;
import com.rku.blinksales.Roomdatabase.VendorTable;
import com.rku.blinksales.form.Profile_form;

import java.io.File;
import java.util.List;


public class Profile extends Fragment {
    private static final int RESULT_CANCELED = 0;
    private static final int RESULT_OK = -1;
    FloatingActionButton go_to_profile_form;
    DatabaseDao db;
    public static TextView id_profile_name, id_profile_phone, id_profile_email, id_profile_form_company_name, id_profile_form_company_address, id_profile_company_email;
    public static ImageView img_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_profile);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);


        go_to_profile_form = view.findViewById(R.id.go_to_profile_form);
        id_profile_name = view.findViewById(R.id.id_profile_name);
        id_profile_phone = view.findViewById(R.id.id_profile_phone);
        id_profile_email = view.findViewById(R.id.id_profile_email);
        id_profile_form_company_name = view.findViewById(R.id.id_profile_company_name);
        id_profile_form_company_address = view.findViewById(R.id.id_profile_company_address);
        id_profile_company_email = view.findViewById(R.id.id_profile_company_email);
        img_profile = view.findViewById(R.id.img_profile);
        db = MainRoomDatabase.getInstance(getContext()).getDao();

        if (db.getProfileId() == 1) {
            ProfileTable profileTable = db.getProfileData();
            File file = new File(profileTable.getProfile_image());
            Glide.with(getContext()).load(file).placeholder(R.drawable.ic_products).into(img_profile);
            id_profile_name.setText(profileTable.getProfile_name());
            id_profile_phone.setText(profileTable.getProfile_phone());
            id_profile_email.setText(profileTable.getProfile_email());
            id_profile_form_company_name.setText(profileTable.getCompany_name());
            id_profile_company_email.setText(profileTable.getCompany_email());
            id_profile_form_company_address.setText(profileTable.getCompany_address());
        }

        go_to_profile_form.setOnClickListener(v->{
                Intent intent=new Intent(getContext(),Profile_form.class);
        startActivity(intent);
        });
        return view;
        }

        }