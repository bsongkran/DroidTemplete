package com.example.droid.service.api;

import rx.Scheduler;

/**
 * Created by ss on 7/20/2016.
 */
public interface IRestApiClient {

    void initializeApi();

    IRestApiEndPoints getRestApiEndPoints();

    Scheduler defaultSubscribeScheduler();


}
