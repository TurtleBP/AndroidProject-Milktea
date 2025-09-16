package com.pro.milkteaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.User;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        User user = new User(1,
                getString(R.string.user_name),
                getString(R.string.user_email),
                getString(R.string.user_phone),
                getString(R.string.user_address));

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
        addressTextView.setText(user.getAddress());

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                navigateToHome();
                return true;
            } else if (itemId == R.id.navigation_cart) {
                navigateToCart();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                // Đã ở màn hình Profile, không cần làm gì
                return true;
            }
            return false;
        });

        // Đảm bảo item "Profile" được chọn khi ở màn hình này
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
    }

    private void navigateToHome() {
        try {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToCart() {
        try {
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}