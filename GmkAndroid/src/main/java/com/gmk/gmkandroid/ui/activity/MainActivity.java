package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.adapter.TabPagerAdapter;
import com.gmk.gmkandroid.ui.fragment.HomeFragment;
import com.gmk.gmkandroid.ui.fragment.FaveFragment;
import com.gmk.gmkandroid.ui.fragment.EventFragment;
import com.gmk.gmkandroid.ui.fragment.GroupFragment;

public class MainActivity extends BaseActivity {
  private int mIconTabColor;

  @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
  @Bind(R.id.navView) NavigationView navView;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.viewPager) ViewPager viewPager;
  @Bind(R.id.tabLayout) TabLayout tabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mIconTabColor = getResources().getColor(R.color.white);

    setSupportActionBar(toolbar);

    final ActionBar ab = getSupportActionBar();
    ab.setHomeAsUpIndicator(
        new IconDrawable(this, MaterialIcons.md_menu)
            .actionBarSize().color(mIconTabColor));
    ab.setDisplayHomeAsUpEnabled(true);
    ab.setTitle("");

    if (navView != null) {
      setupDrawerContent(navView);
    }

    if (viewPager != null) {
      TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
      adapter.addFragment(new HomeFragment());
      adapter.addFragment(new FaveFragment());
      adapter.addFragment(new EventFragment());
      adapter.addFragment(new GroupFragment());
      viewPager.setAdapter(adapter);
    }

    setupTab();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);

    menu.getItem(0).setIcon(
        new IconDrawable(this, MaterialIcons.md_search)
            .actionBarSize().color(mIconTabColor));

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;

      case R.id.mnuSearch:
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
          }
        });
  }

  private void setupTab() {
    IconDrawable homeIcon = new IconDrawable(this, MaterialIcons.md_home)
        .color(mIconTabColor)
        .actionBarSize();
    IconDrawable faveIcon = new IconDrawable(this, MaterialIcons.md_favorite)
        .color(mIconTabColor)
        .actionBarSize();
    IconDrawable eventIcon = new IconDrawable(this, MaterialIcons.md_event)
        .color(mIconTabColor)
        .actionBarSize();
    IconDrawable groupIcon = new IconDrawable(this, MaterialIcons.md_group)
        .color(mIconTabColor)
        .actionBarSize();

    // Give the TabLayout the ViewPager
    tabLayout.setupWithViewPager(viewPager);

    // Set icons
    tabLayout.getTabAt(0).setIcon(homeIcon);
    tabLayout.getTabAt(1).setIcon(faveIcon);
    tabLayout.getTabAt(2).setIcon(eventIcon);
    tabLayout.getTabAt(3).setIcon(groupIcon);
  }
}
