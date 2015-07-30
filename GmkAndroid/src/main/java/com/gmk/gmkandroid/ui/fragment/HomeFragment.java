package com.gmk.gmkandroid.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gmk.gmkandroid.R;

import com.gmk.gmkandroid.model.Cheeses;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.steamcrafted.materialiconlib.MaterialIconView;

public class HomeFragment extends Fragment {
  @Bind(R.id.homeListView) RecyclerView homeListView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);

    homeListView.setLayoutManager(new LinearLayoutManager(homeListView.getContext()));
    homeListView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
        getRandomSublist(Cheeses.sCheeseStrings, 30)));

    return view;
  }

  private List<String> getRandomSublist(String[] array, int amount) {
    ArrayList<String> list = new ArrayList<>(amount);
    Random random = new Random();
    while (list.size() < amount) {
      list.add(array[random.nextInt(array.length)]);
    }
    return list;
  }

  public static class SimpleStringRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
      public String mBoundString;

      public final View mView;
      public final MaterialIconView mImageView;
      public final TextView mTextView;

      public ViewHolder(View view) {
        super(view);
        mView = view;
        mImageView = (MaterialIconView) view.findViewById(R.id.avatar);
        mTextView = (TextView) view.findViewById(android.R.id.text1);
      }

      @Override
      public String toString() {
        return super.toString() + " '" + mTextView.getText();
      }
    }

    public String getValueAt(int position) {
      return mValues.get(position);
    }

    public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
      //context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
      //mBackground = mTypedValue.resourceId;
      mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.list_item, parent, false);
      //view.setBackgroundResource(mBackground);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mBoundString = mValues.get(position);
      holder.mTextView.setText(mValues.get(position));

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //Context context = v.getContext();
          //Intent intent = new Intent(context, CheeseDetailActivity.class);
          //intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
          //
          //context.startActivity(intent);
        }
      });

      //Glide.with(holder.mImageView.getContext())
      //    .load(Cheeses.getRandomCheeseDrawable())
      //    .fitCenter()
      //    .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }
  }

}