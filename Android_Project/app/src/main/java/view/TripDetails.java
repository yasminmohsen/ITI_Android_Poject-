package view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button read;


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
        read=(Button)findViewById(R.id.read);
//        notes=(TextView) findViewById(R.id.dNotes);



        tripName.setText(trip.getTripName());
        startPnt.setText(trip.getStartUi());
        endPtn.setText(trip.getEndUi());
        date.setText(trip.getDate());
        time.setText(trip.getTime());
        type.setText(trip.getTripDirection());
        status.setText(trip.getTripStatus());
        //notes.setText(trip.getNote());


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), PopUpNote.class);
                i.putExtra("note",trip.getNote());
                startActivity(i);

            }
        });

    }
}
