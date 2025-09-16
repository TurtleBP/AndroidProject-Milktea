package com.pro.milkteaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.adapters.MilkTeaAdapter;
import com.pro.milkteaapp.models.MilkTea;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MilkTeaAdapter adapter;
    private List<MilkTea> milkTeaList;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setupRecyclerView();
        setupBottomNavigation();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        milkTeaList = getSampleMilkTea();
        adapter = new MilkTeaAdapter(this, milkTeaList);
        recyclerView.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
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

    private void navigateToCart() {
        try {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToProfile() {
        try {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MilkTea> getSampleMilkTea() {
        List<MilkTea> list = new ArrayList<>();
        list.add(new MilkTea(1,
                getString(R.string.classic_milk_tea),
                50.0,
                getString(R.string.classic_milk_tea_desc),
                R.drawable.milk_tea_1,
                getString(R.string.category_classic)));

        list.add(new MilkTea(2,
                getString(R.string.taro_milk_tea),
                55.0,
                getString(R.string.taro_milk_tea_desc),
                R.drawable.milk_tea_2,
                getString(R.string.category_specialty)));

        list.add(new MilkTea(3,
                getString(R.string.matcha_milk_tea),
                60.0,
                getString(R.string.matcha_milk_tea_desc),
                R.drawable.milk_tea_3,
                getString(R.string.category_green_tea)));

        list.add(new MilkTea(4,
                getString(R.string.thai_milk_tea),
                45.0,
                getString(R.string.thai_milk_tea_desc),
                R.drawable.milk_tea_4,
                getString(R.string.category_classic)));

        list.add(new MilkTea(5,
                getString(R.string.honeydew_milk_tea),
                58.0,
                getString(R.string.honeydew_milk_tea_desc),
                R.drawable.milk_tea_5,
                getString(R.string.category_fruit_tea)));

        list.add(new MilkTea(6,
                getString(R.string.wintermelon_milk_tea),
                52.0,
                getString(R.string.wintermelon_milk_tea_desc),
                R.drawable.milk_tea_6,
                getString(R.string.category_specialty)));

        return list;
    }
}