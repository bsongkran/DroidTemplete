package com.example.droid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.droid.R;
import com.example.droid.data.model.AppUser;
import com.example.droid.data.repository.AppUserRepository;
import com.facebook.FacebookSdk;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash);
        //NetworkUtil.generateFacebookHashKey(getApplicationContext());
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
        AppUser appUser = AppUserRepository.getAppUserRepository(getApplicationContext()).getAppUser();
        if (appUser != null && appUser.isLoggedIn()) {
            openMainDrawerScreen();
        } else {
            openLoginScreen();
        }
    }

}


