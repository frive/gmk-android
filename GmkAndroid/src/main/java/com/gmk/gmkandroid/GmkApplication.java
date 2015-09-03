package com.gmk.gmkandroid;

import android.app.Application;

import android.location.Location;
import com.couchbase.lite.android.AndroidContext;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import retrofit.RestAdapter;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.LocationServices;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Database;
import com.orhanobut.logger.Logger;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.service.GmkService;

public class GmkApplication extends Application {

  public static Database couch;
  public static GmkService gmkAPI;
  public static Location myLocation;

  private static final String DATABASE_NAME = "gmk";

  private static LostApiClient lostApiClient;
  private static Manager couchManager;

  @Override
  public void onCreate() {
    super.onCreate();

    try {
      couchManager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
      couchManager.getDatabase(DATABASE_NAME).delete();

      couch = couchManager.getDatabase(DATABASE_NAME);
    } catch (Exception e) {
      Logger.e(e, "");
    }

    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(getString(R.string.gmk_endpoint))
        .build();

    gmkAPI = restAdapter.create(GmkService.class);

    lostApiClient = new LostApiClient.Builder(this).build();
    lostApiClient.connect();

    myLocation = LocationServices.FusedLocationApi.getLastLocation();

    Iconify
        .with(new FontAwesomeModule())
        .with(new MaterialModule());
  }
}
