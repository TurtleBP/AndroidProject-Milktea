package com.pro.milkteaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;
import com.google.android.material.checkbox.MaterialCheckBox; // Thêm import này
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.MilkTea;
import com.pro.milkteaapp.utils.MoneyUtils;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView milkTeaImage;
    private TextView nameTextView, priceTextView, descriptionTextView, quantityTextView;
    private Button addToCartButton, incrementButton, decrementButton;
    private RadioGroup sizeRadioGroup;
    private MaterialCheckBox toppingCheckBox; // Khai báo CheckBox
    private int quantity = 1;
    private String selectedSize = "Nhỏ";
    private String selectedTopping = ""; // Mặc định không có topping
    private MilkTea milkTea;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initializeViews();
        setupToolbar();
        setupBottomNavigation();

        milkTea = (MilkTea) getIntent().getSerializableExtra("milkTea");

        if (milkTea != null) {
            nameTextView.setText(milkTea.getName());
            descriptionTextView.setText(milkTea.getDescription());
            Glide.with(this)
                    .load(milkTea.getImageResource())
                    .placeholder(R.drawable.ic_milk_tea)
                    .into(milkTeaImage);

            // Logic xử lý CheckBox
            toppingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedTopping = toppingCheckBox.getText().toString();
                } else {
                    selectedTopping = "Không có"; // Hoặc một chuỗi rỗng
                }
                updatePriceDisplay();
            });

            updatePriceDisplay();

            sizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radioSmall) {
                    selectedSize = "Nhỏ";
                } else if (checkedId == R.id.radioMedium) {
                    selectedSize = "Vừa";
                } else if (checkedId == R.id.radioLarge) {
                    selectedSize = "Lớn";
                }
                updatePriceDisplay();
            });

            incrementButton.setOnClickListener(v -> {
                quantity++;
                quantityTextView.setText(String.valueOf(quantity));
            });
            decrementButton.setOnClickListener(v -> {
                if (quantity > 1) {
                    quantity--;
                    quantityTextView.setText(String.valueOf(quantity));
                }
            });
            addToCartButton.setOnClickListener(v -> {
                Toast.makeText(this,
                        milkTea.getName() + " " + getString(R.string.added_to_cart),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                intent.putExtra("selected_milk_tea", milkTea);
                intent.putExtra("quantity", quantity);
                intent.putExtra("selected_size", selectedSize);
                intent.putExtra("selected_topping", selectedTopping);
                startActivity(intent);
            });
        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        milkTeaImage = findViewById(R.id.milkTeaImage);
        nameTextView = findViewById(R.id.nameTextView);
        priceTextView = findViewById(R.id.priceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        quantityTextView = findViewById(R.id.quantityTextView);
        addToCartButton = findViewById(R.id.addToCartButton);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        sizeRadioGroup = findViewById(R.id.sizeRadioGroup);
        toppingCheckBox = findViewById(R.id.toppingCheckBox); // Tìm kiếm CheckBox
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void setupBottomNavigation() {
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    navigateToHome();
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    navigateToCart();
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    navigateToProfile();
                    return true;
                }
                return false;
            });
        }
    }

    private void navigateToHome() {
        try {
            Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToCart() {
        try {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToProfile() {
        try {
            Intent intent = new Intent(ProductDetailActivity.this, ProfileActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePriceDisplay() {
        double calculatedPrice = milkTea.getPrice();
        if (selectedSize.equals("Vừa")) {
            calculatedPrice += calculatedPrice * 0.1;
        } else if (selectedSize.equals("Lớn")) {
            calculatedPrice += calculatedPrice * 0.2;
        }
        if (selectedTopping.equals("Trân châu trắng")) {
            calculatedPrice += 5.0;
        }
        priceTextView.setText(MoneyUtils.formatVnd(calculatedPrice));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}