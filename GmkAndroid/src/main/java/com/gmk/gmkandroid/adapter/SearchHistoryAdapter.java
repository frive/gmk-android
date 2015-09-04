package com.gmk.gmkandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;
import butterknife.ButterKnife;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.ui.activity.SearchResultsActivity;

public class SearchHistoryAdapter extends
    LiveQueryRecyclerAdapter<SearchHistoryAdapter.ViewHolder> {

  private Context mContext;

  public SearchHistoryAdapter(Context context, LiveQuery liveQuery) {
    super(context, liveQuery);

    this.mContext = context;
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
        Intent i = new Intent(mContext, SearchResultsActivity.class);
        i.putExtra("q", holder.tvQuery.getText());
        mContext.startActivity(i);
      }
    });
  }
}