package com.gmk.gmkandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.model.Place;

public class PlaceActivity extends BaseActivity {
  private Place place;

  @Bind(R.id.ivPlaceHeader) ImageView ivPlaceHeader;
  @Bind(R.id.fabMapMarker) FloatingActionButton fabMapMarker;
  @Bind(R.id.tvFaves) TextView tvFaves;
  @Bind(R.id.tvCuisine) TextView tvCuisine;
  @Bind(R.id.tvAddress) TextView tvAddress;
  @Bind(R.id.tvPhone) TextView tvPhone;
  @Bind(R.id.tvWeb) TextView tvWeb;
  @Bind(R.id.tvEmail) TextView tvEmail;

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

    int colorTitle = getResources().getColor(R.color.white);

    CollapsingToolbarLayout collapsingToolbar =
        (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(place.getName());
    collapsingToolbar.setCollapsedTitleTextColor(colorTitle);
    collapsingToolbar.setExpandedTitleColor(colorTitle);

    loadBackdrop();
  }

  private void loadBackdrop() {
    int colorPrimary = getResources().getColor(R.color.primary);

    Drawable imgMapMarker = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.MAP_MARKER)
        .setColor(getResources().getColor(R.color.white))
        .build();

    Drawable imgFave = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.HEART)
        .setColor(colorPrimary)
        .build();

    Drawable imgCuisine = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.SILVERWARE_VARIANT)
        .setColor(colorPrimary)
        .build();

    Drawable imgAddress = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.HOME)
        .setColor(colorPrimary)
        .build();

    Drawable imgPhone = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.PHONE)
        .setColor(colorPrimary)
        .build();

    Drawable imgEmail = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.EMAIL)
        .setColor(colorPrimary)
        .build();

    Drawable imgWeb = MaterialDrawableBuilder.with(this)
        .setIcon(MaterialDrawableBuilder.IconValue.WEB)
        .setColor(colorPrimary)
        .build();

    //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    fabMapMarker.setImageDrawable(imgMapMarker);

    tvFaves.setText("100");
    tvFaves.setCompoundDrawables(imgFave, null, null, null);

    tvCuisine.setText(place.getCuisine());
    tvCuisine.setCompoundDrawables(imgCuisine, null, null, null);

    tvAddress.setText(place.getAddress());
    tvAddress.setCompoundDrawables(imgAddress, null, null, null);

    tvPhone.setText(place.getPhone());
    tvPhone.setCompoundDrawables(imgPhone, null, null, null);

    tvEmail.setText(place.getEmail());
    tvEmail.setCompoundDrawables(imgEmail, null, null, null);

    tvWeb.setText(place.getWebsite());
    tvWeb.setCompoundDrawables(imgWeb, null, null, null);
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
