package com.gmk.gmkandroid.service;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import retrofit.client.Response;
import retrofit.Callback;

import java.util.Map;

public interface GmkService {
  @GET("/search")
  void searchPlaces(@QueryMap Map<String, String> query, Callback<Response> cb);
}
