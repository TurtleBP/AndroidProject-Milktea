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
    private OnItemActionClickListener listener;

    public interface OnItemActionClickListener {
        void onItemRemoved(int position);
        void onItemEdited(int position);
        void onQuantityIncreased(int position);
        void onQuantityDecreased(int position);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnItemActionClickListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
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
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.size.setText("Size: " + cartItem.getSize());
        holder.topping.setText("Topping: " + cartItem.getTopping());
        holder.total.setText(MoneyUtils.formatVnd(cartItem.getTotalPrice()));

        Glide.with(context)
                .load(cartItem.getMilkTea().getImageResource())
                .placeholder(R.drawable.ic_milk_tea)
                .into(holder.image);

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemRemoved(holder.getAdapterPosition());
            }
        });

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemEdited(holder.getAdapterPosition());
            }
        });

        holder.incrementButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityIncreased(holder.getAdapterPosition());
            }
        });

        holder.decrementButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityDecreased(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, deleteButton, editButton, incrementButton, decrementButton;
        TextView name, quantity, total, size, topping;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartItemImage);
            name = itemView.findViewById(R.id.cartItemName);
            quantity = itemView.findViewById(R.id.cartItemQuantity);
            total = itemView.findViewById(R.id.cartItemTotal);
            size = itemView.findViewById(R.id.cartItemSize);
            topping = itemView.findViewById(R.id.cartItemTopping);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
        }
    }
}