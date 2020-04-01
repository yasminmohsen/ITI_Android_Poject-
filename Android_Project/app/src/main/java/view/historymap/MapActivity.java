package view.historymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.android_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Pojos.Trip;
import view.History;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    List<Trip> historyTrip;
    //GeoLocation geolocation;
     ArrayList<MyPoints> pointsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        historyTrip = History.c;

        //geolocation = new GeoLocation();

        pointsList = new ArrayList<>();

        for(Trip t : historyTrip ){
            //geolocation.getAddress(t.getStartPoint(), getApplicationContext(), new GeoHandler());
            //geolocation.getAddress(t.getEndPoint(), getApplicationContext(), new GeoHandler());
            getAddressFromLocation(t.getStartPoint());
            getAddressFromLocation(t.getEndPoint());
        }

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        for(int i = 0; i < pointsList.size(); i+=2){
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .color(Color.BLUE)
                    .add(
                            new LatLng(pointsList.get(i).lng, pointsList.get(i).lat),
                            new LatLng(pointsList.get(i+1).lng, pointsList.get(i+1).lat)
                    ));
            LatLng sydney = new LatLng(pointsList.get(i).lng, pointsList.get(i).lat);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(""));

            LatLng des = new LatLng(pointsList.get(i+1).lng, pointsList.get(i+1).lat);
            googleMap.addMarker(new MarkerOptions().position(des)
                    .title(""));
        }

        // Position the map's camera near Alice Springs in the center of Egypt,
        // and set the zoom factor so most of Egypt shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.8206, 30.8025), 6));
    }

    private void getAddressFromLocation(String s) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);


        try {
            List<Address> addresses = geocoder.getFromLocationName(s, 1);

            if (addresses.size() > 0) {
                Address address =  (Address)addresses.get(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(address.getLatitude()).append(",");
                stringBuilder.append(address.getLongitude());
                String result = stringBuilder.toString();

                String[] arrOfStr = result.split(",");
                double lng=Double.parseDouble(arrOfStr[0]);
                double lat=Double.parseDouble(arrOfStr[1]);
                MyPoints p = new MyPoints(lng, lat);
                pointsList.add(p);

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
            //printToast("Could not get address..!");
        }
    }

    /*private class GeoHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            String address;
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    address = bundle.getString("address");
                    break;
                    default:
                        address = null;
            }
            String[] arrOfStr = address.split(",");
            double lng=Double.parseDouble(arrOfStr[0]);
            double lat=Double.parseDouble(arrOfStr[1]);
            MyPoints p = new MyPoints(lng, lat);
            pointsList.add(p);
        }
    }*/
}