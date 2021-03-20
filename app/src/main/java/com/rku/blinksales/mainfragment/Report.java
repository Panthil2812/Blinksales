package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rku.blinksales.R;
import com.rku.blinksales.Reports.Expense_report;
import com.rku.blinksales.Reports.Purchase_report;
import com.rku.blinksales.Reports.Sales_report;
import com.rku.blinksales.Reports.Tax_report;
import com.rku.blinksales.form.Customers_form;
import com.rku.blinksales.form.Product_form;

public class Report  extends Fragment {

    TextView txt_rpt_sales,txt_rpt_purchase,txt_rpt_expense,txt_rpt_customer,txt_rpt_tax,txt_rpt_vendor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_report);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);

        //main page code
        txt_rpt_sales = view.findViewById(R.id.txt_rpt_sales);
        txt_rpt_purchase = view.findViewById(R.id.txt_rpt_purchase);
        txt_rpt_expense = view.findViewById(R.id.txt_rpt_expense);
        txt_rpt_customer = view.findViewById(R.id.txt_rpt_customer);
        txt_rpt_tax = view.findViewById(R.id.txt_rpt_tax);
        txt_rpt_vendor = view.findViewById(R.id.txt_rpt_vendor);

        txt_rpt_sales.setOnClickListener(v -> {
            startActivity(new Intent(getContext(),Sales_report.class));
        });
        txt_rpt_purchase.setOnClickListener(v -> {
            startActivity(new Intent(getContext(),Purchase_report.class));
        });
        txt_rpt_expense.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Expense_report.class));
        });
        txt_rpt_customer.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(),));
        });
        txt_rpt_tax.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Tax_report.class));
        });
        txt_rpt_vendor.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(),));
        });

        return view;

    }
}