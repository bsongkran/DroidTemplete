package com.example.droid.dal.repositories;

import android.content.Context;
import android.util.Log;

import com.example.droid.config.Constants;
import com.example.droid.dal.IAppUserRepository;
import com.example.droid.dal.PreferenceManager;
import com.example.droid.model.user.AppUser;

import javax.inject.Inject;


/**
 * Created by js on 7/20/2015.
 */
public class AppUserRepository implements IAppUserRepository {

    private String TAG = "AppUserRepository";
    private Context context;
    private static final String AppUserKey = "AppUser";
    private PreferenceManager mPreferenceManager;

    @Inject
    public AppUserRepository(Context context) {
        this.context = context;
        this.mPreferenceManager = PreferenceManager.getPreferenceManager(context, Constants.SHOP_APPMOVER_STORAGE_PREFERENCE, Context.MODE_PRIVATE);
    }

    public AppUser getAppUser() {
        try {
            return mPreferenceManager.getObject(AppUserKey, AppUser.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveAppUser(AppUser appUser) {
        if (mPreferenceManager != null) {
            mPreferenceManager.putObject(AppUserKey, appUser);
            mPreferenceManager.commit();
        } else {
            Log.d(TAG, "Preferences is null");
        }
    }

    @Override
    public void deleteUser() {
        if (mPreferenceManager != null) {
            mPreferenceManager.removeObject(AppUserKey);
            mPreferenceManager.commit();
        } else {
            Log.d(TAG, "Preferences is null");
        }
    }



}
