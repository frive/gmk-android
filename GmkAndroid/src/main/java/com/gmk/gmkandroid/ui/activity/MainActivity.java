package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.ViewPager;
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
  @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
  @Bind(R.id.nav_main) NavigationView navigationView;
  @Bind(R.id.vpager_main) ViewPager viewPager;
  @Bind(R.id.tab_main) TabLayout tabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    if (navigationView != null) {
      setupDrawerContent(navigationView);
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
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
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
    int iconColor = Color.WHITE;

    IconDrawable homeIcon = new IconDrawable(this, MaterialIcons.md_home)
        .color(iconColor)
        .actionBarSize();
    IconDrawable faveIcon = new IconDrawable(this, MaterialIcons.md_favorite)
        .color(iconColor)
        .actionBarSize();
    IconDrawable eventIcon = new IconDrawable(this, MaterialIcons.md_event)
        .color(iconColor)
        .actionBarSize();
    IconDrawable groupIcon = new IconDrawable(this, MaterialIcons.md_group)
        .color(iconColor)
        .actionBarSize();

    // Give the TabLayout the ViewPager
    tabLayout.setupWithViewPager(viewPager);

    // Set icons
    tabLayout.getTabAt(0).setIcon(homeIcon);
    tabLayout.getTabAt(1).setIcon(faveIcon);
    tabLayout.getTabAt(2).setIcon(eventIcon);
    tabLayout.getTabAt(3).setIcon(groupIcon);

    tabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
  }
}
