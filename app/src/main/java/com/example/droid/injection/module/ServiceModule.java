package com.example.droid.injection.module;

/**
 * Created by ss on 7/20/2016.
 */

import android.content.Context;

import com.example.droid.service.api.IRestApiClient;
import com.example.droid.service.api.RestApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule  {

    private final Context context;

    public ServiceModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public IRestApiClient provideRestApiClient(){
        return new RestApiClient(context);
    }

}
