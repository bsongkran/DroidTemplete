package com.example.droid.injection.module;

import android.app.Application;
import android.content.Context;


import com.example.droid.service.api.IRestApiClient;
import com.example.droid.viewmodel.gallery.GalleryViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {


    private final  Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }




}