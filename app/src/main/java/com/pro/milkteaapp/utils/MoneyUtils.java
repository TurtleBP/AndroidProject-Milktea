package com.pro.milkteaapp.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtils {
    public static String formatVnd(double price) {
        try {
            long vndPrice = (long) (price * 1000);
            NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
            return format.format(vndPrice) + "đ";
        } catch (Exception e) {
            return String.valueOf((int)(price * 1000)) + "đ";
        }
    }
}