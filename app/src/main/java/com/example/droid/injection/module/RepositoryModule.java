package com.example.droid.injection.module;

/**
 * Created by ss on 7/20/2016.
 */

import android.app.Application;
import android.content.Context;

import com.example.droid.dal.IAppUserRepository;
import com.example.droid.dal.repositories.AppUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule extends ApplicationModule {

    public RepositoryModule(Application application) {
        super(application);
    }

    @Provides
    @Singleton
    public IAppUserRepository provideAppUserRepository(){
        return new AppUserRepository(provideApplicationContext());
    }


}
