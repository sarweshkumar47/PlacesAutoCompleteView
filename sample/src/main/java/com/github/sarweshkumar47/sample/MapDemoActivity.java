package com.github.sarweshkumar47.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.github.sarweshkumar47.placesautocompleteview.Models.PlacesCustomInfo;
import com.github.sarweshkumar47.placesautocompleteview.PlacesAutoCompleteView;

import org.oscim.android.MapView;
import org.oscim.android.cache.TileCache;
import org.oscim.backend.canvas.Bitmap;
import org.oscim.core.BoundingBox;
import org.oscim.core.GeoPoint;
import org.oscim.layers.marker.ItemizedLayer;
import org.oscim.layers.marker.MarkerItem;
import org.oscim.layers.marker.MarkerSymbol;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Layers;
import org.oscim.map.Map;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.TileSource;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import java.util.ArrayList;

import static org.oscim.android.canvas.AndroidGraphics.drawableToBitmap;

public class MapDemoActivity extends AppCompatActivity {

    private PlacesAutoCompleteView placesAutoCompleteView;
    private PlacesCustomInfo placesCustomInfo;
    private Map mMap;
    private Double mLatitude = 0.0, mLongitude = 0.0;
    private ItemizedLayer<MarkerItem> placesLayer;
    private String TAG = "MapActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_demo_activity);

        Log.d(TAG, "PlacesAutoComplete, MapDemoActivity onCreate");
        MapView mMapView = (MapView) findViewById(R.id.mapView);
        ImageButton myLocationButton = (ImageButton) findViewById(R.id.myLocation);
        placesAutoCompleteView = (PlacesAutoCompleteView) findViewById(R.id.searchLayer);

        mMap = mMapView.map();

        TileSource mTileSource = new OSciMap4TileSource();

        TileCache mCache = new TileCache(this, null, "opensciencemap-tiles.db");
        mCache.setCacheSize(512 * (1 << 10));
        mTileSource.setCache(mCache);
        VectorTileLayer mBaseLayer = mMap.setBaseMap(mTileSource);
        Layers layers = mMap.layers();
        layers.add(new BuildingLayer(mMap, mBaseLayer));
        layers.add(new LabelLayer(mMap, mBaseLayer));

        mMap.setTheme(VtmThemes.DEFAULT);

        Bitmap markerBlueBitmap = drawableToBitmap(getResources(), R.drawable.marker_blue_pin_128px);
        final MarkerSymbol myLocationMarker = new MarkerSymbol(markerBlueBitmap, MarkerItem.HotspotPlace.CENTER);

        Bitmap markerRedBitmap = drawableToBitmap(getResources(), R.drawable.marker_pin_128px);
        MarkerSymbol placesMarker = new MarkerSymbol(markerRedBitmap, MarkerItem.HotspotPlace.CENTER);
        placesLayer = new ItemizedLayer<>(mMap, placesMarker);
        mMap.layers().add(placesLayer);
        final ItemizedLayer<MarkerItem> locationLayer = new ItemizedLayer<>(mMap, myLocationMarker);

        locationLayer.addItem(new MarkerItem("", "", new GeoPoint(13.018624, 77.570836)));
        mMap.layers().add(locationLayer);

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "PlacesAutoComplete, MapDemoActivity myLocationButton click");
                mMap.viewport().setRotation(0);
                mMap.viewport().setTilt(0);
                mMap.animator().animateTo(500, new GeoPoint(13.018624, 77.570836), 1 << 16, false);
            }
        });

        placesAutoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                placesCustomInfo = (PlacesCustomInfo) parent.getItemAtPosition(position);
                placesAutoCompleteView.setText(placesCustomInfo.getPlaceName());
                placesAutoCompleteView.setSelected(true);
                mLatitude = placesCustomInfo.getLatitude();
                mLongitude = placesCustomInfo.getLongitude();
                goToGeoBoundingBox(placesCustomInfo.getGeoBoundingBox());
                placesAutoCompleteView.dismissDropDown();
            }
        });
    }

    private void goToGeoBoundingBox(ArrayList<Double> arrayList) {
        Log.d(TAG, "PlacesAutoComplete, MapDemoActivity goToGeoBoundingBox call");
        new MapAsyncLoad().execute(arrayList.get(0), arrayList.get(2), arrayList.get(1), arrayList.get(3));
    }

    private class MapAsyncLoad extends AsyncTask<Double, Void, Void> {

        BoundingBox bx;
        @Override
        protected Void doInBackground(Double... params) {

            bx = new BoundingBox(params[0], params[1],
                    params[2], params[3]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hideSoftKeyboard();
            mMap.animator().animateTo(500, bx);
            //mMap.layers().remove(placesLayer);
            placesLayer.removeAllItems();
            placesLayer.addItem(new MarkerItem("", "", new GeoPoint(mLatitude, mLongitude)));
            //mMap.layers().add(placesLayer);
        }
    }


    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                .getWindowToken(), 0);
    }
}
