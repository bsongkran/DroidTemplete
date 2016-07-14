package com.example.droid.service.api;

import com.example.droid.data.model.AppUser;
import com.example.droid.data.model.PrettyModel;
import com.example.droid.data.model.Repository;
import com.example.droid.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ss on 2/5/2016.
 */
public interface ApiEndPointsService {

    /*Login*/
    @GET("Api/AppUserApi/LoginByEmailAndPasswordForShop")
    Call<AppUser> login(
            @Query("userName") String username,
            @Query("password") String password,
            @Query("deviceId") String deviceId,
            @Query("deviceOS") String deviceOS,
            @Query("deviceToken") String deviceToken
    );


    @GET("users/{username}/repos")
    Observable<List<Repository>> publicRepositories(@Path("username") String username);

    @GET
    Observable<User> userFromUrl(@Url String userUrl);

    @GET
    Observable<PrettyModel> getPrettyData(@Url String url);

}
