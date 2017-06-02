package com.multimediainformatika.bukabengkel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.multimediainformatika.bukabengkel.fragment.ListBengkelMotorFragment;
import com.multimediainformatika.bukabengkel.fragment.MapsDetailFragment;

/**
 * Created by eksmud on 30/04/2017.
 */

public class ServiceMotor extends AppCompatActivity{

    private LocationManager mgr = null;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private Dialog dialog;
    private ListBengkelMotorFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_motor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("SERVICE MOTOR");

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            gps_enabled = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = mgr
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            System.out.println("GPS NETWORK ERROR");
        }

        ambilLokasi();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = (ListBengkelMotorFragment) fragmentManager.findFragmentById(R.id.fragment1);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ambilLokasi() {
        // TODO Auto-generated method stub
        dialog = makeProgressDialog("Sedang Mencari Lokasi...");
        dialog.show();
        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000,
                    3000, locationListenerGps);
        }

        if (network_enabled) {
            mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000,
                    3000, locationListenerNetwork);
        }
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {

            mgr.removeUpdates(this);
            mgr.removeUpdates(locationListenerNetwork);
            if (dialog != null)
                dialog.dismiss();

            fragment.updateToServer("1",String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        }

        public void onProviderDisabled(String provider) {
            // required for interface, not used
        }

        public void onProviderEnabled(String provider) {
            // required for interface, not used
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // required for interface, not used
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            mgr.removeUpdates(this);
            mgr.removeUpdates(locationListenerGps);
            if (dialog != null)
                dialog.dismiss();
            fragment.updateToServer("1",String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
        }

        public void onProviderDisabled(String provider) {
            // required for interface, not used
        }

        public void onProviderEnabled(String provider) {
            // required for interface, not used
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // required for interface, not used
        }
    };

    public ProgressDialog makeProgressDialog(String message) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(message);
        return dialog;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (dialog != null)
            dialog.dismiss();
        mgr.removeUpdates(locationListenerGps);
        mgr.removeUpdates(locationListenerNetwork);
    }
}
