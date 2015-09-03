package com.gmk.gmkandroid.adapter;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.QueryEnumerator;
import butterknife.ButterKnife;
import com.orhanobut.logger.Logger;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.adapter.LiveQueryRecyclerAdapter;

public class SearchHistoryAdapter extends
    LiveQueryRecyclerAdapter<SearchHistoryAdapter.ViewHolder> {

  public SearchHistoryAdapter(Context context, LiveQuery liveQuery) {
    super(context, liveQuery);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tvQuery) TextView tvQuery;

    public ViewHolder(View itemView, int viewType) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  @Override
  public SearchHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_search_history, parent, false);

    return new ViewHolder(itemView, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    final SearchHistoryAdapter.ViewHolder holder =
        (SearchHistoryAdapter.ViewHolder) viewHolder;

    final Document doc = (Document) getItem(position);

    holder.tvQuery.setText((String) doc.getProperty("q"));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Logger.v((String) doc.getProperty("_id"));
        //Intent intent = new Intent(mContext, PlaceActivity.class);
        //intent.putExtra("place", place);
        //
        //mContext.startActivity(intent);
      }
    });
  }
}