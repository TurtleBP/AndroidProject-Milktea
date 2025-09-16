package com.pro.milkteaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.milkteaapp.R;
import com.pro.milkteaapp.models.MilkTea;
import com.pro.milkteaapp.activity.ProductDetailActivity;
import com.pro.milkteaapp.utils.MoneyUtils;

import java.util.List;

public class MilkTeaAdapter extends RecyclerView.Adapter<MilkTeaAdapter.ViewHolder> {
    private Context context;
    private List<MilkTea> milkTeaList;

    public MilkTeaAdapter(Context context, List<MilkTea> milkTeaList) {
        this.context = context;
        this.milkTeaList = milkTeaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_milk_tea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MilkTea milkTea = milkTeaList.get(position);
        holder.name.setText(milkTea.getName());
        holder.price.setText(MoneyUtils.formatVnd(milkTea.getPrice()));

        Glide.with(context)
                .load(milkTea.getImageResource())
                .placeholder(R.drawable.ic_milk_tea)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("milkTea", milkTea);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return milkTeaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.milkTeaImage);
            name = itemView.findViewById(R.id.milkTeaName);
            price = itemView.findViewById(R.id.milkTeaPrice);
        }
    }
}