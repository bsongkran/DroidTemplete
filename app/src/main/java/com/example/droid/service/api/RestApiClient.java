package com.example.droid.service.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.example.droid.config.Constants;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;


public class RestApiClient implements IRestApiClient {

    private Map<String, String> headers = new HashMap<String, String>();
    private IRestApiEndPoints restApiEndPoints;
    private Context context;
    private Scheduler defaultSubscribeScheduler;

    /**
     * public constructor
     */
    public RestApiClient(Context context) {
        this.context = context;
        this.initializeApi();
    }

    /**
     * Add a header which is added to every API request
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Add multiple headers
     */
    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    /**
     * Remove a header
     */
    public void removeHeader(String key) {
        headers.remove(key);
    }

    /**
     * Remove all headers
     */
    public void clearHeaders() {
        headers.clear();
    }

    /**
     * Get all headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }


    @Override
    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

    @Override
    public void initializeApi() {
        // OkHttpClient V3.0
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));
        dispatcher.setMaxRequests(20);
        dispatcher.setMaxRequestsPerHost(1);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.dispatcher(dispatcher);
        httpBuilder.connectionPool(new ConnectionPool(100, 30, TimeUnit.SECONDS));
        httpBuilder.addInterceptor(logging);
        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                //builder.addHeader("Content-type", "application/json");
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
                return chain.proceed(builder.build());
            }
        });

        // create the OkHttpClient instance from httpBuilder
        OkHttpClient okHttpClient = httpBuilder.build();

        // Gson Date Format
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(BuildConfig.API_DOMAIN)
                .baseUrl(Constants.DOMAIN_GITHUB)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // Service setup
        restApiEndPoints = retrofit.create(IRestApiEndPoints.class);
    }

    @Override
    public IRestApiEndPoints getRestApiEndPoints() {
        return this.restApiEndPoints;
    }
}
