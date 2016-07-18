package com.example.droid.data.viewmodel.gallery;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.droid.BR;
import com.example.droid.R;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by ss on 7/18/2016.
 */
public class GalleryCollectionViewModel {
    private Context context;
    public final ObservableList<ItemGridCollectionViewModel> items = new ObservableArrayList<>();
    public final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.item_gallery);
    public GalleryCollectionViewModel(Context context){
        this.context = context;

    }

}
