package com.example.droid.injection.component;

import com.example.droid.injection.module.ActivityModule;
import com.example.droid.view.activity.LoginActivity;
import com.example.droid.view.activity.MainActivity;
import com.example.droid.view.activity.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ss on 8/4/2016.
 */
@Singleton
@Component(modules = {ActivityModule.class })
public interface ActivityComponent extends  ApplicationComponent {

    //Inject Activities
    void inject(SplashActivity splashActivity);
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);

}
