package com.gmk.gmkandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.model.Place;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PlaceRecyclerViewAdapter extends
    RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

  // Store a member variable for the users
  private ArrayList<Place> places;
  // Store the context for later use
  private Context context;

  // Pass in the context and users array into the constructor
  public PlaceRecyclerViewAdapter(Context context, ArrayList<Place> places) {
    this.places = places;
    this.context = context;
  }

  // Provide a direct reference to each of the views within a data item
  // Used to cache the views within the item layout for fast access
  public static class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public TextView tvInfo;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      super(itemView);
      this.tvInfo = (TextView) itemView.findViewById(R.id.tv_info);
    }
  }

  // Usually involves inflating a layout from XML and returning the holder
  @Override
  public PlaceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Inflate the custom layout
    View itemView = LayoutInflater.from(context).
        inflate(R.layout.item_search_result, parent, false);
    // Return a new holder instance
    return new PlaceRecyclerViewAdapter.ViewHolder(itemView);
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(PlaceRecyclerViewAdapter.ViewHolder holder, int position) {
    // Get the data model based on position
    Place place = places.get(position);

    // Set item views based on the data model
    holder.tvInfo.setText(place.getName());
  }

  // Return the total count of items
  @Override
  public int getItemCount() {
    return places.size();
  }
}