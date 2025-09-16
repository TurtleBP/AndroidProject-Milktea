package com.pro.milkteaapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.MilkTea;
import com.pro.milkteaapp.utils.MoneyUtils;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView milkTeaImage;
    private TextView nameTextView, priceTextView, descriptionTextView;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        milkTeaImage = findViewById(R.id.milkTeaImage);
        nameTextView = findViewById(R.id.nameTextView);
        priceTextView = findViewById(R.id.priceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        addToCartButton = findViewById(R.id.addToCartButton);

        MilkTea milkTea = (MilkTea) getIntent().getSerializableExtra("milkTea");

        if (milkTea != null) {
            nameTextView.setText(milkTea.getName());
            priceTextView.setText(MoneyUtils.formatVnd(milkTea.getPrice()));
            descriptionTextView.setText(milkTea.getDescription());

            Glide.with(this)
                    .load(milkTea.getImageResource())
                    .placeholder(R.drawable.ic_milk_tea)
                    .into(milkTeaImage);

            addToCartButton.setOnClickListener(v -> {
                Toast.makeText(this,
                        milkTea.getName() + " " + getString(R.string.added_to_cart),
                        Toast.LENGTH_SHORT).show();
            });
        }
    }
}