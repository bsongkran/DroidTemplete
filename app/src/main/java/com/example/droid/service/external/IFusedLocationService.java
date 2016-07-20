package com.example.droid.service.external;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

/**
 * Created by ss on 7/20/2016.
 */

public interface IFusedLocationService extends LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    void initialService();

    void requestDisconnect();

    void requestConnect();

    Location getLocation();

}
