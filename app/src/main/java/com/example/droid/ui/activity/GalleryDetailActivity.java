package com.example.droid.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.example.droid.R;
import com.example.droid.data.viewmodel.gallery.GalleryDetailViewModel;
import com.example.droid.databinding.ActivityGalleryDetailBinding;

public class GalleryDetailActivity extends BaseActivity {
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    ActivityGalleryDetailBinding binding;
    GalleryDetailViewModel galleryDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_gallery_detail);

        Bundle extras =  getIntent().getExtras();
        if(extras != null){
            String imageUrl = extras.getString(EXTRA_IMAGE_URL);
            String name = extras.getString(EXTRA_NAME);
            galleryDetailViewModel = new GalleryDetailViewModel(getApplicationContext() , name , imageUrl );
        }
        binding.setViewModel(galleryDetailViewModel);
        binding.progressLoadGallery.setVisibility(View.VISIBLE);

        setupToolbar();

    }

    private void setupToolbar(){
        binding.toolbar.setTitle(galleryDetailViewModel.getName());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



}
