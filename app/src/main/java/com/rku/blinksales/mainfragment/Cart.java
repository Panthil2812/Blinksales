package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rku.blinksales.Adapter.AutoCompleteCountryAdapter;
import com.rku.blinksales.Adapter.CartRecyclerViewAdapter;
import com.rku.blinksales.Adapter.ProductRecyclerViewAdapter;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.ScanCodeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart extends Fragment {
    Button id_add_products;
    RecyclerView cart_recycler_view;
    DatabaseDao db;
    CartRecyclerViewAdapter cartRecyclerViewAdapter;
    public static SearchView cart_search_view;
    ImageButton cart_barcode, cart_btn_pending;
    TextView id_clear_cart, id_checkout, id_bill_amount;
    AutoCompleteTextView search_view;
    ArrayList<ProductTable> productTables;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_cart);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.VISIBLE);
        id_btn_refresh.setVisibility(View.VISIBLE);
        db = MainRoomDatabase.getInstance(getContext()).getDao();


        //view  content
        id_add_products = view.findViewById(R.id.cart_add_products);
        cart_recycler_view = view.findViewById(R.id.cart_recycler_view);
        cart_barcode = view.findViewById(R.id.cart_barcode);
        cart_btn_pending = view.findViewById(R.id.cart_btn_pending);
        id_bill_amount = view.findViewById(R.id.id_bill_amount);
        id_checkout = view.findViewById(R.id.id_checkout);
        id_clear_cart = view.findViewById(R.id.id_clear_cart);

        productTables = new ArrayList<>(db.getAllSearchProduct(true));
        search_view = view.findViewById(R.id.cart_search);
        AutoCompleteCountryAdapter adapter = new AutoCompleteCountryAdapter(getContext(), productTables);
        search_view.setAdapter(adapter);
        int offset = 320;
        search_view.setDropDownHorizontalOffset(0 * offset);
        search_view.setDropDownWidth(search_view.getWidth() + offset * 3);

        //if any cart is activity or not
        AddCart();



        // delete product in cart with dialog box
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.delete_dialog, viewGroup, false);
                builder.setView(dialogView);
                Button OK = dialogView.findViewById(R.id.Dialog_ok);
                Button Cancel = dialogView.findViewById(R.id.Dialog_cancel);
                TextView title = dialogView.findViewById(R.id.Dialog_title);
                title.setText("Remove Product");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CartTable note = cartRecyclerViewAdapter.getNoteAt(viewHolder.getAdapterPosition());
                            db.deleteCartTable(note);
                            Toast.makeText(getActivity(), "Product Remove in Cart", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                            if (db.totalCartItem(note.getCart_id()) == 0) {
                                id_bill_amount.setText("0 ₹ /-");
                            }

                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            cartRecyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
//                            Toast.makeText(getActivity(), "Product Not deleted", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;
                    Paint p = new Paint();
                    try {
                        if (dX > 0) {
                            p.setColor(getResources().getColor(R.color.colorPrimary));
                            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                    (float) itemView.getBottom(), p);
                        } else {
                            p.setColor(getResources().getColor(R.color.colorPrimary));
                            c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                    (float) itemView.getRight(), (float) itemView.getBottom(), p);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(cart_recycler_view);


        //add more products in cart to Dashboard
        id_add_products.setOnClickListener(v -> startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class)));

        //clear cart onclick method
        id_clear_cart.setOnClickListener(v -> {
            if (db.countCart() != 0) {
                db.DeletePendingCartTable(db.findActivityIdCart());
                cart_recycler_view.setAdapter(null);
                Toast.makeText(getContext(), "CLEAR CART : id " + db.findActivityIdCart(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Not Active Cart ", Toast.LENGTH_SHORT).show();

            }
        });

        //check out cart onclick method
        id_checkout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "CHECKOUT CART", Toast.LENGTH_SHORT).show();
        });

        //pending cart onclick method
        cart_btn_pending.setOnClickListener(v -> {
            if (db.findActivityIdCart() != 0) {
                int id = db.findActivityIdCart();
                db.updateALLPendingCartTable();
                //cartRecyclerViewAdapter.notifyDataSetChanged();
                cart_recycler_view.setAdapter(null);
                id_bill_amount.setText("0 ₹ /-");
                Toast.makeText(getContext(), "PENDING CART" + db.findActivityIdCart(), Toast.LENGTH_SHORT).show();
            }


        });

        //barcode onclick method
        cart_barcode.setOnClickListener(v -> {
            Intent intent_barcode = new Intent(getContext(), ScanCodeActivity.class);
            intent_barcode.putExtra("key", 33);
            startActivity(intent_barcode);
            cart_search_view.clearFocus();
            closeKeyboard();
//            if(search_view.getText().toString().trim().isEmpty())
//            {
//                Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
//            }
        });

        //search view onclick method
        search_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductTable note = adapter.getItem(position);
                search_view.setText(note.getProduct_name());
                search_view.clearFocus();
