package com.monaosoftware.droid.data.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;

import com.monaosoftware.droid.BR;
import com.monaosoftware.droid.databinding.NavHeaderMainDrawerBinding;

/**
 * Created by ss on 7/6/2016.
 */
public class UserViewModel extends BaseObservable  implements BaseViewModel {

    private Context context;

    private String email;

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public UserViewModel(Context context){
        this.context = context;
        setEmail("bsongkran@hotmail.com");
    }

    @Override
    public void destroy() {

    }
}
