package com.gmk.gmkandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.model.Place;
import com.gmk.gmkandroid.model.Mapbox;

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
  @Bind(R.id.mbxvLocation) MapView mbxvLocation;
  @Bind(R.id.tvOpHrs) TextView tvOpHrs;
  @Bind(R.id.tvParking) TextView tvParking;
  @Bind(R.id.tvWifi) TextView tvWifi;
  @Bind(R.id.tvCreditCard) TextView tvCreditCard;
  @Bind(R.id.tvDelivery) TextView tvDelivery;
  @Bind(R.id.tvCatering) TextView tvCatering;

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
    loadMap();
  }

  private void loadMap() {
    double lat = place.getCoordinates().get(0);
    double lon = place.getCoordinates().get(1);
    LatLng loc = new LatLng(lat, lon);

    Mapbox mbxModel = place.getMapbox();

    mbxvLocation.setMinZoomLevel(mbxvLocation.getTileProvider().getMinimumZoomLevel());
    mbxvLocation.setMaxZoomLevel(mbxvLocation.getTileProvider().getMaximumZoomLevel());
    mbxvLocation.setCenter(loc);

    Marker marker = new Marker(mbxvLocation, "", "", loc);
    marker.setIcon(new Icon(this, Icon.Size.LARGE, mbxModel.getMarker().getSymbol(),
            mbxModel.getMarker().getColor()));
    mbxvLocation.addMarker(marker);
  }

  private void loadBackdrop() {
    int colorPrimary = getResources().getColor(R.color.primary);
    int colorWhite = getResources().getColor(R.color.white);

    Drawable imgMapMarker = new IconDrawable(this, MaterialIcons.md_place)
        .color(colorWhite);

    IconDrawable imgFave = new IconDrawable(this, MaterialIcons.md_favorite)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgCuisine = new IconDrawable(this, MaterialIcons.md_local_dining)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgAddress = new IconDrawable(this, MaterialIcons.md_place)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgPhone = new IconDrawable(this, MaterialIcons.md_phone)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgEmail = new IconDrawable(this, MaterialIcons.md_email)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgWeb = new IconDrawable(this, MaterialIcons.md_public)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgOpHrs = new IconDrawable(this, MaterialIcons.md_schedule)
        .color(colorPrimary)
        .actionBarSize();
    IconDrawable imgCheck = new IconDrawable(this, MaterialIcons.md_check_circle)
        .color(colorPrimary)
        .actionBarSize();

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

    tvOpHrs.setText(place.getOperatingHrs());
    tvOpHrs.setCompoundDrawables(imgOpHrs, null, null, null);

    if (place.getParking().length() > 0) {
      tvParking.setVisibility(TextView.VISIBLE);
    }

    if (place.getWifi()) {
      tvWifi.setVisibility(TextView.VISIBLE);
    }

    if (place.getCreditCard()) {
      tvCreditCard.setVisibility(TextView.VISIBLE);
    }

    if (place.getDelivery()) {
      tvDelivery.setVisibility(TextView.VISIBLE);
    }

    if (place.getCatering()) {
      tvCatering.setVisibility(TextView.VISIBLE);
    }

    tvParking.setCompoundDrawables(imgCheck, null, null, null);
    tvWifi.setCompoundDrawables(imgCheck, null, null, null);
    tvCreditCard.setCompoundDrawables(imgCheck, null, null, null);
    tvDelivery.setCompoundDrawables(imgCheck, null, null, null);
    tvCatering.setCompoundDrawables(imgCheck, null, null, null);
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