//                Toast.makeText(getContext(),"id :"+db.findActivityIdCart(),Toast.LENGTH_SHORT).show();
                if (db.findActivityIdCart() == 0) {

                    // all cart is pending cart and zero cart created

                    PendingCartTable pendingCartTable = new PendingCartTable(1,"Cart",new Date());
                    db.insertPendingCartTable(pendingCartTable);

                    int cartId = db.findActivityIdCart();
                    CartTable cartTable = new CartTable(cartId, note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                            note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(), note.getProduct_qty(),
                            1.0, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                            note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(),
                            note.getProduct_selling_price(), note.getDiscount(), note.getHSN());
                    db.insertCartTable(cartTable);
                    AddCart();
                    Dashboard.id_dashboard_total_items.setText(db.totalCartItem(cartId).toString());
                    Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(cartId).toString() + " ₹ /-");
                    Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                }else if (db.findActivityIdCart() != 0)
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
                                        CartTable cartTable = new CartTable(cartId, note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                                                note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(), note.getProduct_qty(),
                                                qty, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                                                note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(),
                                                total, note.getDiscount(), note.getHSN());
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
                                note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(), note.getProduct_qty(),
                                1.0, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                                note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(),
                                note.getProduct_selling_price(), note.getDiscount(), note.getHSN());
                        db.insertCartTable(cartTable);
                        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(cartId).toString());
                        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(cartId).toString() + " ₹ /-");
                        Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(getContext(), "add product in cart", Toast.LENGTH_SHORT).show();
                }
                search_view.setText("");
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });

        return view;
    }


    private void AddCart() {
        if (db.findActivityIdCart() != 0) {
            // recyclerview date display
            int activity_id = db.findActivityIdCart();
            cartRecyclerViewAdapter = new CartRecyclerViewAdapter(getContext(), getActivity(), id_bill_amount);
            cart_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            cart_recycler_view.setHasFixedSize(true);
            cart_recycler_view.setAdapter(cartRecyclerViewAdapter);
            db.getDateCartTable(db.findActivityIdCart()).observe(this, new Observer<List<CartTable>>() {
                @Override
                public void onChanged(@Nullable List<CartTable> notes) {

                    cartRecyclerViewAdapter.setNotes(notes);
                }
            });
            cartRecyclerViewAdapter.setOnItemClickListener(new CartRecyclerViewAdapter.OnItemClickListener() {

                @Override
                public void plus_btn(CartTable note, EditText qty, TextView amount) {
                    closeKeyboard();
                    qty.clearFocus();
                    String str = qty.getText().toString().trim();
                    String value = note.getProduct_selling_price().toString();
                    Double number = Double.valueOf(str).doubleValue();
                    Double total_amount = Double.valueOf(value).doubleValue();
                    number++;
                    total_amount = (number * total_amount);
                    CartTable cartTable = new CartTable(note.getCart_id(), note.getProduct_id(), note.getProduct_image_uri(), note.getProduct_name(),
                            note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price(), note.getProduct_qty(),
                            number, note.getProduct_unit(), note.getProduct_price_unit(), note.getProduct_barcode(),
                            note.getProduct_stock(), note.getProduct_is_include(), note.getGst(), note.getGst_amount(),
                            total_amount, note.getDiscount(), note.getHSN());
                    cartTable.setCart_item_id(note.getCart_item_id());
                    db.updateCartTable(cartTable);
                    Dashboard.id_dashboard_total_items.setText(db.totalCartItem(note.getCart_id()).toString());
                    Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(note.getCart_id()).toString() + " ₹ /-");
                }

                @Override
                public void min_btn(CartTable note, EditText qty, TextView amount) {
                    closeKeyboard();
                    qty.clearFocus();
                    String str = qty.getText().toString().trim();
                    String value = note.getProduct_selling_price().toString();
                    Double number = Double.valueOf(str).doubleValue();
                    Double total_amount = Double.valueOf(value).doubleValue();
                    number--;
                    if (number == 0.0) {
                        Toast.makeText(getContext(), "qty not less than 0", Toast.LENGTH_SHORT).show();
                    } else {

                        total_amount = (number * total_amount);

                        CartTable cartTable = new CartTable(note.getCart_id(), note.getProduct_id(), note.getProduct_image_uri(),
                                note.getProduct_name(), note.getProduct_category(), note.getProduct_mrp(),
                                note.getProduct_selling_price(), note.getProduct_qty(), number, note.getProduct_unit(),
                                note.getProduct_price_unit(), note.getProduct_barcode(), note.getProduct_stock(),
                                note.getProduct_is_include(), note.getGst(), note.getGst_amount(), total_amount, note.getDiscount(),
                                note.getHSN());

                        cartTable.setCart_item_id(note.getCart_item_id());
                        db.updateCartTable(cartTable);
                        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(note.getCart_id()).toString());
                        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(note.getCart_id()).toString() + " ₹ /-");
                    }

                }
            });
        } else {
            cart_recycler_view.setAdapter(null);
            id_bill_amount.setText("0 ₹ /-");

        }
    }


    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            cart_search_view.clearFocus();
        }
    }
}