package view;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;

import Contract.AddBase;
import Pojos.Trip;
import Presenter.AddPresenter;
import view.alarm.AlarmReceiver;
import view.alarm.AlarmServiceID;
import view.alarm.TripCalenderManager;

public class RoundTrip extends AppCompatActivity implements AddBase {


    private Trip trip;
    private EditText tripName;
    private TextView startPnt;
    private TextView endPtn;
    private TextView date;
    private TextView time;
    private Button dateBtn;
    private Button timeBtn;
    private EditText notes;
    private Button save;
    private  String startLoc;
    private  String endLoc;
    private  String startLocAdd;
    private  String endLocAdd;
    private Trip t;
    private AddPresenter addPresenter;
    public static final String PrefName = "MyPrefFile";
    public static final String counter = "Counter";

    //alarm manager
    AlarmManager alarmManager;
    Intent myIntent;
    private PendingIntent pendingIntent;
    TripCalenderManager tripAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_trip);

        // instantiate calender
        tripAlarm = new TripCalenderManager();
        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        myIntent = new Intent(this, AlarmReceiver.class);

       tripName = (EditText) findViewById(R.id.rTripName);
        startPnt = (TextView) findViewById(R.id.editStartPoint);
        endPtn = (TextView) findViewById(R.id.editEndPoint);
        dateBtn = (Button) findViewById(R.id.calenderBtn);
        timeBtn = (Button) findViewById(R.id.alaramBtn);
        date = (TextView) findViewById(R.id.dateText);
        time = (TextView) findViewById(R.id.TimeText);
        notes = (EditText) findViewById(R.id.NotesText);
        save = (Button) findViewById(R.id.AddBtn);
        trip=new Trip();
        Intent i = getIntent();
        startLoc= i.getExtras().getString("start");
        endLoc= i.getExtras().getString("end");
        startLocAdd= i.getExtras().getString("startAdd");
        endLocAdd= i.getExtras().getString("endAdd");


        final SharedPreferences prefs = RoundTrip.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long cnt = prefs.getLong(counter, 0);
        addPresenter=new AddPresenter(getApplicationContext(),this);

        startPnt.setText(endLoc);
        endPtn.setText(startLoc);



        dateBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                tripAlarm.showDateDialog(date, RoundTrip.this);

            }
        });



        timeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                tripAlarm.showTimeDialog(time, RoundTrip.this);

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = new Trip();
                String name = tripName.getText().toString();
                //String name="hello";
                String id = name + cnt;
                t.setTripName(name);
                t.setTripId(id);
                t.setTripStatus("upcoming");
                t.setEndUi(startLoc);
                t.setStartUi(endLoc);
                t.setTripDirection("Back Trip");
                t.setStartPoint(endLocAdd);
                t.setEndPoint(startLocAdd);
                t.setNote(notes.getText().toString());
                t.setDate(date.getText().toString());
                t.setTime(time.getText().toString());


                addPresenter.insertTripPresenter(t);

                saveTripToShared(t);

                // here start pendingIntent
                int serviceId = new AlarmServiceID().getAlarmServiceId(id);

                pendingIntent = PendingIntent.getBroadcast(RoundTrip.this, serviceId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, tripAlarm.calendar.getTimeInMillis(), pendingIntent);

    }
});

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

        super.onStart();
        // increment th id counter
        final SharedPreferences prefs = RoundTrip.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long count = prefs.getLong(counter, 0);

        // shared pref :
        long value = prefs.getLong(counter, 0);
        prefs.edit().putLong(counter, (value + 1)).apply();

    }

    @Override
    public void showOnSucessAdd() {

        Intent intent=new Intent(RoundTrip.this,Home.class);

        startActivity(intent);
        finish();

    }

    @Override
    public void showOnFailFail() {

      Toast.makeText(getApplicationContext(),"Fill Empty Data",Toast.LENGTH_LONG).show();
    }
}
