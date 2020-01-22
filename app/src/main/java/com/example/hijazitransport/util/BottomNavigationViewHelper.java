package com.example.hijazitransport.util;

import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

        } catch (NoSuchFieldException e) {
            Log.e("BottomNavigationHelper", "Unable to get shift mode field" + e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNavigationHelper", "Unable to change value of shift mode" + e);
        }
    }
}
