package com.monaosoftware.droid.data.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


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

    public MainViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;
        this.infoMessageVisibility = new ObservableInt(View.VISIBLE);
        this.toolbarTitle = new ObservableField<>("DroidTemplate");
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
