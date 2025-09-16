package com.pro.milkteaapp.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.adapters.CartAdapter;
import com.pro.milkteaapp.models.CartItem;
import com.pro.milkteaapp.models.MilkTea;
import com.pro.milkteaapp.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemActionClickListener {
    private RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    public static List<CartItem> cartItems = new ArrayList<>();
    private CartAdapter adapter;
    private TextView totalTextView;
    private Button checkoutButton;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_milk_tea")) {
            MilkTea selectedMilkTea = (MilkTea) intent.getSerializableExtra("selected_milk_tea");
            int addedQuantity = intent.getIntExtra("quantity", 1);
            String selectedSize = intent.getStringExtra("selected_size");
            String selectedTopping = intent.getStringExtra("selected_topping");

            addItemToCart(selectedMilkTea, addedQuantity, selectedSize, selectedTopping);
        }

        initializeViews();
        setupToolbar();
        setupRecyclerView();
        setupClickListeners();
        setupBottomNavigation();
    }

    private void addItemToCart(MilkTea milkTea, int addedQuantity, String size, String topping) {
        boolean itemFound = false;
        for (CartItem item : cartItems) {
            if (item.getMilkTea().getId() == milkTea.getId() && item.getSize().equals(size) && item.getTopping().equals(topping)) {
                item.addQuantity(addedQuantity);
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            cartItems.add(new CartItem(milkTea, addedQuantity, size, topping));
        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.cartRecyclerView);
        totalTextView = findViewById(R.id.totalTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
        adapter = new CartAdapter(this, cartItems, this);
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

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                navigateToHome();
                return true;
            } else if (itemId == R.id.navigation_cart) {
                return true;
            } else if (itemId == R.id.navigation_profile) {
                navigateToProfile();
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_cart);
    }

    private void navigateToHome() {
        try {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToProfile() {
        try {
            Intent intent = new Intent(CartActivity.this, ProfileActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            updateTotal();
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemRemoved(int position) {
        // Hiển thị dialog xác nhận trước khi xóa
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position >= 0 && position < cartItems.size()) {
                            cartItems.remove(position);
                            adapter.notifyItemRemoved(position);
                            updateTotal();
                            Toast.makeText(CartActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onItemEdited(int position) {
        if (position >= 0 && position < cartItems.size()) {
            CartItem itemToEdit = cartItems.get(position);
            Toast.makeText(this, "Chỉnh sửa sản phẩm: " + itemToEdit.getMilkTea().getName(), Toast.LENGTH_SHORT).show();
            // TODO: Viết logic để mở dialog hoặc activity chỉnh sửa
        }
    }

    @Override
    public void onQuantityIncreased(int position) {
        if (position >= 0 && position < cartItems.size()) {
            CartItem item = cartItems.get(position);
            item.setQuantity(item.getQuantity() + 1);
            adapter.notifyItemChanged(position);
            updateTotal();
        }
    }

    @Override
    public void onQuantityDecreased(int position) {
        if (position >= 0 && position < cartItems.size()) {
            CartItem item = cartItems.get(position);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                adapter.notifyItemChanged(position);
            } else {
                // Khi số lượng giảm xuống 1, gọi lại phương thức onItemRemoved để hiển thị dialog xác nhận
                onItemRemoved(position);
            }
            updateTotal();
        }
    }
}