package com.malin.order_backend.utils;


public class MealCodeUtil {
    private static int mealCode = 0;

    public static String getMealCode() {
        mealCode++;
        if (mealCode > 9999) mealCode = 1;
        String str = String.format("%04d",mealCode);
        return str;
    }
}
