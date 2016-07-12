package com.monaosoftware.droid.data.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.monaosoftware.droid.BR;
import com.monaosoftware.droid.service.api.ApiClient;
import com.monaosoftware.droid.data.model.AppUser;
import com.monaosoftware.droid.data.model.Repository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by ss on 6/30/2016.
 */
public class LoginViewModel extends BaseObservable implements BaseViewModel {

    //Event return back to View Activity
    public interface LoginDataListener {
        void onLoginCompleted();
    }

    private Context context;
    private Subscription subscription;
    private LoginDataListener loginDataListener;
    private AppUser appUser;

    private String username;
    private String password;

    public LoginViewModel(Context context, AppUser appUser , LoginDataListener loginDataListener) {
        this.context = context;
        this.loginDataListener = loginDataListener;
        this.appUser = appUser;
        //Load data
        initializeBindingData(appUser);
    }

    //Bind data after load view
    private void initializeBindingData(AppUser appUser) {
       setUsername(appUser.getUsername());
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @SuppressWarnings("unchecked")
    public void onLoginClick(@NonNull final View view) {
        if(this.getUsername() != null && !this.getUsername().isEmpty()
                 && this.getPassword() != null &&  !this.getPassword().isEmpty() && this.getPassword().equalsIgnoreCase("1234")){
            loginDataListener.onLoginCompleted();
        }else{
            Toast.makeText(context , "Username or Password is not correct" , Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginWithFacebookClik(@NonNull final View view){

    }

    public void login(){
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        ApiClient apiClient = ApiClient.getInstance(context);
        subscription = apiClient.getService().publicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(apiClient.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        //Add OnDataChanged
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.print(error);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        System.out.println(repositories);
                    }
                });
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
        loginDataListener = null;
    }



}
