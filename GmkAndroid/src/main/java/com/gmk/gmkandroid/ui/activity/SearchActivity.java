package com.gmk.gmkandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.couchbase.lite.LiveQuery;
import com.gmk.gmkandroid.adapter.SearchHistoryAdapter;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.cocoahero.android.geojson.util.StreamUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.GmkApplication;
import com.gmk.gmkandroid.model.Place;
import com.gmk.gmkandroid.document.SearchHistory;
import com.gmk.gmkandroid.adapter.PlaceRecyclerViewAdapter;

public class SearchActivity extends BaseActivity {
  private GmkApplication app;

  private PlaceRecyclerViewAdapter mAdapter;
  private ArrayList<Place> mPlaces;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.svPlaces) SearchView svPlaces;
  @Bind(R.id.rvSearchResult) RecyclerView rvSearchResult;
  @Bind(R.id.rvSearchHistory) RecyclerView rvSearchHistory;
  @Bind(R.id.progressBar) ProgressBar progressBar;
  @Bind(R.id.tvNoResult) TextView tvNoResult;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);

    app = (GmkApplication) getApplication();

    final ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
    ab.setTitle(getString(R.string.title_activity_search));

    setupSearchHistory();
    setupSearchView();

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

  private void fetchPlaces(Map qs) {
    // Save queries
    if (qs.get("q") != null) {
      try {
        Map<String, Object> props = new HashMap<>();
        props.put("q", qs.get("q"));
        SearchHistory.createSearchQuery(app.couch, props);
      } catch (Exception e) {
        Logger.e(e, "");
      }
    }

    // Show progress bar before making network request
    progressBar.setVisibility(ProgressBar.VISIBLE);

    app.gmkAPI.searchPlacesJson(qs, new Callback<Response>() {
      @Override public void success(Response resp, Response response) {
        JSONObject docs;
        ArrayList<Place> places;

        try {
          // hide progress bar
          progressBar.setVisibility(ProgressBar.GONE);

          if (response != null) {
            // Get the docs json array
            docs = new JSONObject(StreamUtils.toString(resp.getBody().in()));
            places = Place.fromJson(docs.getJSONArray("places"));

            if (places.size() == 0) {
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search, menu);

    menu.findItem(R.id.mnuMyLocation)
        .setIcon(
            new IconDrawable(this, MaterialIcons.md_my_location).actionBarSize().colorRes(R.color.md_grey_800));

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
      case R.id.mnuMyLocation:
        Map qs  = new HashMap();

        if (app.myLocation != null) {
          qs.put("lat", app.myLocation.getLatitude());
          qs.put("lon", app.myLocation.getLongitude());
        }

        qs.put("distance", 3);

        // Fetch the data remotely
        fetchPlaces(qs);

        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setupSearchHistory() {
    rvSearchHistory.setHasFixedSize(true);
    LiveQuery liveQuery = SearchHistory.getQuery(app.couch).toLiveQuery();

    SearchHistoryAdapter mAdapter = new SearchHistoryAdapter(this, liveQuery);

    rvSearchHistory.setAdapter(mAdapter);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    rvSearchHistory.setLayoutManager(mLayoutManager);
  }

  private void setupSearchView() {
    if (svPlaces != null) {
      SearchManager searchManager =
          (SearchManager) getSystemService(Context.SEARCH_SERVICE);

      svPlaces.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
      svPlaces.setIconifiedByDefault(false);

      svPlaces.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
          Map qs  = new HashMap();

          if (!query.isEmpty()) {
            qs.put("q", query);
          }

          // Fetch the data remotely
          fetchPlaces(qs);

          // Reset SearchView
          svPlaces.clearFocus();
          svPlaces.setQuery("", false);

          return true;
        }

        @Override public boolean onQueryTextChange(String s) {
          return false;
        }
      });
    }
  }
}
