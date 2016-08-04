package com.example.droid.service;

import android.content.Context;

import com.example.droid.MainApplication;
import com.example.droid.model.user.AppUser;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

/**
 * Created by ss on 8/4/2016.
 */
public class UserService implements  IUserService{

    private static final String TAG = "UserService";

    private Context context;

    @Inject
    public UserService(Context context){
        this.context = context;
    }

    @Override
    public void getUser() {
//        MainApplication.get(context).disconnectFromFacebook();
//        AppUser appUser = appUserRepository.getAppUser();
//        appUser.setLoggedIn(false);
//        appUserRepository.saveAppUser(appUser);
    }

    @Override
    public void saveUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void logout() {

    }

    @Override
    public void disconnectFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }
}
