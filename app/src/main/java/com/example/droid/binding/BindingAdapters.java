package com.example.droid.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.droid.databinding.NavHeaderMainDrawerBinding;
import com.example.droid.viewmodel.main.MainViewModel;

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


    @BindingAdapter("bind:imageUrl")
    public static void loadImage(CircleImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter({"bind:imageUrl", "placeholder"})
    public static void loadImageWithPlaceHolder(ImageView imageView, String imageUrl, Drawable drawable) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter(value = {"android:src", "placeHolder"}, requireAll = false)
    public static void setImageUrl(ImageView view, String url, Drawable drawable) {
        Glide.with(view.getContext()).load(url).placeholder(drawable).into(view);
    }

    @BindingAdapter("android:onLayoutChange")
    public static void setOnLayoutChangeListener(View view,
                                                 View.OnLayoutChangeListener oldValue,
                                                 View.OnLayoutChangeListener newValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue);
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

}
