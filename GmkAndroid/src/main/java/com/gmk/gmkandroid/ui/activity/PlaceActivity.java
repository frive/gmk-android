package com.gmk.gmkandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.model.Place;

public class PlaceActivity extends BaseActivity {
  private Place place;

  @Bind(R.id.ivPlaceHeader) ImageView ivPlaceHeader;
  @Bind(R.id.fabMapMarker) FloatingActionButton fabMapMarker;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_place_detail);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    place = (Place) intent.getSerializableExtra("place");

    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    CollapsingToolbarLayout collapsingToolbar =
        (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(place.getName());

    loadBackdrop();
  }

  private void loadBackdrop() {
    Drawable imgMapMarker = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.MAP_MARKER)
        .setColor(getResources().getColor(R.color.white))
        .build();

    //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    fabMapMarker.setImageDrawable(imgMapMarker);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
        // if this doesn't work as desired, another possibility is to call `finish()` here.
        this.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
