package com.monaosoftware.droid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.monaosoftware.droid.R;
import com.monaosoftware.droid.config.Constants;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private long delay_time;
    private long time = 3000L;

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
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isLoggedInWithFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void openLoginScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_SCREEN_DELAY_TIME);
    }

    private void openMainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_SCREEN_DELAY_TIME);
    }

    private void openMainDrawerScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainDrawerActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_SCREEN_DELAY_TIME);
    }

    private void checkingLoggedIn(){
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if(isLoggedInWithFacebook()){
                    openMainDrawerScreen();
                }else{
                    openLoginScreen();
                }
            }
        };
        handler.postDelayed(runnable , 1000);
    }

}


