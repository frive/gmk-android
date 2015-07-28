package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;

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
      TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), MainActivity.this);
      adapter.addFragment(new HomeFragment(), "Home");
      adapter.addFragment(new FaveFragment(), "Fave");
      adapter.addFragment(new EventFragment(), "Event");
      adapter.addFragment(new GroupFragment(), "Group");
      viewPager.setAdapter(adapter);
    }

    // Give the TabLayout the ViewPager
    tabLayout.setupWithViewPager(viewPager);

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
}
