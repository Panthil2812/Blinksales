package com.rku.blinksales.mainfragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.AutoCompleteCountryAdapter;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

//implements GestureDetector.OnDoubleTapListener
public class Dashboard extends Fragment {
    DatabaseDao db;
    ImageButton go_to_cart, go_to_Pending;
    NavigationView navigationView;
    public static TextView id_dashboard_total_items, id_dashboard_total_amount;
    int count = 0;
    private List<ProductTable> productTables;
    AutoCompleteTextView editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        closeKeyboard();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
        FragmentManager fm = getActivity().getSupportFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        navigationView = getActivity().findViewById(R.id.nav_menu);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);


        db = MainRoomDatabase.getInstance(getContext()).getDao();
        List<String> Data = db.getCategory();
        go_to_cart = view.findViewById(R.id.go_to_cart);
        go_to_Pending = view.findViewById(R.id.go_to_Pending);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout layout = view.findViewById(R.id.tabs);
        id_dashboard_total_items = view.findViewById(R.id.id_dashboard_total_items);
        id_dashboard_total_amount = view.findViewById(R.id.id_dashboard_total_amount);
        productTables = new ArrayList<>(db.getAllSearchProduct(true));
        editText = view.findViewById(R.id.actv);
        AutoCompleteCountryAdapter adapter = new AutoCompleteCountryAdapter(getContext(), productTables);
        editText.setAdapter(adapter);
        int offset = 320;
        editText.setDropDownHorizontalOffset(0 * offset);
        editText.setDropDownWidth(editText.getWidth() + offset * 3);

        layout.setupWithViewPager(viewPager);
        MainViewPagerAdapter ViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager(), Data);
        viewPager.setAdapter(ViewPagerAdapter);
        if (db.findActivityIdCart() != 0 && db.totalCartItem(db.findActivityIdCart()) != 0) {
            id_dashboard_total_items.setText(db.totalCartItem(db.findActivityIdCart()).toString());
            id_dashboard_total_amount.setText(db.totalCartAmount(db.findActivityIdCart()).toString() + " ₹ /-");

        } else {
            id_dashboard_total_items.setText("0");
            id_dashboard_total_amount.setText("0 ₹ /-");
        }
        //go to cart
        go_to_cart.setOnClickListener(v -> {
            closeKeyboard();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Cart())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_cart);

        });
        //go to PendingCart
        go_to_Pending.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Pending_cart())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_pending_cart);

        });
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductTable note = adapter.getItem(position);
                editText.setText(note.getProduct_name());
                editText.clearFocus();
                if (db.findActivityIdCart() == 0) {

                    // all cart is pending cart and zero cart created

                    PendingCartTable pendingCartTable = new PendingCartTable(1,"Cart",new Date());
                    db.insertPendingCartTable(pendingCartTable);

                    int cartId = db.findActivityIdCart();
                    CartTable cartTable = new CartTable(cartId, note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                            note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(),note.getGood_value(), note.getProduct_qty(),
                            1.0, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                            note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(), note.getGst_amount(),
                            note.getProduct_selling_price(),note.getGood_value(), note.getDiscount(), note.getHSN());
                    db.insertCartTable(cartTable);
                    Dashboard.id_dashboard_total_items.setText(db.totalCartItem(cartId).toString());
                    Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(cartId).toString() + " ₹ /-");
                    Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                }
                else if (db.findActivityIdCart() != 0)
                {
                    //activity cart
                    int cartId = db.findActivityIdCart();
                    if(db.totalProductItem(cartId,note.getProduct_id()) == 1.0)
                    {
                        CartTable cartTableList = db.getOneCartItem(cartId,note.getProduct_id());
                        //   Toast.makeText(getContext(), "cart item :"+cartTableList.getCart_item_id(), Toast.LENGTH_SHORT).show();

                        new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
                                .setTitle("Product Exists")
                                .setMessage("Product already added to cart.\n" +
                                        "Do you want to update?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  CartTable cartTableList = db.getOneCartItem(note.getProduct_id());
                                        Double qty = cartTableList.getSelected_qty();
                                        qty++;
                                        Double total = note.getProduct_selling_price();
                                        total = qty*total;
                                        Double total_good_value = (qty*note.getGood_value());
                                        Double total_gst_amount = (qty*note.getGst_amount());
                                        CartTable cartTable = new CartTable(cartId, note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                                                note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(),note.getGood_value(), note.getProduct_qty(),
                                                qty, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                                                note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(),total_gst_amount,
                                                total,total_good_value, note.getDiscount(), note.getHSN());
                                        cartTable.setCart_item_id(cartTableList.getCart_item_id());
                                        db.updateCartTable(cartTable);
                                        //db.updateOneCartTable(qty,total,cartTableList.getCart_item_id());
                                        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(cartId).toString());
                                        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(cartId).toString() + " ₹ /-");
                                        Toast.makeText(getContext(), "Updated product in cart", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else {
                        CartTable cartTable = new CartTable(cartId, note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                                note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(),note.getGood_value(), note.getProduct_qty(),
                                1.0, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                                note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(), note.getGst_amount(),
                                note.getProduct_selling_price(),note.getGood_value(), note.getDiscount(), note.getHSN());
                        db.insertCartTable(cartTable);
                        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(cartId).toString());
                        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(cartId).toString() + " ₹ /-");
                        Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });


        return view;
    }
    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
           // product_searchView.clearFocus();
        }
    }

}

