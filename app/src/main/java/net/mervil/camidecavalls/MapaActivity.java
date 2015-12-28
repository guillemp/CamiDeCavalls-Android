package net.mervil.camidecavalls;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapaActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Etapa etapa;
    private List<Coord> coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        String tram = getIntent().getStringExtra("tram");
        DatabaseHelper db = new DatabaseHelper(this);
        etapa = db.getEtapa(tram);
        coords = db.getCoordsByTram(tram);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        PolylineOptions options = new PolylineOptions();
        options.color(Color.RED);
        options.width(5);
        options.visible(true);

        for (int i = 0; i < coords.size(); i++) {
            Coord coord = coords.get(i);

            double lat = coord.getLat();
            double lng = coord.getLng();

            LatLng latLng = new LatLng(lat, lng);
            options.add(latLng);
        }

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addPolyline(options);

        Coord startPoint = coords.get(0);
        Coord endPoint = coords.get(coords.size() - 1);

        MarkerOptions startMarker = new MarkerOptions().position(new LatLng(startPoint.getLat(), startPoint.getLng())).title(getString(R.string.start));
        MarkerOptions endMarker = new MarkerOptions().position(new LatLng(endPoint.getLat(), endPoint.getLng())).title(getString(R.string.end));

        mMap.addMarker(startMarker);
        mMap.addMarker(endMarker);

        /*
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startMarker.getPosition());
        builder.include(endMarker.getPosition());
        LatLngBounds bounds = builder.build();

        int padding = 10; // offset from edges of the map in pixels
        CameraUpdate location = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(location);
        */

        //LatLng center = new LatLng(startPoint.getLat(), startPoint.getLng());
        //CameraUpdate location = CameraUpdateFactory.newLatLngZoom(center, 15);
        //mMap.animateCamera(location);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition arg0) {

                Coord startPoint = coords.get(0);
                Coord endPoint = coords.get(coords.size() - 1);

                MarkerOptions startMarker = new MarkerOptions().position(new LatLng(startPoint.getLat(), startPoint.getLng())).title(getString(R.string.start));
                MarkerOptions endMarker = new MarkerOptions().position(new LatLng(endPoint.getLat(), endPoint.getLng())).title(getString(R.string.end));

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(startMarker.getPosition());
                builder.include(endMarker.getPosition());
                LatLngBounds bounds = builder.build();

                int padding = 100; // offset from edges of the map in pixels
                CameraUpdate location = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.moveCamera(location);

                mMap.setOnCameraChangeListener(null);
            }
        });

    }
}
