package com.gmk.gmkandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;

import java.util.ArrayList;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.adapter.PlaceRecyclerViewAdapter;
import com.gmk.gmkandroid.model.Place;

public class SearchResultListFragment extends Fragment {

  private PlaceRecyclerViewAdapter mAdapter;
  private ArrayList<Place> mPlaces;

  @Bind(R.id.rvSearchResult) RecyclerView rvSearchResult;

  public static SearchResultListFragment newInstance(ArrayList<Place> places) {
    Bundle args = new Bundle();
    args.putSerializable("places", places);

    SearchResultListFragment fragment = new SearchResultListFragment();
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search_result_list, container, false);
    ButterKnife.bind(this, view);

    mPlaces = (ArrayList<Place>) getArguments().getSerializable("places");

    // Lookup the recyclerview in activity layout
    // Create adapter passing in the sample user data
    mAdapter = new PlaceRecyclerViewAdapter(getActivity(), mPlaces);
    // Attach the adapter to the recyclerview to populate items
    rvSearchResult.setAdapter(mAdapter);
    // Set layout manager to position the items
    rvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
    // That's all!

    mAdapter.notifyDataSetChanged();

    return view;
  }
}
