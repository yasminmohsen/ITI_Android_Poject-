package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android_project.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Contract.AddBase;
import Contract.HomeBase;
import Pojos.Trip;
import Pojos.Users;
import Presenter.AddPresenter;
import view.alarm.AlarmReceiver;
import view.alarm.TripCalenderManager;


import java.util.Calendar;

public class AddNewTrip extends AppCompatActivity implements AddBase {

    EditText tripName;
    Button date;
    Button time;
    TextView dateText;
    TextView timeText;
    EditText notes;
    Button add;
    RadioButton oneDir;
    RadioButton round;
    DatabaseReference databaseReferenceUsers;
    public static final String PrefName = "MyPrefFile";
    public static final String counter = "Counter";
    String flag;


    private List<Trip> c = new ArrayList<Trip>();
    AddPresenter presenter;

    String tripDir;
    String startPointAddress;// for database
    String endPointAddress;//for database

    String showStartPoint;// for ui
    String showEndPoint;// for ui

    Trip roundedTrip;


    Trip addTripObj;

    PlacesClient placesClient;
    List<Place.Field> placeField = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fregment;
    AutocompleteSupportFragment places_fregment_end;


    //alarm manager
    AlarmManager alarmManager;
    Intent myIntent;
    private PendingIntent pendingIntent;
    TripCalenderManager tripAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);


        // instantiate calender
        tripAlarm = new TripCalenderManager();
        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        myIntent = new Intent(this, AlarmReceiver.class);


        addTripObj = new Trip();

        getSupportActionBar().setTitle("Add new trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripName = (EditText) findViewById(R.id.tripName);
        date = (Button) findViewById(R.id.calenderBtn);
        time = (Button) findViewById(R.id.alaramBtn);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView) findViewById(R.id.TimeText);
        notes = (EditText) findViewById(R.id.NotesText);
        add = (Button) findViewById(R.id.AddBtn);
        oneDir = (RadioButton) findViewById(R.id.oneDirection);
        round = (RadioButton) findViewById(R.id.roundBtn);


        presenter = new AddPresenter(getApplicationContext(), this);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("upcoming");



        Intent i = getIntent();
        roundedTrip= (Trip) i.getSerializableExtra("sampleObject");



        final SharedPreferences prefs = AddNewTrip.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long cnt = prefs.getLong(counter, 0);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Trip t = new Trip();
                String tn = tripName.getText().toString();
                String tripId = tn + cnt; // the id

                t.setTripName(tripName.getText().toString());
                t.setTripId(tripId);
                t.setStartPoint(startPointAddress);
                t.setEndPoint(endPointAddress);
                t.setNote(notes.getText().toString());
                t.setDate(dateText.getText().toString());
                t.setTime(timeText.getText().toString());
                t.setTripDirection(tripDir);
                t.setTripStatus("Upcoming");
                t.setStartUi(showStartPoint);
                t.setEndUi(showEndPoint);

                presenter.insertTripPresenter(t);

                //save trip object to sharedpreference
                saveTripToShared(t);

                 // here start pendingIntent
                int serviceId = (int)cnt;
                pendingIntent = PendingIntent.getBroadcast(AddNewTrip.this, serviceId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, tripAlarm.calendar.getTimeInMillis(), pendingIntent);

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripAlarm.showDateDialog(dateText, AddNewTrip.this);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripAlarm.showTimeDialog(timeText, AddNewTrip.this);
            }
        });

        oneDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripDir = "One Direction";

            }
        });

        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripDir = "Round";
                flag="round";

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

    @Override
    protected void onStart() {
        super.onStart();
 // increment th id counter
        final SharedPreferences prefs = AddNewTrip.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long count = prefs.getLong(counter, 0);

        // shared pref :
        long value = prefs.getLong(counter, 0);
        prefs.edit().putLong(counter, (value + 1)).apply();
    }


    private void setupPlaceAutocomlete() {

        places_fregment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        places_fregment.setPlaceFields(placeField);
        places_fregment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                startPointAddress = place.getAddress();
                showStartPoint = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(AddNewTrip.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });


        places_fregment_end = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_end);
        places_fregment_end.setPlaceFields(placeField);
        places_fregment_end.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                endPointAddress = place.getAddress();
                showEndPoint = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(AddNewTrip.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initPlaces() {

        Places.initialize(this, getString(R.string.place_api_key));
        placesClient = Places.createClient(this);
    }

    @Override
    public void showOnSucessAdd() {

        if(flag=="round")
        {
            Intent i = new Intent(getApplicationContext(), RoundTrip.class);
            i.putExtra("start",showStartPoint);
            i.putExtra("end", showEndPoint);
            i.putExtra("startAdd",startPointAddress);
            i.putExtra("endAdd", endPointAddress);

            startActivity(i);
        }
        else{
            Toast.makeText(this, "Added", Toast.LENGTH_LONG);
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }

    }

    @Override
    public void showOnFailFail() {
        Toast.makeText(this, "Fill the Empty Fields ", Toast.LENGTH_SHORT).show();

    }
    // convert string to millisecond
    /*
        String someDate = "05.10.2011";
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date date = sdf.parse(someDate);
        System.out.println(date.getTime());
     */
}
