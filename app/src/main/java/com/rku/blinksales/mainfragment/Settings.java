package com.rku.blinksales.mainfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rku.blinksales.R;

public class Settings  extends Fragment {

    TextView txt_set_general,txt_set_wireless_conn,txt_set_barcode_printing, txt_set_invoice_printing,txt_set_imp_ex_db,txt_set_other;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_settings);

        txt_set_general = getActivity().findViewById(R.id.txt_set_general);
        txt_set_wireless_conn = getActivity().findViewById(R.id.txt_set_wireless_conn);
        txt_set_barcode_printing = getActivity().findViewById(R.id.txt_set_barcode_printing);
        txt_set_invoice_printing = getActivity().findViewById(R.id.txt_set_invoice_printing);
        txt_set_imp_ex_db = getActivity().findViewById(R.id.txt_set_imp_ex_db);
        txt_set_other = getActivity().findViewById(R.id.txt_set_other);


        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        return view;

    }

}