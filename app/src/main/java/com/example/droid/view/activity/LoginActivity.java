package com.example.droid.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.example.droid.R;
import com.example.droid.config.Constants;
import com.example.droid.data.model.AppUser;
import com.example.droid.data.repository.AppUserRepository;
import com.example.droid.data.viewmodel.login.LoginViewModel;
import com.example.droid.databinding.ActivityLoginBinding;

import org.json.JSONObject;

import java.util.Arrays;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginActivity extends AppCompatActivity implements LoginViewModel.LoginDataListener {

    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private AppUserRepository appUserRepository;
    private CallbackManager callbackManager;
    private ACProgressFlower acProgressFlower;

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            if (loginResult != null) {
                if (Constants.ALLOW_TO_LOG) {
                    Log.i("Facebook ApplicationId", loginResult.getAccessToken().getApplicationId());
                }
                if(acProgressFlower != null){
                    acProgressFlower.dismiss();
                }

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {
                                if (Constants.ALLOW_TO_LOG) Log.d(TAG, response.toString());

                                String facebookId = user.optString("id");
                                String email = user.optString("email");
                                String name = user.optString("name");
                                AppUser appUser = appUserRepository.getAppUser();
                                if (appUser == null) appUser = new AppUser();
                                appUser.setUsername(name);
                                appUser.setFacebookId(facebookId);
                                appUser.setEmail(email);
                                appUser.setLoggedIn(true);
                                appUserRepository.saveAppUser(appUser);

                                openMainScreen();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }
        }

        @Override
        public void onCancel() {
            if (Constants.ALLOW_TO_LOG) {
                Log.i(TAG, "Facebook Cancel Login");
            }
        }

        @Override
        public void onError(FacebookException error) {
            if (Constants.ALLOW_TO_LOG) {
                Log.e(TAG, error.getMessage());
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

        this.callbackManager = CallbackManager.Factory.create();
        this.binding.btnFacebookLogin.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        this.binding.btnFacebookLogin.registerCallback(callbackManager, facebookCallback);
        this.binding.btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acProgressFlower = new ACProgressFlower.Builder(LoginActivity.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.DKGRAY).build();
                acProgressFlower.show();
            }
        });

        AppUser appUser = appUserRepository.getAppUser();
        if (appUser == null) {
            appUser = new AppUser();
        }
        this.loginViewModel = new LoginViewModel(this, appUser, appUserRepository);
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
