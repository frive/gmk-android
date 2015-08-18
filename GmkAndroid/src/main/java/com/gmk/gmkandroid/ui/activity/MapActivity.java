package com.gmk.gmkandroid.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gmk.gmkandroid.R;
import com.gmk.gmkandroid.GmkApplication;

public class MapActivity extends BaseActivity {
  private GmkApplication app;

  @Bind(R.id.mapview) MapView mv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    ButterKnife.bind(this);

    app = (GmkApplication) getApplication();

    mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
    mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
    mv.setCenter(mv.getTileProvider().getCenterCoordinate());

    // Show user location (purposely not in follow mode)
    mv.setUserLocationEnabled(true);
    mv.setUserLocationTrackingMode(UserLocationOverlay.TrackingMode.FOLLOW);

    // Retrieve UserLocation
    final LatLng userLocation = mv.getUserLocation();

    Map query  = new HashMap();
    query.put("lat", userLocation.getLatitude());
    query.put("lon", userLocation.getLongitude());
    query.put("distance", 3);

    app.gmkAPI.searchPlacesGeoJson(query, new Callback<Response>() {
      @Override public void success(Response resp, Response response) {
        try {
          GeoJSONObject geoJSON = GeoJSON.parse(resp.getBody().in());
          FeatureCollection featureCollection = (FeatureCollection) geoJSON;

          for (Feature f : featureCollection.getFeatures()) {
            JSONArray coordinates = (JSONArray) f.getGeometry().toJSON().get("coordinates");
            JSONObject markerProps =
                f.getProperties().getJSONObject("mapbox").getJSONObject("marker");

            double lat = (Double) coordinates.get(0);
            double lon = (Double) coordinates.get(1);

            Marker marker = new Marker(mv, "", "", new LatLng(lat, lon));
            marker.setIcon(
                new Icon(getApplicationContext(), Icon.Size.LARGE, markerProps.getString("symbol"),
                    markerProps.getString("color")));
            mv.addMarker(marker);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override public void failure(RetrofitError retrofitError) {
        // Log error here since request failed
        throw retrofitError;
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_map, menu);
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
