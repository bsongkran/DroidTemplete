package com.example.droid.view.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.droid.MainApplication;
import com.example.droid.injection.component.ApplicationComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ss on 7/1/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private ApplicationComponent component;

    protected ApplicationComponent getApplicationComponent(){
        return  this.component;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = MainApplication.get(getApplicationContext()).getComponent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
