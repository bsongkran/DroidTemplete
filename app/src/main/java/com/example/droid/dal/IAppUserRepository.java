package com.example.droid.dal;

import com.example.droid.model.user.AppUser;

/**
 * Created by ss on 7/20/2016.
 */
public interface IAppUserRepository {

    AppUser getAppUser();

    void saveAppUser(AppUser appUser);

    void deleteUser();

}
