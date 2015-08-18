package com.gmk.gmkandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import java.util.ArrayList;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.model.Place;
import com.gmk.gmkandroid.ui.activity.PlaceActivity;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PlaceRecyclerViewAdapter extends
    RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

  // Store a member variable for the users
  private ArrayList<Place> places;
  // Store the context for later use
  private Context mContext;

  // Pass in the context and users array into the constructor
  public PlaceRecyclerViewAdapter(Context context, ArrayList<Place> places) {
    this.places = places;
    this.mContext = context;
  }

  // Provide a direct reference to each of the views within a data item
  // Used to cache the views within the item layout for fast access
  public static class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.tvFaves) TextView tvFaves;
    @Bind(R.id.ivPhoto) ImageView ivPhoto;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  // Usually involves inflating a layout from XML and returning the holder
  @Override
  public PlaceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Inflate the custom layout
    View itemView = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.item_search_result, parent, false);
    // Return a new holder instance
    return new PlaceRecyclerViewAdapter.ViewHolder(itemView);
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(PlaceRecyclerViewAdapter.ViewHolder holder, int position) {
    // Get the data model based on position
    final Place place = places.get(position);

    IconDrawable imgPhoto = new IconDrawable(mContext, MaterialIcons.md_image);
    IconDrawable imgFave = new IconDrawable(mContext, MaterialIcons.md_favorite)
        .color(mContext.getResources().getColor(R.color.primary_accent))
        .actionBarSize();

    // Set item views based on the data model
    holder.tvName.setText(place.getName());
    holder.tvFaves.setText("100");
    holder.tvFaves.setCompoundDrawables(imgFave, null, null, null);
    holder.ivPhoto.setImageDrawable(imgPhoto);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, PlaceActivity.class);
        intent.putExtra("place", place);

        mContext.startActivity(intent);
      }
    });
  }

  // Return the total count of items
  @Override
  public int getItemCount() {
    return places.size();
  }
}