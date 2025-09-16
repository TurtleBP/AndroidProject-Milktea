package com.pro.milkteaapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.milkteaapp.R;
import com.pro.milkteaapp.adapters.CartAdapter;
import com.pro.milkteaapp.models.CartItem;
import com.pro.milkteaapp.models.MilkTea;
import com.pro.milkteaapp.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartItems;
    private TextView totalTextView;
    private Button checkoutButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeViews();
        setupToolbar();
        setupRecyclerView();
        setupClickListeners();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.shopping_cart));
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItems = getSampleCartItems();
        adapter = new CartAdapter(this, cartItems);
        recyclerView.setAdapter(adapter);
        updateTotal();
    }

    private void setupClickListeners() {
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this,
                        getString(R.string.empty_cart), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartActivity.this,
                        getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateTotal() {
        double total = calculateTotal();
        totalTextView.setText(getString(R.string.total) + ": " + MoneyUtils.formatVnd(total));
    }

    private double calculateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private List<CartItem> getSampleCartItems() {
        List<CartItem> items = new ArrayList<>();
        MilkTea milkTea1 = new MilkTea(1,
                getString(R.string.classic_milk_tea),
                50.0,
                getString(R.string.classic_milk_tea_desc),
                R.drawable.milk_tea_1,
                getString(R.string.category_classic));

        MilkTea milkTea2 = new MilkTea(2,
                getString(R.string.taro_milk_tea),
                55.0,
                getString(R.string.taro_milk_tea_desc),
                R.drawable.milk_tea_2,
                getString(R.string.category_specialty));

        items.add(new CartItem(milkTea1, 2));
        items.add(new CartItem(milkTea2, 1));

        return items;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}