package com.example.marcellosouza.weathermap.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marcellosouza.weathermap.R;
import com.example.marcellosouza.weathermap.util.Constants;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//compactActivity
public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiCLient;
    private GoogleApiClient client;
    private LatLng latLngSelected;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.marcellosouza.weathermap.R.layout.activity_map);

        startingTrack();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatButtonSearch);
        floatingActionButton.setOnClickListener(this);

    }


    private boolean isGooglePlaySericesAvailable(final Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultado = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultado == ConnectionResult.SUCCESS;
    }

    private void startingTrack() {
        if (isGooglePlaySericesAvailable(this) == true) {
            mGoogleApiCLient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (!mGoogleApiCLient.isConnected() || !mGoogleApiCLient.isConnecting())
                mGoogleApiCLient.connect();

        } else {
            Log.e("Messagem", "Não conseguiu conectar no google play services");
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {

                if(mMap != null)
                {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(getLocalClassName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                    latLngSelected = latLng;
                }
            }
        });
    }


    static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 0, MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION) )
            {

            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
            }

            Log.e("Permissions", "No permissions to use locations");

            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiCLient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiCLient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        stopLocationsUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

        }
    }

    public void stopLocationsUpdates() {
        if (mGoogleApiCLient != null && mGoogleApiCLient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiCLient, this);
            mGoogleApiCLient.disconnect();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    permissionRefused();
                }
                return;
            }
        }
    }

    public void permissionRefused()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface d, int id){
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.floatButtonSearch:

                if(latLngSelected == null) {

                    Toast.makeText(this, getString(R.string.select_a_point), Toast.LENGTH_LONG);

                }else {

                    Intent intent = new Intent(MapActivity.this, CityListActivity.class);
                    intent.putExtra(Constants.BUNDLE_LAT, latLngSelected.latitude);
                    intent.putExtra(Constants.BUNDLE_LONG, latLngSelected.longitude);

                    startActivity(intent);
                }

                break;
        }
    }
}