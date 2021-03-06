package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.gmk.gmkandroid.GmkApplication;
import com.gmk.gmkandroid.model.Place;
import com.gmk.gmkandroid.adapter.PlaceRecyclerViewAdapter;

public class SearchActivity extends BaseActivity {
  private GmkApplication app;

  private PlaceRecyclerViewAdapter mAdapter;
  private ArrayList<Place> mPlaces;

  @Bind(R.id.rvSearchResult) RecyclerView rvSearchResult;
  @Bind(R.id.pbSearch) ProgressBar pbSearch;
  @Bind(R.id.tvNoResult) TextView tvNoResult;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);

    app = (GmkApplication) getApplication();

    final ActionBar ab = getSupportActionBar();
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

  private void fetchPlaces(String query) {
    // Show progress bar before making network request
    pbSearch.setVisibility(ProgressBar.VISIBLE);

    Map qs  = new HashMap();
    qs.put("lat", 14.42);
    qs.put("lon", 121);
    qs.put("distance", 3);

    if (!query.isEmpty()) {
      qs.put("q", query);
    }

    app.gmkAPI.searchPlacesJson(qs, new Callback<Response>() {
      @Override public void success(Response resp, Response response) {
        JSONObject docs;
        ArrayList<Place> places;

        try {
          // hide progress bar
          pbSearch.setVisibility(ProgressBar.GONE);

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
    final MenuItem searchItem = menu.findItem(R.id.svPlaces);
    final SearchView svPlaces = (SearchView) MenuItemCompat.getActionView(searchItem);

    svPlaces.setIconifiedByDefault(false);
    svPlaces.setQueryHint(getString(R.string.main_search_hint));

    svPlaces.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        // Fetch the data remotely
        fetchPlaces(query);
        // Reset SearchView
        svPlaces.clearFocus();
        svPlaces.setQuery("", false);

        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.svPlaces) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
