package com.example.droid.data.dal;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.droid.config.Constants;

import java.lang.reflect.Type;


public  class PreferenceManager {
    private static PreferenceManager preferenceManager;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private static Gson GSON;

    Type typeOfObject = new TypeToken<Object>() {
    }.getType();

    private PreferenceManager(Context context, String namePreferences, int mode) {

        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = Constants.SHOP_APPMOVER_STORAGE_PREFERENCE;
        }

        GSON = new GsonBuilder().create();
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static PreferenceManager getPreferenceManager(Context context, String namePreferences, int mode) {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager(context, namePreferences, mode);
        }
        return preferenceManager;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }

        editor.putString(key, GSON.toJson(object));
    }

    public void commit() {
        editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key " + key + " is instance of other class");
            }
        }
    }

    public void removeObject(String key)
    {
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.remove(key);
    }
}