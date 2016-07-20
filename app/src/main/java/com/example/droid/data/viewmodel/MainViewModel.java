package com.example.droid.data.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.example.droid.BR;
import com.example.droid.data.dal.IAppUserRepository;
import com.example.droid.data.model.user.AppUser;
import com.example.droid.data.dal.repositories.AppUserRepository;

import rx.Subscription;


/**
 * View model for the MainActivity
 */
public class MainViewModel extends BaseObservable implements BaseViewModel {

    private static final String TAG = "MainViewModel";

    public ObservableInt infoMessageVisibility;
    public ObservableField<String> toolbarTitle;

    private Context context;
    private Subscription subscription;
    private DataListener dataListener;
    private String imageUrl;
    private String username;
    private String email;
    private AppUser appUser;


    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }


    public MainViewModel(Context context, DataListener dataListener , IAppUserRepository appUserRepository) {
        this.context = context;
        this.dataListener = dataListener;
        this.infoMessageVisibility = new ObservableInt(View.VISIBLE);
        this.toolbarTitle = new ObservableField<>("DroidTemplate");
        this.appUser = appUserRepository.getAppUser();

        updateView(appUser);
    }

    private void updateView(AppUser appUser){
        this.setEmail(appUser.getEmail());
        this.setUsername(appUser.getUsername());
        this.setImageUrl("https://graph.facebook.com/" + appUser.getFacebookId() + "/picture?type=normal");
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
        dataListener = null;
    }

    private void loadGithubRepos(String username) {

    }

    public interface DataListener {
        void onDataChanged();
    }





}
