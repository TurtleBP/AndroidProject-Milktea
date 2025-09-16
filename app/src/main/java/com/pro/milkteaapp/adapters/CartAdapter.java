package com.pro.milkteaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.CartItem;
import com.pro.milkteaapp.utils.MoneyUtils;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.name.setText(cartItem.getMilkTea().getName());
        holder.price.setText(MoneyUtils.formatVnd(cartItem.getMilkTea().getPrice()));
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.total.setText(MoneyUtils.formatVnd(cartItem.getTotalPrice()));

        Glide.with(context)
                .load(cartItem.getMilkTea().getImageResource())
                .placeholder(R.drawable.ic_milk_tea)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, quantity, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartItemImage);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartItemPrice);
            quantity = itemView.findViewById(R.id.cartItemQuantity);
            total = itemView.findViewById(R.id.cartItemTotal);
        }
    }
}