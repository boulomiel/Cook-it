package com.rubenmimoun.cookit.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rubenmimoun.cookit.R;
import com.rubenmimoun.cookit.utils.PermissionUtils;

import static android.content.Context.LOCATION_SERVICE;
import static android.telephony.CellLocation.requestLocationUpdate;

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {

    private GoogleMap map ;
    private static final int MY_PERMISSION = 1;
    private LocationManager mLocationManager;
    private boolean permissionIsGranted = false ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        PermissionUtils.checkLocationPermission(getContext(),this,MY_PERMISSION);

        this.map = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.setMyLocationEnabled(true);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==  MY_PERMISSION  ){
            if(permissions.length >0){

                if(permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    requestLocationUpdate();


                }
            }


            PermissionUtils.checkLocationPermission(getContext(),this,MY_PERMISSION);

        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}