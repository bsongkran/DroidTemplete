package com.example.droid.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.droid.MainApplication;
import com.example.droid.injection.component.ApplicationComponent;

/**
 * Created by ss on 8/8/2016.
 */
public class BaseFragment extends Fragment {

    private Context context;
    private ApplicationComponent component;

    protected ApplicationComponent getApplicationComponent(){
        return  this.component;
    }

    protected Context getContextBase(){
        return this.context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = MainApplication.get(getActivity().getApplicationContext()).getComponent();
        this.context = getActivity().getApplicationContext();
    }


}
