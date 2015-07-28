package com.gmk.gmkandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

import com.thedazzler.droidicon.IconicFontDrawable;

public class TabPagerAdapter extends FragmentPagerAdapter {

  private final List<Fragment> mFragments = new ArrayList<>();
  private final List<String> mFragmentTitles = new ArrayList<>();
  private final List<IconicFontDrawable> icons = new ArrayList<>();

  public TabPagerAdapter(FragmentManager fm, Context context) {
    super(fm);

    IconicFontDrawable homeIcon = new IconicFontDrawable(context, "gmd-home");
    IconicFontDrawable faveIcon = new IconicFontDrawable(context, "gmd-favorite");
    IconicFontDrawable eventIcon = new IconicFontDrawable(context, "gmd-event");
    IconicFontDrawable groupIcon = new IconicFontDrawable(context, "gmd-group");

    icons.add(homeIcon);
    icons.add(faveIcon);
    icons.add(eventIcon);
    icons.add(groupIcon);


    //mFragments.add(new HomeFragment());
    //mFragments.add(new FaveFragment());
    //mFragments.add(new EventFragment());
    //mFragments.add(new GroupFragment());
  }

  public void addFragment(Fragment fragment, String title) {
    mFragments.add(fragment);
    mFragmentTitles.add(title);
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
    // Generate title based on item position
    // return tabTitles[position];
    //Drawable image = icons.get(position);
    //image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
    //SpannableString sb = new SpannableString(" ");
    //ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
    //sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    //
    //return sb;

    return mFragmentTitles.get(position);
  }
}