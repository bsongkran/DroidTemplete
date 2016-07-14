package com.example.droid.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.droid.R;
import com.example.droid.data.model.PrettyModel;
import com.example.droid.data.model.Repository;
import com.example.droid.data.viewmodel.gallery.ItemGridViewModel;
import com.example.droid.data.viewmodel.github.ItemRepoViewModel;
import com.example.droid.databinding.ItemRepoBinding;

import java.util.List;

/**
 * Created by ss on 7/13/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<PrettyModel> prettyModelList;

    public GalleryAdapter(Context context){
        this.context = context;
    }

    public void setPrettyModelList(List<PrettyModel> prettyModelList){
        this.prettyModelList = prettyModelList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGridViewModel binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_gallery, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemGridViewModel binding;

        public ViewHolder(ItemGridViewModel binding) {
            super(binding.);
            this.binding = binding;
        }



    }

}
