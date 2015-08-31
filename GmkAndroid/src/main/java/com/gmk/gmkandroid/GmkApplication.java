package com.gmk.gmkandroid;

import android.app.Application;

import android.location.Location;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import retrofit.RestAdapter;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.LocationServices;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.service.GmkService;

public class GmkApplication extends Application {

  public static GmkService gmkAPI;
  public Location myLocation;

  private LostApiClient lostApiClient;

  @Override
  public void onCreate() {
    super.onCreate();

    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(getString(R.string.gmk_endpoint))
        .build();

    gmkAPI = restAdapter.create(GmkService.class);

    LostApiClient lostApiClient = new LostApiClient.Builder(this).build();
    lostApiClient.connect();

    myLocation = LocationServices.FusedLocationApi.getLastLocation();

    Iconify
        .with(new FontAwesomeModule())
        .with(new MaterialModule());
  }
}
