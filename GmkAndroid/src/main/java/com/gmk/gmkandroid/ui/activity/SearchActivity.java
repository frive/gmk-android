package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
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
import com.gmk.gmkandroid.adapter.PlaceRecyclerViewAdapter;
import com.gmk.gmkandroid.model.Place;

public class SearchActivity extends BaseActivity {
  @Bind(R.id.search_result) RecyclerView mRecyclerView;
  @Bind(R.id.search_progress) ProgressBar mProgress;
  @Bind(R.id.search_view) SearchView mSearchView;

  private PlaceRecyclerViewAdapter mAdapter;
  private ArrayList<Place> mPlaces;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);

    final ActionBar ab = getSupportActionBar();
    ab.setTitle("Search");
    ab.setDisplayHomeAsUpEnabled(true);

    mPlaces = new ArrayList<Place>();

    // Lookup the recyclerview in activity layout
    // Create adapter passing in the sample user data
    mAdapter = new PlaceRecyclerViewAdapter(this, mPlaces);
    // Attach the adapter to the recyclerview to populate items
    mRecyclerView.setAdapter(mAdapter);
    // Set layout manager to position the items
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    // That's all!

    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        // Fetch the data remotely
        fetchPlaces(query);
        // Reset SearchView
        mSearchView.clearFocus();
        mSearchView.setQuery("", false);

        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
  }

  private void fetchPlaces(String query) {
    // Show progress bar before making network request
    mProgress.setVisibility(ProgressBar.VISIBLE);

    Map qs  = new HashMap();
    qs.put("lat", 14.42);
    qs.put("lon", 121);
    qs.put("distance", 3);

    if (!query.isEmpty()) {
      qs.put("q", query);
    }

    gmkAPI.searchPlacesJson(qs, new Callback<Response>() {
      @Override public void success(Response resp, Response response) {
        try {
          // hide progress bar
          mProgress.setVisibility(ProgressBar.GONE);
          JSONObject docs;

          if (response != null) {
            // Get the docs json array
            docs = new JSONObject(StreamUtils.toString(resp.getBody().in()));

            // Remove all books from the adapter
            mPlaces.clear();

            // Load model objects into the adapter
            mPlaces.addAll(Place.fromJson(docs.getJSONArray("places")));

            mAdapter.notifyDataSetChanged();
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
