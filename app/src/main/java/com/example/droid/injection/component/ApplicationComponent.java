package com.example.droid.injection.component;

import com.example.droid.MainApplication;
import com.example.droid.view.activities.GithubActivity;
import com.example.droid.injection.module.ApplicationModule;
import com.example.droid.injection.module.RepositoryModule;
import com.example.droid.injection.module.ServiceModule;
import com.example.droid.view.activities.LoginActivity;
import com.example.droid.view.activities.MainActivity;
import com.example.droid.view.activities.SplashActivity;
import com.example.droid.view.fragments.GalleryFragment;
import com.example.droid.view.fragments.MapFragment;

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