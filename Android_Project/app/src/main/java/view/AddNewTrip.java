package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Pojos.Trip;
import Pojos.Users;
import Presenter.Presenter;

import java.util.Calendar;

public class AddNewTrip extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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



    private List<Trip> c = new ArrayList<Trip>();
    Presenter presenter;

    String tripDir;
    String startPointAddress;
    String endPointAddress;



    PlacesClient placesClient;
    List<Place.Field> placeField = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fregment;
    AutocompleteSupportFragment places_fregment_end;


    int x = 0;
    int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);
        getSupportActionBar().setTitle("Add new trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripName = (EditText) findViewById(R.id.tripName);
  //      startPoint = (EditText) findViewById(R.id.startPointText);
//        endPoint = (EditText) findViewById(R.id.endPointTextEdit);
        date = (Button) findViewById(R.id.calenderBtn);
        time = (Button) findViewById(R.id.alaramBtn);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView) findViewById(R.id.TimeText);
        notes = (EditText) findViewById(R.id.NotesText);
        add = (Button) findViewById(R.id.AddBtn);
        oneDir=(RadioButton)findViewById(R.id.oneDirection);
        round=(RadioButton)findViewById(R.id.roundBtn);

        presenter = new Presenter(getApplicationContext());
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("User");




        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String i="id"+x;
                presenter.insertTripPresenter(tripName.getText().toString(), i, startPointAddress, endPointAddress, notes.getText().toString(),
                        dateText.getText().toString(), time.getText().toString(), tripDir, "Upcoming");

                //addtoFireBase();

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);


            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");


            }
        });



        oneDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              tripDir="oneDirection";

            }
        });



        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               tripDir="round";

            }
        });




        initPlaces();
        setupPlaceAutocomlete();


    }
//    private  void addtoFireBase(){
//        String id = databaseReferenceUsers.push().getKey();
//        Users trp = new Users(tripName.getText().toString(), startPointAddress, endPointAddress,notes.getText().toString(), tripDir, "UpComing",  dateText.getText().toString(), time.getText().toString());
//        databaseReferenceUsers.child(id).setValue(trp);
//    }


    private void setupPlaceAutocomlete() {

        places_fregment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        places_fregment.setPlaceFields(placeField);
        places_fregment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

               startPointAddress= place.getAddress();

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


                endPointAddress= place.getAddress();
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cl = cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, month);
        cl.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(cl.getTime());

        dateText.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        timeText.setText(hourOfDay + ":" + minute);

    }

    @Override
    protected void onStart() {
        super.onStart();
        x++;
    }
}
