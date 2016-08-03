package com.example.droid.view.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.droid.MainApplication;
import com.example.droid.service.external.IFusedLocationService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.droid.R;
import com.example.droid.databinding.FragmentMapBinding;
import com.example.droid.util.MarshMallowPermission;
import com.example.droid.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapLoadedCallback {

    private Context context;
    private Location mCurrentLocation;
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    private LatLng currenLatLng;
    private FragmentMapBinding binding;
    @Inject
    IFusedLocationService fusedLocationService;

    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity().getApplicationContext();

        MainApplication.get(context).getComponent().inject(this);

        initializeLocationService();
    }

    private void initializeLocationService() {
        if (!MarshMallowPermission.getInstant(getActivity()).checkPermissionForAccessFineLocation()) {
            MarshMallowPermission.getInstant(getActivity()).requestPermissionForAccessFineLocation();
        }

        if (MarshMallowPermission.getInstant(getActivity()).checkPermissionForAccessFineLocation()) {
            fusedLocationService.initialService();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        View view = binding.getRoot();
        this.context = getActivity().getApplicationContext();
        setupMap();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationService != null) {
            fusedLocationService.requestDisconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setupMap();

        if (fusedLocationService != null) {
            fusedLocationService.requestConnect();
        }

        System.gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationService != null)
            fusedLocationService.requestDisconnect();
    }

    private void setupMap() {
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    private void getDirectionMenuItemClicked(final LatLng latLng) {
        if (NetworkUtil.haveNetworkConnectivity(context)) {
            if (mCurrentLocation != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Get Direction from current location");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url = "http://maps.google.com/maps?saddr=" + (currenLatLng != null ? currenLatLng.latitude : "") + "," + (currenLatLng != null ? currenLatLng.longitude : "")
                                + "&daddr=" + latLng.latitude + "," + latLng.longitude + "&mode=driving";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            } else {
                // showSettingsAlert();
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker != null) {
            getDirectionMenuItemClicked(marker.getPosition());
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mCurrentLocation != null) {
            centerMapOnMyLocation(mCurrentLocation);
        }
        return true;
    }

    private void centerMapOnMyLocation(Location location) {
        if (location != null) {
            LatLng currentlatLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentlatLng).zoom(17).bearing(0).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentlatLng, 15);
            googleMap.moveCamera(cameraUpdate);
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.setOnInfoWindowClickListener(this);
        map.setOnMapLoadedCallback(this);
        map.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public void onMapLoaded() {
        mCurrentLocation = fusedLocationService.getLocation();
        if (mCurrentLocation != null) {
            loadMapInfo(mCurrentLocation);
            if (MarshMallowPermission.getInstant(getActivity()).checkPermissionForAccessFineLocation()) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationButtonClickListener(this);
            }
        } else {
            // messageView.setVisibility(View.VISIBLE);
        }
    }


    private void loadMapInfo(Location location) {
        if (googleMap != null) googleMap.clear();
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            this.currenLatLng = latLng;
        }
        if (currenLatLng != null && getListLocationMarkerOptions() != null) {
            drawMarker(currenLatLng);
        }
    }

    private void drawMarker(LatLng latLng) {
        // add markers
        for (MarkerOptions options : getListLocationMarkerOptions()) {
            googleMap.addMarker(options);
        }
        // show all markers on the screen
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions options : getListLocationMarkerOptions()) {
            builder.include(options.getPosition());
        }
        builder.include(currenLatLng);
        LatLngBounds bounds = builder.build();
        // zoom
        customGoogleMapZoom(adjustBoundsForMaxZoomLevel(bounds));
    }


    private List<MarkerOptions> getListLocationMarkerOptions() {
        List<MarkerOptions> listOfMarkerOptions = new ArrayList<MarkerOptions>();
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(18.8026425, 98.9651844));
        options.title("MAYA Lifestyle Shopping Center");
        options.snippet("Mueang Chiang Mai District, Chiang Mai 50300");
        listOfMarkerOptions.add(options);
        return listOfMarkerOptions;
    }

    private LatLngBounds adjustBoundsForMaxZoomLevel(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;
        double deltaLat = Math.abs(sw.latitude - ne.latitude);
        double deltaLon = Math.abs(sw.longitude - ne.longitude);

        double zoomN = 0.005; // minimum zoom coefficient
        if (deltaLat < zoomN) {
            sw = new LatLng(sw.latitude - (zoomN - deltaLat / 2), sw.longitude);
            ne = new LatLng(ne.latitude + (zoomN - deltaLat / 2), ne.longitude);
            bounds = new LatLngBounds(sw, ne);
        } else if (deltaLon < zoomN) {
            sw = new LatLng(sw.latitude, sw.longitude - (zoomN - deltaLon / 2));
            ne = new LatLng(ne.latitude, ne.longitude + (zoomN - deltaLon / 2));
            bounds = new LatLngBounds(sw, ne);
        }
        return bounds;
    }

    private void customGoogleMapZoom(LatLngBounds bounds) {
        // get the screen size
        DisplayMetrics metrics = new DisplayMetrics();
        if (context != null) {
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            int padding_in_dp = 50; // 6 dps
            final float scale = getResources().getDisplayMetrics().density;
            int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding_in_px);
            googleMap.animateCamera(cameraUpdate);
        }
    }


    public void searchMapLocation(String textSearch) {
        List<Address> addressList = null;
        if (textSearch != null || !textSearch.equals("")) {
            Geocoder geocoder = new Geocoder(context);
            try {
                addressList = geocoder.getFromLocationName(textSearch, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            //googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

}
