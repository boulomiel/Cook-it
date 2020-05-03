package com.rubenmimoun.cookit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

public class Preferences {

    private static Preferences yourPreference;
    private SharedPreferences sharedPreferences;

    public static Preferences getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new Preferences(context);
        }
        return yourPreference;
    }

    private Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void removeValue(String key){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        if(sharedPreferences.getString(key,"") != null )
        prefsEditor.remove(key).commit();
    }
}
