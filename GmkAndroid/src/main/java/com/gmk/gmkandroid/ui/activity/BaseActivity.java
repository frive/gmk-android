package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import retrofit.RestAdapter;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.service.GmkService;

public class BaseActivity extends AppCompatActivity {
  protected static GmkService gmkAPI;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(getString(R.string.gmk_endpoint))
        .build();

    gmkAPI = restAdapter.create(GmkService.class);
  }

}
