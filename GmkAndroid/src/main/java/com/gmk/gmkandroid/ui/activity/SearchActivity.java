package com.gmk.gmkandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.couchbase.lite.LiveQuery;

import com.gmk.gmkandroid.GmkApplication;
import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.adapter.SearchHistoryAdapter;
import com.gmk.gmkandroid.document.SearchHistory;

public class SearchActivity extends BaseActivity {
  private GmkApplication app;

  @Bind(R.id.svPlaces) SearchView svPlaces;
  @Bind(R.id.rvSearchHistory) RecyclerView rvSearchHistory;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);

    app = (GmkApplication) getApplication();

    final ActionBar ab = getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
    ab.setTitle(getString(R.string.title_activity_search));

    setupSearchView();
    setupSearchHistory();
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
          if (!query.isEmpty()) {
            Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
            i.putExtra("q", query);
            startActivity(i);
          }

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

  @OnClick(R.id.cvMyLocation)
  void myLocationClicked() {
    Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
    i.putExtra("myLocationQuery", true);
    startActivity(i);
  }
}
