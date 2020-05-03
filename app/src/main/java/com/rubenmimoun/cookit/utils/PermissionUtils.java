package com.rubenmimoun.cookit.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.rubenmimoun.cookit.ui.map.MapFragment;

public class PermissionUtils {

    public static void  checkLocationPermission(Context context, Fragment fragment,int code){

        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            requestLocationPermission(fragment,code);
        }

    }

    public static void requestLocationPermission(Fragment fragment, int requestCode){
       fragment.requestPermissions(new String []{Manifest.permission.ACCESS_COARSE_LOCATION},requestCode);

    }
}
