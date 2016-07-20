package com.example.droid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.droid.MainApplication;
import com.example.droid.R;
import com.example.droid.data.dal.IAppUserRepository;
import com.example.droid.data.model.user.AppUser;
import com.facebook.FacebookSdk;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {


    @Inject
    IAppUserRepository appUserRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Inject
        MainApplication.get(getApplicationContext()).getComponent().inject(this);
        setContentView(R.layout.activity_splash);

        checkingLoggedIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void openLoginScreen() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMainScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMainDrawerScreen() {
        Intent intent = new Intent(SplashActivity.this, MainDrawerActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkingLoggedIn() {
        AppUser appUser = appUserRepository.getAppUser();
        if (appUser != null && appUser.isLoggedIn()) {
            openMainDrawerScreen();
        } else {
            openLoginScreen();
        }
    }

}


