package com.example.droid.service.api;

import rx.Scheduler;

/**
 * Created by ss on 7/20/2016.
 */
public interface RestApiClient {

    void initializeApi();

    RestApiEndPoints getRestApiEndPoints();

    Scheduler defaultSubscribeScheduler();


}
