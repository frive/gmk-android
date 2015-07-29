package com.gmk.gmkandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

  private final List<Fragment> mFragments = new ArrayList<>();

  public TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void addFragment(Fragment fragment) {
    mFragments.add(fragment);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    // Return empty title for icons
    return "";
  }
}