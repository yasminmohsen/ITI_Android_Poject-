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
import com.google.firebase.auth.FirebaseAuth;
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
    String startPointAddress;// for database
    String endPointAddress;//for database

    String showStartPoint;// for ui
    String showEndPoint;// for ui



    PlacesClient placesClient;
    List<Place.Field> placeField = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fregment;
    AutocompleteSupportFragment places_fregment_end;


    int x = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);
        getSupportActionBar().setTitle("Add new trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripName = (EditText) findViewById(R.id.tripName);
        date = (Button) findViewById(R.id.calenderBtn);
        time = (Button) findViewById(R.id.alaramBtn);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView) findViewById(R.id.TimeText);
        notes = (EditText) findViewById(R.id.NotesText);
        add = (Button) findViewById(R.id.AddBtn);
        oneDir=(RadioButton)findViewById(R.id.oneDirection);
        round=(RadioButton)findViewById(R.id.roundBtn);


        presenter = new Presenter(getApplicationContext());
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("upcoming");



        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String i="id"+dateText;
                presenter.insertTripPresenter(tripName.getText().toString(), i, startPointAddress, endPointAddress, notes.getText().toString(),
                        dateText.getText().toString(), timeText.getText().toString(), tripDir, "Upcoming",showStartPoint,showEndPoint);

               // addtoFireBase();

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

              tripDir="One Direction";


            }
        });



        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               tripDir="Round";

            }
        });




        initPlaces();
        setupPlaceAutocomlete();


    }
//    private  void addtoFireBase(){
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String trip = databaseReferenceUsers.push().getKey();
//        Users trp = new Users(tripName.getText().toString(), startPointAddress, endPointAddress,notes.getText().toString(), tripDir, "UpComing",  dateText.getText().toString(), time.getText().toString());
//        databaseReferenceUsers.child(userId).child(trip).setValue(trp);
//    }


    private void setupPlaceAutocomlete() {

        places_fregment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        places_fregment.setPlaceFields(placeField);
        places_fregment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

               startPointAddress= place.getAddress();
               showStartPoint=place.getName();

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
                showEndPoint=place.getName();
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


        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(cl.getTime());


        dateText.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        timeText.setText(hourOfDay + ":" + minute);

    }


}
