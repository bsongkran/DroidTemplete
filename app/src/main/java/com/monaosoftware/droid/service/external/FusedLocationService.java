package com.monaosoftware.droid.service.external;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class FusedLocationService implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private static final String TAG = "FusedLocationService";
    private static final long INTERVAL = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;
    private static final float MINIMUM_ACCURACY = 50.0f;
    private static FusedLocationService instance;
    private Context context;
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private boolean isAppInBackground;
    private Activity activity;

    public FusedLocationService(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public static FusedLocationService getFusedLocationService(Context context, Activity activity)
    {
        if (instance == null)
        {
            instance = new FusedLocationService(context, activity);
            instance.initialGoogleApiClient();
            instance.initialLocationServices();
        }
        return instance;
    }

    private void initialLocationServices()
    {
        if (checkPermissionForAccessFineLocation())
        {
            instance.fusedLocationProviderApi = LocationServices.FusedLocationApi;
        }
    }

    public void initialGoogleApiClient()
    {
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        if (mGoogleApiClient != null)
        {
            mGoogleApiClient.connect();
            createLocationRequest();
        }
    }

    public void createLocationRequest()
    {
        mLocationRequest = LocationRequest.create();
        if (checkPermissionForAccessFineLocation())
        {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        // mLocationRequest.setExpirationDuration(ONE_MIN);
        mLocationRequest.setSmallestDisplacement(MINIMUM_ACCURACY);
    }

    public void requestConnect()
    {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    public void requestDisconnect()
    {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    public boolean isAppInbackground()
    {
        return isAppInBackground;
    }

    public void setAppInbackground(boolean isAppInbackground)
    {
        instance.isAppInBackground = isAppInbackground;
    }

    public void stopLocationUpdates()
    {
        if (fusedLocationProviderApi != null)
            fusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        if(fusedLocationProviderApi != null){
            Location currentLocation = fusedLocationProviderApi.getLastLocation(mGoogleApiClient);
            if (currentLocation != null && currentLocation.getTime() > REFRESH_TIME)
            {
                mCurrentLocation = currentLocation;
            }
            else
            {
                if(mGoogleApiClient != null && mLocationRequest != null){
                    fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    // Schedule a Thread to unregister location listeners
                    Executors.newScheduledThreadPool(1).schedule(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            fusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, FusedLocationService.this);
                        }
                    }, ONE_MIN, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        // if the existing location is empty or
        // the current location accuracy is greater than existing accuracy
        // then store the current location
        if (null == this.mCurrentLocation || location.getAccuracy() < this.mCurrentLocation.getAccuracy())
        {
            this.mCurrentLocation = location;
            // let's inform my client class through the receiver

            // if the accuracy is not better, remove all location updates for
            // this listener
            if (this.mCurrentLocation.getAccuracy() < MINIMUM_ACCURACY)
            {
                fusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    public Location getLocation()
    {
        return this.mCurrentLocation;
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }

    private boolean checkPermissionForAccessFineLocation(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

}