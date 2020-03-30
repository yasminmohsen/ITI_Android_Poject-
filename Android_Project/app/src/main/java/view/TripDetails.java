package view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android_project.R;

import Pojos.Trip;

public class TripDetails extends AppCompatActivity {
    private Trip trip;
    private TextView tripName;
    private TextView startPnt;
    private TextView endPtn;
    private TextView date;
    private TextView time;
    private TextView type;
    private TextView status;
    private TextView notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        Intent i = getIntent();
        trip = (Trip) i.getSerializableExtra("sampleObject");


        tripName=(TextView) findViewById(R.id.tripName);
        startPnt=(TextView) findViewById(R.id.dStart);
        endPtn=(TextView) findViewById(R.id.dEnd);
        date=(TextView) findViewById(R.id.dDate);
        time=(TextView) findViewById(R.id.dTime);
        type=(TextView) findViewById(R.id.dType);
        status=(TextView) findViewById(R.id.dStatus);
        notes=(TextView) findViewById(R.id.dNotes);



        tripName.setText(trip.getTripName());
        startPnt.setText(trip.getStartUi());
        endPtn.setText(trip.getEndUi());
        date.setText(trip.getDate());
        time.setText(trip.getTime());
        type.setText(trip.getTripDirection());
        status.setText(trip.getTripStatus());
        notes.setText(trip.getNote());


    }
}
