package com.pro.milkteaapp.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private MilkTea milkTea;
    private int quantity;
    private String size;
    private String topping;
    private double totalPrice;

    public CartItem(MilkTea milkTea, int quantity, String size, String topping) {
        this.milkTea = milkTea;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
        this.calculateTotalPrice();
    }

    public MilkTea getMilkTea() { return milkTea; }
    public int getQuantity() { return quantity; }
    public String getSize() { return size; }
    public String getTopping() { return topping; }
    public double getTotalPrice() { return totalPrice; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.calculateTotalPrice();
    }

    public void setSize(String size) {
        this.size = size;
        this.calculateTotalPrice();
    }

    public void setTopping(String topping) {
        this.topping = topping;
        this.calculateTotalPrice();
    }

    public void addQuantity(int addedQuantity) {
        this.quantity += addedQuantity;
        this.calculateTotalPrice();
    }

    public void increaseQuantity() {
        this.quantity++;
        this.calculateTotalPrice();
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
            this.calculateTotalPrice();
        }
    }

    public double calculatePriceForOneItem() {
        double basePrice = milkTea.getPrice();
        double finalPrice = basePrice;

        // Tính giá tiền cho các tùy chọn
        double sizePrice = 0;
        double toppingPrice = 0;

        if (size != null) {
            if (size.equals("Vừa")) {
                sizePrice = basePrice * 0.1; // Giá tăng 5%
            } else if (size.equals("Lớn")) {
                sizePrice = basePrice * 0.2; // Giá tăng 10%
            }
        }

        if (topping != null && topping.equals("Trân châu trắng")) {
            toppingPrice = 5.0; // Thêm 5.000đ
        }

        // Cộng tất cả lại để có tổng giá cuối cùng cho một sản phẩm
        finalPrice = basePrice + sizePrice + toppingPrice;

        return finalPrice;
    }

    public void calculateTotalPrice() {
        this.totalPrice = calculatePriceForOneItem() * this.quantity;
    }
}