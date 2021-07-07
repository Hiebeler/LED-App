package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class PrefConfig {

    public static void writeListInPref(Context context, List<String> list, String key) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, jsonString);
        editor.apply();
    }

    public static List<String> readListFromPref (Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(key, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> list = gson.fromJson(jsonString, type);
        return list;
    }

}
