package com.example.droid;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.example.droid.injection.component.ApplicationComponent;

import com.example.droid.injection.component.DaggerApplicationComponent;
import com.example.droid.injection.module.ApplicationModule;
import com.example.droid.injection.module.RepositoryModule;
import com.example.droid.injection.module.ServiceModule;
import com.facebook.FacebookSdk;

import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private ApplicationComponent applicationComponent;
    private Thread.UncaughtExceptionHandler androidDefaultUEH;
    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "Uncaught exception is: ", ex);
            Crashlytics.logException(ex);
            androidDefaultUEH.uncaughtException(thread, ex);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        /** UncaughtExceptionHandler*/
        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        setupDaggerInjection();

        FacebookSdk.sdkInitialize(getApplicationContext());

        setupFontLibrary();

        JodaTimeAndroid.init(this);
    }

    public static MainApplication get(@NonNull Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    private void setupFontLibrary() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setupDaggerInjection() {
        //Dagger2 Inject
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .serviceModule(new ServiceModule(getApplicationContext()))
                .repositoryModule(new RepositoryModule(getApplicationContext()))
                .build();
        applicationComponent.injectApplication(this);
    }


}