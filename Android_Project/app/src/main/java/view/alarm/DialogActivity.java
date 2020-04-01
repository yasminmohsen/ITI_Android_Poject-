package view.alarm;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.android_project.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import Contract.HistoryBase;
import Contract.HomeBase;
import Pojos.Trip;
import Presenter.Presenter;
import Presenter.HistoryPresenter;

public class DialogActivity extends Activity implements HomeBase, HistoryBase {

    Button snoozeBtn;
    Button startBtn;
    Button cancelBtn;
    AlarmReceiver alarmReceiver;

    MediaPlayer mMediaPlayer;
    Intent intent;

    Trip tripDialog;

    String source;
    String destination;
    Trip tripData;

    BubblesManager bubblesManager;
    private int MY_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        final Presenter presenter= new Presenter(getApplicationContext(),this);
        final HistoryPresenter historyPresenter= new HistoryPresenter(this);


        Intent dIntent = getIntent();
        Bundle args = dIntent.getBundleExtra("Data");
        if(args != null) {
             tripData = (Trip) args.getSerializable("obj");
        }
        this.setFinishOnTouchOutside(false);

        alarmReceiver = new AlarmReceiver();

        startFloatingButton();

        //Get Trip Object
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("trip", null);
        Type type = new TypeToken<Trip>(){}.getType();
        tripDialog = gson.fromJson(json, type);

        if(tripDialog != null){

            source = tripDialog.getStartPoint();
            destination = tripDialog.getEndPoint();
            String sss = tripDialog.getTripId();
            Toast.makeText(this, destination+"  "+source, Toast.LENGTH_LONG).show();
        }

        //register for receiver
        IntentFilter filter = new IntentFilter("my.action.data");
        registerReceiver(alarmReceiver, filter);
        intent = new Intent("my.action.data");
        intent.putExtra("send", "stop");
        intent.setAction("my.action.data");

        //start media play sound
        mMediaPlayer = MediaPlayer.create(this, R.raw.a);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);


        snoozeBtn = findViewById(R.id.snooze_btn);
        startBtn = findViewById(R.id.start_btn);
        cancelBtn = findViewById(R.id.cancel_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start Activity

                /****** handling trip database****/
                tripData.setTripStatus("Done");
               presenter.deleteTripPresenter(tripData);
                historyPresenter.addToFireBaseHistory(tripData);
                /****** handling trip database****/


                Uri gmmIntentUri = Uri.parse("google.navigation:q="+source+","+destination);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);




                mMediaPlayer.stop();

                ///////////////////////////////////////////////////////////////

                //Floating Button Permission
                if(Build.VERSION.SDK_INT >= 23) {
                    if(!Settings.canDrawOverlays(DialogActivity.this)) {
                        Intent floatIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                        startActivityForResult(floatIntent, MY_PERMISSION);
                    }
                }else{
                    Intent floatIntent = new Intent(DialogActivity.this, Service.class);
                    startService(floatIntent);
                }
                addNewBubble();

                ///////////////////////////////////////////////////////////////
                //sendBroadcast(intent);
                // put Trip id
                //new CancelMyAlarm().cancelAlarm(DialogActivity.this, new AlarmServiceID().getAlarmServiceId(tripDialog.getTripId()));

                finish();
            }
        });

        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMediaPlayer.stop();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mMediaPlayer.stop();

                /****** handling trip database****/
               tripData.setTripStatus("Cancelled");
               presenter.deleteTripPresenter(tripData);
               historyPresenter.addToFireBaseHistory(tripData);
                /****** handling trip database****/
                sendBroadcast(intent);

                finish();
                // put Trip id
                //new CancelMyAlarm().cancelAlarm(DialogActivity.this, new AlarmServiceID().getAlarmServiceId(tripDialog.getTripId()));
            }
        });
    }

    private void startFloatingButton() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_remove)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    private void addNewBubble() {

        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(this)
                .inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                Toast.makeText(DialogActivity.this, "Removed", Toast.LENGTH_SHORT).show();
            }
        });

        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {
            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Toast.makeText(DialogActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                String s = readFromShared();
                if(s.equals("f") || s.equals("false")) {

                    intent.putExtra("send", "notes");
                    sendBroadcast(intent);
                    writeInShared("t");
                }
            }
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }

    //write in shared preference
    void writeInShared(String s) {
        SharedPreferences msgPref = getSharedPreferences("msg", 0);
        SharedPreferences.Editor editor = msgPref.edit();
        editor.putString("msg", s);
        editor.commit();
    }

    String readFromShared() {
        SharedPreferences msgPref = getSharedPreferences("msg", 0);
        String msg = msgPref.getString("msg", "false");
        return msg;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
        bubblesManager.recycle();
    }

    @Override
    public void showOnSuccessHistory(List<Trip> tripList) {

    }

    @Override
    public void showOnFailHistory() {

    }

    @Override
    public void showOnSucess(List<Trip> tripList) {

    }

    @Override
    public void showOnFail() {

    }

    @Override
    public void showOnSucessFirebase(List<Trip> tripList) {

    }

    @Override
    public void showOnFaiIntenetConnet() {

    }
}
