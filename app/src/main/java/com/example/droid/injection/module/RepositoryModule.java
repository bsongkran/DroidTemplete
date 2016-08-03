package com.example.droid.injection.module;

/**
 * Created by ss on 7/20/2016.
 */

import android.content.Context;

import com.example.droid.dal.IAppUserRepository;
import com.example.droid.dal.repositories.AppUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private final Context context;

    public RepositoryModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public IAppUserRepository provideAppUserRepository(){
        return new AppUserRepository(context);
    }


}
