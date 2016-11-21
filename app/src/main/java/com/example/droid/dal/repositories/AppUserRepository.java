package com.example.droid.dal.repositories;

import com.example.droid.model.domains.user.AppUser;

/**
 * Created by ss on 7/20/2016.
 */
public interface AppUserRepository {

    AppUser getAppUser();

    void saveAppUser(AppUser appUser);

    void deleteUser();

}
