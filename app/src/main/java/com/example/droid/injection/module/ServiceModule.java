package com.example.droid.injection.module;

/**
 * Created by ss on 7/20/2016.
 */

import android.app.Application;
import android.content.Context;

import com.example.droid.dal.IAppUserRepository;
import com.example.droid.service.IUserService;
import com.example.droid.service.UserService;
import com.example.droid.service.api.IRestApiClient;
import com.example.droid.service.api.RestApiClient;
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
    public IRestApiClient provideRestApiClient(){
        return new RestApiClient(provideApplicationContext());
    }

    @Provides
    @Singleton
    public IFusedLocationService provideFusedLocationService (){
        return new FusedLocationService(provideApplicationContext());
    }

    @Provides
    @Singleton
    public IUserService provideUserService(IAppUserRepository appUserRepository){
        return new UserService(provideApplicationContext() );
    }

}
