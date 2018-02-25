package com.asus.cloudmusic.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.asus.cloudmusic.Constant.Text;

public class Shares_Util {
    private static SharedPreferences sharedPreferences;

    public static void storeInt(String key, int num, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, num);
        editor.apply();
    }

    public static void storeBool(String key, boolean bool, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    public static void storeString(String key, String st, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, st);
        editor.apply();
    }

    public static void storeFloat(String key, float ft, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, ft);
        editor.apply();
    }

    public static boolean getBool(String key, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static int getInt(String key, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static String getString(String key, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static float getFloat(String key, Context context) {
        sharedPreferences = context.getSharedPreferences(Text.sharedKey, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }
}
