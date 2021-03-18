package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.Adapter.ProductRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.ScanCodeActivity;
import com.rku.blinksales.form.Product_form;

import java.io.File;
import java.util.List;

public class Products extends Fragment {
    DatabaseDao db;
    RecyclerView myrv;
    FloatingActionButton id_add_products;
    public static SearchView product_searchView;
    ImageButton id_search_barcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_products);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        id_add_products = view.findViewById(R.id.id_add_products);
        db = MainRoomDatabase.getInstance(getContext()).getDao();

        //fragment view
        myrv = view.findViewById(R.id.id_product_recyclerView);
        product_searchView = view.findViewById(R.id.product_search);
        id_search_barcode = view.findViewById(R.id.id_search_barcode);
        int searchCloseButtonId = product_searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.product_searchView.findViewById(searchCloseButtonId);


        //recyclerview adapter to display all product
        final ProductRecyclerViewAdapter recyclerViewAdapter = new ProductRecyclerViewAdapter(getContext());
        myrv.setLayoutManager(new LinearLayoutManager(getContext()));
        myrv.setHasFixedSize(true);
        myrv.setAdapter(recyclerViewAdapter);
        db.getCategoryProducts().observe(this, new Observer<List<ProductTable>>() {
            @Override
            public void onChanged(@Nullable List<ProductTable> notes) {
                recyclerViewAdapter.setNotes(notes);
            }
        });

        //edit product data to product form intent
        recyclerViewAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(getContext(), Product_form.class);
            intent.putExtra("id", note.getProduct_id());
            intent.putExtra("image", note.getProduct_image_uri());
            intent.putExtra("pro_name", note.getProduct_name());
            intent.putExtra("category", note.getProduct_category());
            intent.putExtra("mrp", note.getProduct_mrp().toString());
            intent.putExtra("price", note.getProduct_selling_price().toString());
            intent.putExtra("qty", note.getProduct_qty());
            intent.putExtra("unit", note.getProduct_unit());
            intent.putExtra("barcode", note.getProduct_barcode());
            intent.putExtra("gst", note.getGst().toString());
            intent.putExtra("hsn", note.getHSN());
            intent.putExtra("stock", note.getProduct_stock().booleanValue());
            startActivity(intent);
        });

        //barcode intent layout
        id_search_barcode.setOnClickListener(v -> {
            Intent intent_barcode = new Intent(getContext(), ScanCodeActivity.class);
            intent_barcode.putExtra("key", 22);
            startActivity(intent_barcode);
            product_searchView.clearFocus();
            closeKeyboard();
        });

        //SearchView Close Button Event
        closeButton.setOnClickListener(v -> {
            product_searchView.setQuery("", false);
            product_searchView.clearFocus();
            closeKeyboard();
        });

        //add new product
        id_add_products.setOnClickListener(v -> {
            startActivity(new Intent(getContext().getApplicationContext(), Product_form.class));
        });

        // delete product with delete dialog box
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
                        .setTitle("Delete Product")
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    ProductTable note = recyclerViewAdapter.getNoteAt(viewHolder.getAdapterPosition());
                                    try {
                                        File file = new File(note.getProduct_image_uri());
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                    } catch (Exception e) {
                                        e.getStackTrace();
                                    }

                                    db.deleteProductTable(note);
                                    Toast.makeText(getActivity(), "Product  deleted", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                Toast.makeText(getActivity(), "Product Not deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();


            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView
                    recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
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
        }).attachToRecyclerView(myrv);

        //search function for product search
        product_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    Toast.makeText(getContext(), "Search : " + query, Toast.LENGTH_LONG).show();
                    GetFilterData(query);
                }
                closeKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  adapter.getFilter().filter(newText);
                if (newText != null) {
                    //Toast.makeText(getContext(),"OnSearch : "+newText,Toast.LENGTH_LONG).show();
                    GetFilterData(newText);
                }
                return true;
            }

            private void GetFilterData(String str) {

                str = "%" + str + "%";
                db.searchProducts(str).observe(getViewLifecycleOwner(), new Observer<List<ProductTable>>() {
                    @Override
                    public void onChanged(List<ProductTable> notes) {
                        recyclerViewAdapter.setNotes(notes);
                    }
                });

            }
        });


        return view;

    }


    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            product_searchView.clearFocus();
        }
    }
}