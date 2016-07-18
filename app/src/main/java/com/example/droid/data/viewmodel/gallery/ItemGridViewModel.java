package com.example.droid.data.viewmodel.gallery;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;


import com.example.droid.BR;
import com.example.droid.data.model.Pretty;
import com.example.droid.view.activity.GalleryDetailActivity;

/**
 * Created by ss on 7/13/2016.
 */
public class ItemGridViewModel extends BaseObservable  {

    private Context context;
    private Pretty.ResultsEntity resultsEntity;
    private String name;
    private String imageUrl;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    public ItemGridViewModel(Context context , Pretty.ResultsEntity resultsEntity  ){
        this.context = context;
        this.resultsEntity = resultsEntity;
        updateView();
    }

    private void updateView(){
        this.setName(resultsEntity.getWho());
        this.setImageUrl(resultsEntity.getUrl());
    }

    public void onItemClick(View view){
        Intent intent = new Intent(context , GalleryDetailActivity.class);
        intent.putExtra(GalleryDetailActivity.EXTRA_IMAGE_URL , imageUrl);
        intent.putExtra(GalleryDetailActivity.EXTRA_NAME , name);
        context.startActivity(intent);
    }



}
