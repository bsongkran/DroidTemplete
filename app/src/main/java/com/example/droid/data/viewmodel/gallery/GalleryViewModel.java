package com.example.droid.data.viewmodel.gallery;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;

import com.example.droid.data.model.Pretty;
import com.example.droid.data.model.Repository;
import com.example.droid.data.model.User;
import com.example.droid.data.viewmodel.BaseViewModel;
import com.example.droid.service.api.ApiClient;
import com.example.droid.view.adapter.GalleryRecycleAdapter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ss on 7/13/2016.
 */
public class GalleryViewModel extends BaseObservable implements BaseViewModel {

    private static final String TAG = "GalleryViewModel";
    private Context context;
    private Subscription subscription;
    private IDataChangedListener dataChagedListener;

    public interface IDataChangedListener {
        void onDataChanged(Pretty pretty);
    }

    private Pretty pretty;

    public GalleryViewModel(Context context, IDataChangedListener dataChangedListener) {
        this.context = context;
        this.dataChagedListener = dataChangedListener;
        loadPrettyData();
    }

    @Override
    public void destroy() {
        this.context = null;
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
    }


    private void loadPrettyData() {

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        ApiClient apiClient = ApiClient.getInstance(context);
        String url = "https://gank.io/api/data/福利/213/1";
        subscription = apiClient.getService().getPrettyData(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(apiClient.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Pretty>() {
                    @Override
                    public void onCompleted() {
                        if (dataChagedListener != null) dataChagedListener.onDataChanged(pretty);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Pretty pretty) {
                        GalleryViewModel.this.pretty = pretty;
                    }
                });
    }


}
