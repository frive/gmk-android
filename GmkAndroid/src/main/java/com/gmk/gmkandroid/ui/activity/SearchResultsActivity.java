package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gmk.gmkandroid.document.SearchHistory;
import com.orhanobut.logger.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.cocoahero.android.geojson.util.StreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.GmkApplication;
import com.gmk.gmkandroid.model.Place;
import com.gmk.gmkandroid.adapter.PlaceRecyclerViewAdapter;

public class SearchResultsActivity extends BaseActivity {
  private GmkApplication app;

  private PlaceRecyclerViewAdapter mAdapter;
  private ArrayList<Place> mPlaces;
  private ActionBar ab;
  private String query;
  private boolean myLocationQuery;

  @Bind(R.id.rvSearchResult) RecyclerView rvSearchResult;
  @Bind(R.id.progressBar) ProgressBar progressBar;
  @Bind(R.id.tvNoResult) TextView tvNoResult;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_results);
    ButterKnife.bind(this);

    query = getIntent().getStringExtra("q");
    myLocationQuery = getIntent().getBooleanExtra("myLocationQuery", false);

    // Save query
    if (query != null && !query.isEmpty()) {
      try {
        Map<String, Object> props = new HashMap<>();
        props.put("q", query);
        SearchHistory.createSearchQuery(app.couch, props);
      } catch (Exception e) {
        Logger.e(e, "");
      }
    }

    app = (GmkApplication) getApplication();

    ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    mPlaces = new ArrayList<Place>();

    // Lookup the recyclerview in activity layout
    // Create adapter passing in the sample user data
    mAdapter = new PlaceRecyclerViewAdapter(this, mPlaces);
    // Attach the adapter to the recyclerview to populate items
    rvSearchResult.setAdapter(mAdapter);
    // Set layout manager to position the items
    rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
    // That's all!
  }

  @Override
  protected void onStart() {
    super.onStart();

    Map qs  = new HashMap();

    if (query != null && !query.isEmpty()) {
      qs.put("q", query);
    }

    if (myLocationQuery) {
      if (app.myLocation != null) {
        qs.put("lat", app.myLocation.getLatitude());
        qs.put("lon", app.myLocation.getLongitude());
      }

      qs.put("distance", 3);
    }

    fetchPlaces(qs);
  }

  private void fetchPlaces(Map qs) {
    // Show progress bar before making network request
    progressBar.setVisibility(ProgressBar.VISIBLE);

    app.gmkAPI.searchPlacesJson(qs, new Callback<Response>() {
      @Override public void success(Response resp, Response response) {
        JSONObject docs;
        ArrayList<Place> places;
        int resultsCount;

        try {
          // hide progress bar
          progressBar.setVisibility(ProgressBar.GONE);

          if (response != null) {
            // Get the docs json array
            docs = new JSONObject(StreamUtils.toString(resp.getBody().in()));
            places = Place.fromJson(docs.getJSONArray("places"));
            resultsCount = places.size();

            ab.setTitle(query);
            ab.setSubtitle(resultsCount + " results");

            if (myLocationQuery) {
              ab.setTitle(getString(R.string.current_location));
            }

            if (resultsCount == 0) {
              tvNoResult.setVisibility(TextView.VISIBLE);
            } else {
              tvNoResult.setVisibility(TextView.GONE);

              // Remove all books from the adapter
              mPlaces.clear();

              // Load model objects into the adapter
              mPlaces.addAll(places);

              mAdapter.notifyDataSetChanged();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (JSONException e) {
          // Invalid JSON format, show appropriate error.
          e.printStackTrace();
        }
      }

      @Override public void failure(RetrofitError retrofitError) {
        // Log error here since request failed
        throw retrofitError;
      }
    });
  }
}
