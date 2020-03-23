package view;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android_project.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import Pojos.Trip;
import Presenter.Presenter;

import java.util.Calendar;
public class AddNewTrip extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText tripName;
    EditText startPoint;
    EditText endPoint;
    Button date;
    Button time;
    TextView dateText;
    TextView timeText;
    EditText notes;
    Button add;

    private List<Trip> c =new ArrayList<Trip>();
    Presenter presenter;

    int x=0;
    int y;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);

        tripName = (EditText) findViewById(R.id.tripName);
        startPoint = (EditText) findViewById(R.id.startPointText);
        endPoint = (EditText) findViewById(R.id.endPointTextEdit);
        date = (Button) findViewById(R.id.calenderBtn);
        time = (Button) findViewById(R.id.alaramBtn);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView) findViewById(R.id.TimeText);
        notes = (EditText) findViewById(R.id.NotesText);
        add = (Button) findViewById(R.id.AddBtn);

        presenter = new Presenter(getApplicationContext());


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                x++;
                Trip t = new Trip();

                t.setTripName(tripName.getText().toString());
                t.setTripId(tripName.getText().toString() + x);
                t.setEndPoint(endPoint.getText().toString());
                t.setStartPoint(startPoint.getText().toString());
                t.setNote(notes.getText().toString());
                t.setDate(dateText.getText().toString());
                t.setTime(time.getText().toString());
                presenter.insertTripPresenter(t);


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
}
