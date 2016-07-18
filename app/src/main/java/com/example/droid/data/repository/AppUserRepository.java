package com.example.droid.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.droid.config.Constants;
import com.example.droid.data.model.AppUser;


/**
 * Created by js on 7/20/2015.
 */
public class AppUserRepository {

    private String TAG = "AppUserRepository";
    private Context context;
    private static AppUserRepository instant;
    private static final String AppUserKey = "AppUser";
    private PreferenceManager mPreferenceManager;

    public AppUserRepository(Context context) {
        this.context = context;
        this.mPreferenceManager = PreferenceManager.getPreferenceManager(context, Constants.SHOP_APPMOVER_STORAGE_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static AppUserRepository getAppUserRepository(Context context) {
        if (instant == null) {
            instant = new AppUserRepository(context);
        }
        return instant;
    }

    public AppUser getAppUser() {
        try {
            return mPreferenceManager.getObject(AppUserKey, AppUser.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveAppUser(AppUser appUser) {
        if (mPreferenceManager != null) {
            mPreferenceManager.putObject(AppUserKey, appUser);
            mPreferenceManager.commit();
        } else {
            Log.d(TAG, "Preferences is null");
        }
    }

    public void deleteAppUser() {
        if (mPreferenceManager != null) {
            mPreferenceManager.removeObject(AppUserKey);
            mPreferenceManager.commit();
        } else {
            Log.d(TAG, "Preferences is null");
        }
    }

}
