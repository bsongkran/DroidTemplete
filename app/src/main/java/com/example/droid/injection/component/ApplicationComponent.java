package com.example.droid.injection.component;

import com.example.droid.MainApplication;
import com.example.droid.data.viewmodel.gallery.GalleryViewModel;
import com.example.droid.injection.module.ApplicationModule;
import com.example.droid.injection.module.RepositoryModule;
import com.example.droid.injection.module.ServiceModule;
import com.example.droid.ui.activity.LoginActivity;
import com.example.droid.ui.activity.MainActivity;
import com.example.droid.ui.activity.MainDrawerActivity;
import com.example.droid.ui.activity.SplashActivity;
import com.example.droid.ui.fragment.GalleryFragment;
import com.example.droid.ui.fragment.MapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class , ServiceModule.class , RepositoryModule.class})
public interface ApplicationComponent {
    //Inject Main Application
    void injectApplication(MainApplication application);

    //Inject Activities
    void inject(SplashActivity splashActivity);
    void inject(LoginActivity loginActivity);
    void inject(MainDrawerActivity mainDrawerActivity);
    void inject(MainActivity mainActivity);


    //Inject Fragments
    void inject(GalleryFragment galleryFragment);
    void inject(MapFragment mapFragment);


    //Inject ViewModel
    void inject(GalleryViewModel galleryViewModels);

}