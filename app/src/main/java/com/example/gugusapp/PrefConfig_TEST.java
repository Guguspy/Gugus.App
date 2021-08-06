package com.example.gugusapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig_TEST {

    private static final String MY_PREFERENCE_NAME = "com.example.gugusapp";
    private static final String PREF_TOTAL = "pref_total_key";

    public static void saveTotalInPref(Context context, int total){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_TOTAL, total);
        editor.apply();
    }

    public static int loadTotalFromPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PREF_TOTAL, 0);
    }

    public static void removeDataFromPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREF_TOTAL);
        editor.apply();
    }

}
