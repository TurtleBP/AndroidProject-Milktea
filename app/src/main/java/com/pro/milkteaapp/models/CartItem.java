package com.pro.milkteaapp.models;

public class CartItem {
    private MilkTea milkTea;
    private int quantity;

    public CartItem(MilkTea milkTea, int quantity) {
        this.milkTea = milkTea;
        this.quantity = quantity;
    }

    public MilkTea getMilkTea() { return milkTea; }
    public int getQuantity() { return quantity; }

    public void setMilkTea(MilkTea milkTea) { this.milkTea = milkTea; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() {
        return milkTea.getPrice() * quantity;
    }
}