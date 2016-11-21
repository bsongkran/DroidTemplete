package com.example.droid.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droid.R;
import com.example.droid.model.domains.user.Pretty;
import com.example.droid.model.viewmodels.gallery.ItemGridViewModel;
import com.example.droid.databinding.ItemGalleryBinding;

import java.util.Collections;
import java.util.List;

/**
 * Created by ss on 7/13/2016.
 */
public class GalleryRecycleAdapter extends RecyclerView.Adapter<GalleryRecycleAdapter.BindingViewHolder> {

    private Context context;
    private List<Pretty.ResultsEntity> resultsEntities;

    public GalleryRecycleAdapter(Context context) {
        this.context = context;
        this.resultsEntities = Collections.emptyList();
    }

    public void setPretty(Pretty pretty) {
        this.resultsEntities = pretty.getResults();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGalleryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_gallery, parent, false);
        return new BindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        Pretty.ResultsEntity resultsEntity = resultsEntities.get(position);
        holder.getBinding().setItemViewModel(new ItemGridViewModel(holder.getBinding().getRoot().getContext() , resultsEntity ));
    }

    @Override
    public int getItemCount() {
        if (resultsEntities == null) {
            return 0;
        }
        return resultsEntities.size();
    }


    public static class BindingViewHolder extends RecyclerView.ViewHolder {

        public BindingViewHolder(View itemView) {
            super(itemView);
        }

        public ItemGalleryBinding getBinding() {
            return DataBindingUtil.getBinding(itemView);
        }

    }

}
