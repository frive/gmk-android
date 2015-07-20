package com.gmk.gmkandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;

public class MainActivity extends ActionBarActivity {

  private MapView mv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mv = (MapView) findViewById(R.id.mapview);
    mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
    mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
    mv.setCenter(mv.getTileProvider().getCenterCoordinate());
    mv.setZoom(0);

    // Show user location (purposely not in follow mode)
    mv.setUserLocationEnabled(true);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_activity_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
