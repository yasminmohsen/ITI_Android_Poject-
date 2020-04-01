package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import Contract.EditBase;
import Pojos.Trip;
import Presenter.EditPresenter;
import view.alarm.AlarmReceiver;
import view.alarm.AlarmServiceID;
import view.alarm.TripCalenderManager;

public class EditTrip extends AppCompatActivity implements EditBase {


    private EditText eTripName;
    private TextView eStartPointAddress;
    private TextView eEndPointAddress;
    private Button eDate;
    private Button eTime;
    private TextView eDateText;
    private TextView eTimeText;
    private EditText eNotes;
    private TextView eTripDir;
    private Button save;
    private RadioButton oneDir;
    private RadioButton round;
    private Trip t;
    private String tripId;
    private String tripStatus;
    private String eStartui;
    private String eEndui;
    private String eStartPoint;
    private String eEndPoint;
    private EditPresenter presenter;
    private AutocompleteSupportFragment places_fregment;
    private AutocompleteSupportFragment places_fregment_end;
    private PlacesClient placesClient;
    private List<Place.Field> placeField = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    public static final String PrefName = "MyPrefFile";
    public static final String counter = "Counter";

    //alarm manager
    AlarmManager alarmManager;
    Intent myIntent;
    private PendingIntent pendingIntent;
    TripCalenderManager tripAlarm;

    String flag;
    String vStartui;
    String vEndui;
    String vStartPoint;
    String vEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        // instantiate calender
        tripAlarm = new TripCalenderManager();
        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        myIntent = new Intent(this, AlarmReceiver.class);

        eTripName = (EditText) findViewById(R.id.tripName);
        eStartPointAddress = (TextView) findViewById(R.id.editStartPoint);
        eEndPointAddress = (TextView) findViewById(R.id.editEndPoint);
        eDate = (Button) findViewById(R.id.calenderBtn);
        eTime = (Button) findViewById(R.id.alaramBtn);
        eDateText = (TextView) findViewById(R.id.dateText);
        eTimeText = (TextView) findViewById(R.id.TimeText);
        eNotes = (EditText) findViewById(R.id.NotesText);
        eTripDir = (TextView) findViewById(R.id.editTripDir);
        save = (Button) findViewById(R.id.AddBtn);
        oneDir = (RadioButton) findViewById(R.id.oneDirection);
        round = (RadioButton) findViewById(R.id.roundBtn);
        presenter = new EditPresenter(getApplicationContext(), this);


        Intent i = getIntent();
        t = (Trip) i.getSerializableExtra("sampleObject");
        tripStatus = t.getTripStatus();

        vStartui = t.getStartUi();
        vEndui = t.getEndUi();
        vStartPoint = t.getStartPoint();
        vEndPoint = t.getEndPoint();
        final SharedPreferences prefs = EditTrip.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long cnt = prefs.getLong(counter, 0);


        eTripName.setText(t.getTripName());
        eStartPointAddress.setText(t.getStartUi());
        eEndPointAddress.setText(t.getEndUi());

        eDateText.setText(t.getDate());
        eTimeText.setText(t.getTime());
        eNotes.setText(t.getNote());
        eTripDir.setText(t.getTripDirection());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tn = eTripName.getText().toString();
                String tripId = t.getTripId();

                t.setTripName(eTripName.getText().toString());

                if (flag != "pressed") {
                    t.setStartPoint(vStartPoint);
                    t.setEndPoint(vEndPoint);
                    t.setStartUi(vStartui);
                    t.setEndUi(vEndui);

                } else {

                    t.setStartUi(eStartui);
                    t.setEndUi(eEndui);
                    t.setStartPoint(eStartPoint);
                    t.setEndPoint(eEndui);
                }


                t.setDate(eDateText.getText().toString());
                t.setTime(eTimeText.getText().toString());
                t.setTripDirection(eTripDir.getText().toString());
                t.setNote(eNotes.getText().toString());

                t.setTripStatus(tripStatus);


                presenter.updateTripPresenter(t);

                saveTripToShared(t);

                // here start pendingIntent
                int serviceId = new AlarmServiceID().getAlarmServiceId(tripId);

                Bundle args = new Bundle();
                args.putSerializable("obj", (Serializable) t);
                myIntent.putExtra("Data", args);

                pendingIntent = PendingIntent.getBroadcast(EditTrip.this, serviceId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, tripAlarm.calendar.getTimeInMillis(), pendingIntent);


            }
        });


        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripAlarm.showDateDialog(eDateText, EditTrip.this);
            }
        });


        eTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripAlarm.showTimeDialog(eTimeText, EditTrip.this);
            }
        });


        oneDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eTripDir.setText("One Direction");

            }
        });


        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eTripDir.setText("Round");


            }
        });
        initPlaces();
        setupPlaceAutocomlete();


    }

    private void saveTripToShared(Trip t) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(t);
        editor.putString("trip", json);
        editor.apply();
    }

    private void setupPlaceAutocomlete() {

        places_fregment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        places_fregment.setPlaceFields(placeField);
        places_fregment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                eStartui = place.getName();
                eStartPoint = place.getAddress();
                eStartPointAddress.setText(eStartui);
                flag = "pressed";

            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(EditTrip.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });


        places_fregment_end = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_end);
        places_fregment_end.setPlaceFields(placeField);
        places_fregment_end.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {


                eEndPoint = place.getAddress();
                eEndui = place.getName();
                eEndPointAddress.setText(eEndui);

                flag = "pressed";

            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(EditTrip.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initPlaces() {

        Places.initialize(this, getString(R.string.place_api_key));
        placesClient = Places.createClient(this);
    }


    @Override
    public void showOnSucessEdit() {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }

    @Override
    public void showOnFailEdit() {

    }

}
