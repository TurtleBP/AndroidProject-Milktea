package com.pro.milkteaapp.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTextView, emailTextView, phoneTextView, addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        addressTextView = findViewById(R.id.addressTextView);

        User user = new User(1,
                getString(R.string.user_name),
                getString(R.string.user_email),
                getString(R.string.user_phone),
                getString(R.string.user_address));

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
        addressTextView.setText(user.getAddress());
    }
}