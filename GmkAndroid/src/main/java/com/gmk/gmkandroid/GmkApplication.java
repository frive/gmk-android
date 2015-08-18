package com.gmk.gmkandroid;

import android.app.Application;

import com.gmk.gmkandroid.service.GmkService;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import retrofit.RestAdapter;

import com.gmk.gmkandroid.R;

public class GmkApplication extends Application {

  public static GmkService gmkAPI;

  @Override
  public void onCreate() {
    super.onCreate();

    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(getString(R.string.gmk_endpoint))
        .build();

    gmkAPI = restAdapter.create(GmkService.class);

    Iconify
        .with(new FontAwesomeModule())
        .with(new MaterialModule());
  }
}
