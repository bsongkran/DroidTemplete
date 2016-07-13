package com.example.droid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private static MainApplication singleton;
    private Thread.UncaughtExceptionHandler androidDefaultUEH;

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "Uncaught exception is: ", ex);
            Crashlytics.logException(ex);
            androidDefaultUEH.uncaughtException(thread, ex);
        }
    };


    /**
     * @return App singleton instance
     */
    public static synchronized MainApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        /** UncaughtExceptionHandler*/
        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setupFontLibrary();
        JodaTimeAndroid.init(this);
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }
    
    private void setupFontLibrary() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }


}