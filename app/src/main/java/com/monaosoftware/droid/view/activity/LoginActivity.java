package com.monaosoftware.droid.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.monaosoftware.droid.R;
import com.monaosoftware.droid.config.Constants;
import com.monaosoftware.droid.data.model.AppUser;
import com.monaosoftware.droid.data.repository.AppUserRepository;
import com.monaosoftware.droid.data.viewmodel.LoginViewModel;
import com.monaosoftware.droid.databinding.ActivityLoginBinding;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginViewModel.LoginDataListener {

    private static  final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private AppUserRepository appUserRepository;
    private CallbackManager callbackManager;

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
             if(loginResult != null){
                 if(Constants.ALLOW_TO_LOG){
                     Log.i("Facebook ApplicationId" , loginResult.getAccessToken().getApplicationId() );
                 }

                 //Check ApplicationId equals Appplication id preference
                 openMainScreen();
             }
        }

        @Override
        public void onCancel() {
            if(Constants.ALLOW_TO_LOG){
                Log.i(TAG , "Facebook Cancel Login");
            }
        }

        @Override
        public void onError(FacebookException error) {
            if(Constants.ALLOW_TO_LOG){
                Log.e(TAG , error.getMessage());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Facebook login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);
        this.appUserRepository = AppUserRepository.getAppUserRepository(getApplicationContext());
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        binding.btnFacebookLogin.setReadPermissions( Arrays.asList("public_profile", "user_friends"));
        binding.btnFacebookLogin.registerCallback(callbackManager, facebookCallback);
       // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));

        AppUser appUser = new AppUser();
        appUser.setUsername("Admin");

        this.loginViewModel = new LoginViewModel(this, appUser, this);
        this.binding.setLoginViewModel(loginViewModel);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.destroy();
    }

    @Override
    public void onLoginCompleted() {
        openMainScreen();
    }

    private void openGitHubScreen() {
        Intent intent = new Intent(this, GithubActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void openMainScreen() {
        Intent intent = new Intent(this, MainDrawerActivity.class);
        startActivity(intent);
        this.finish();
    }

}
