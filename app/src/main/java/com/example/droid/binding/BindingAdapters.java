package com.example.droid.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.droid.data.viewmodel.MainViewModel;
import com.example.droid.databinding.NavHeaderMainDrawerBinding;

import de.hdodenhof.circleimageview.CircleImageView;


@SuppressWarnings("unused")
public class BindingAdapters {


    @BindingAdapter({"bind:model"})
    public static void loadHeader(NavigationView view, MainViewModel model) {
        NavHeaderMainDrawerBinding binding = NavHeaderMainDrawerBinding.inflate(LayoutInflater.from(view.getContext()));
        binding.setMainViewModel(model);
        binding.executePendingBindings();
        view.addHeaderView(binding.getRoot());
        view.getHeaderView(0).setVisibility(View.GONE);
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(CircleImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable drawable) {
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(drawable).into(imageView);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }
}
