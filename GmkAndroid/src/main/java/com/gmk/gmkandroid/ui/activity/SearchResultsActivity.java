package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
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
import com.gmk.gmkandroid.document.SearchHistory;
import com.gmk.gmkandroid.ui.fragment.SearchResultListFragment;

public class SearchResultsActivity extends BaseActivity {
  private GmkApplication app;

  private ArrayList<Place> mPlaces;
  private String query;
  private boolean myLocationQuery;
  private boolean mToggleMap = false;

  private ActionBar ab;

  @Bind(R.id.progressBar) ProgressBar progressBar;
  @Bind(R.id.tvNoResult) TextView tvNoResult;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_results);
    ButterKnife.bind(this);

    query = getIntent().getStringExtra("q");
    myLocationQuery = getIntent().getBooleanExtra("myLocationQuery", false);

    app = (GmkApplication) getApplication();

    mPlaces = new ArrayList<Place>();

    // Save query
    if (query != null && !query.isEmpty()) {
      try {
        Map<String, Object> props = new HashMap<>();
        props.put("q", query);
        SearchHistory.createSearchQuery(app.couch, props);
      } catch (Exception e) {
        Logger.e(e, "Create document error");
      }
    }

    ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
  }

  @Override
  protected void onStart() {
    super.onStart();

    Map qs  = new HashMap();

    if (query != null && !query.isEmpty()) {
      qs.put("q", query);
    }

    if (myLocationQuery) {
      qs.put("distance", 3);

      if (app.myLocation != null) {
        qs.put("lat", app.myLocation.getLatitude());
        qs.put("lon", app.myLocation.getLongitude());
      }
    }

    fetchPlaces(qs);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search_results, menu);

    menu.findItem(R.id.mnuToggleMap).setIcon(
        new IconDrawable(this, MaterialIcons.md_map).actionBarSize());

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mnuToggleMap:

        if (mToggleMap) {
          mToggleMap = false;
          attachSearchResultsListFragment();
        } else {
          mToggleMap = true;
        }

        return true;
    }

    return super.onOptionsItemSelected(item);
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

              attachSearchResultsListFragment();
            }
          }
        } catch (IOException e) {
          Logger.e(e, "IO error");
        } catch (JSONException e) {
          // Invalid JSON format, show appropriate error.
          Logger.e(e, "Invalid JSON format");
        }
      }

      @Override public void failure(RetrofitError retrofitError) {
        // Log error here since request failed
        Logger.e(retrofitError, "Retrofit error");
      }
    });
  }

  private void attachSearchResultsListFragment() {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    SearchResultListFragment fragment = SearchResultListFragment.newInstance(mPlaces);
    ft.replace(R.id.frameLayout, fragment);
    ft.commit();
  }
}
