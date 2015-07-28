package com.gmk.gmkandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;

import com.gmk.gmkandroid.R;

public class FaveFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_fave, container, false);
    TextView textView = (TextView) view;
    textView.setText("Fave");

    return view;
  }

}