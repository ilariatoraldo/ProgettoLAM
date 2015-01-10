package com.example.ila.projectlam;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by Ila on 10/01/2015.
 */
public class Geo extends FragmentActivity {

    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo);
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        mMap = ((MapFragment)
                getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    /**public void localizzazione() {
        LocationListener locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(provider,
                minTime,
                minDistance,
                locListener);
        Location lastKnownlocation=lm.getLastKnownLocation(locationProvider);
        lm.removeUpdates(locationListener);

    }*/
}
