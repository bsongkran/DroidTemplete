package com.example.droid.data.viewmodel.gallery;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.droid.BR;


/**
 * Created by ss on 7/14/2016.
 */
public class GalleryDetailViewModel extends BaseObservable {

    private Context context;
    private String imageUrl;
    private String name;
    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    public GalleryDetailViewModel(Context context , String name , String imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
        this.name = name;
        updateView();
    }

    private void updateView(){
        this.setImageUrl(imageUrl);
        this.setName(name);
    }


}
