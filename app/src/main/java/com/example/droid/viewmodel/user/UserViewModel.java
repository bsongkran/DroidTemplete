package com.example.droid.viewmodel.user;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.droid.BR;
import com.example.droid.viewmodel.IViewModel;

/**
 * Created by ss on 7/6/2016.
 */
public class UserViewModel extends BaseObservable  implements IViewModel {

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
