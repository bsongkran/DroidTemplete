package com.example.droid.data.viewmodel.gallery;

import android.content.Context;
import android.databinding.BaseObservable;

import com.example.droid.data.model.Pretty;
import com.example.droid.data.viewmodel.BaseViewModel;
import com.example.droid.service.api.IRestApiClient;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ss on 7/13/2016.
 */
public class GalleryViewModel extends BaseObservable implements BaseViewModel {

    private static final String TAG = "GalleryViewModel";
    private Context context;
    private Subscription subscription;
    private IDataChangedListener dataChagedListener;
    private IRestApiClient restApiClient;


    public interface IDataChangedListener {
        void onDataChanged(Pretty pretty);
    }

    private Pretty pretty;

    public GalleryViewModel(Context context, IDataChangedListener dataChangedListener , IRestApiClient restApiClient) {
        this.context = context;
        this.dataChagedListener = dataChangedListener;
        this.restApiClient = restApiClient;

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

        String url = "https://gank.io/api/data/福利/213/1";
        subscription = restApiClient.getRestApiEndPoints().getPrettyData(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(restApiClient.defaultSubscribeScheduler())
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
