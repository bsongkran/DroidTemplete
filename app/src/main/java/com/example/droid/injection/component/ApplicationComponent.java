package com.example.droid.injection.component;

import com.example.droid.MainApplication;
import com.example.droid.view.activity.GithubActivity;
import com.example.droid.viewmodel.gallery.GalleryViewModel;
import com.example.droid.injection.module.ApplicationModule;
import com.example.droid.injection.module.RepositoryModule;
import com.example.droid.injection.module.ServiceModule;
import com.example.droid.view.activity.LoginActivity;
import com.example.droid.view.activity.MainActivity;
import com.example.droid.view.activity.SplashActivity;
import com.example.droid.view.fragment.GalleryFragment;
import com.example.droid.view.fragment.MapFragment;
import com.example.droid.viewmodel.github.GithubViewModel;

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
    void inject(MainActivity mainActivity);
    void inject(GithubActivity githubActivity);



    //Inject Fragments
    void inject(GalleryFragment galleryFragment);
    void inject(MapFragment mapFragment);



}