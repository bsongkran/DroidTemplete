package com.example.droid.injection.module;

/**
 * Created by ss on 7/20/2016.
 */

import android.app.Application;

import com.example.droid.dal.repositories.AppUserRepository;
import com.example.droid.service.UserService;
import com.example.droid.service.UserServiceImpl;
import com.example.droid.service.api.RestApiClient;
import com.example.droid.service.api.RestApiClientImpl;
import com.example.droid.service.external.FusedLocationService;
import com.example.droid.service.external.IFusedLocationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule extends ApplicationModule {


    public ServiceModule(Application application) {
        super(application);
    }

    @Provides
    @Singleton
    public RestApiClient provideRestApiClient(){
        return new RestApiClientImpl(provideApplicationContext());
    }

    @Provides
    @Singleton
    public IFusedLocationService provideFusedLocationService (){
        return new FusedLocationService(provideApplicationContext());
    }

    @Provides
    @Singleton
    public UserService provideUserService(AppUserRepository appUserRepository){
        return new UserServiceImpl(provideApplicationContext() );
    }

}
