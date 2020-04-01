package view.alarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.android_project.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Random;

import Pojos.Trip;

import static view.alarm.NotificationService.CHANNEL_ID;

public class RingtonePlayingService extends Service {

    private WindowManager wm;
    private LinearLayout linearLayout;
    private ImageView stopWM_IV;
    private TextView notesTxt;

    public RingtonePlayingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String s = intent.getStringExtra("send");
        if(s != null){
            if(s.equals("stop")){
                stopSelf();
            } else if(s.equals("notes")){
                // run dialog of notes
                String read = readFromShared();
                if(read.equals("t")) {
                    startNotesWindow();
                }
            }
        }else {

            Intent dialogIntent = new Intent(this, DialogActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle args = intent.getBundleExtra("Data");
            Trip serTrip = (Trip) args.getSerializable("obj");

            if(serTrip != null) {
                Bundle sevArgs = new Bundle();
                args.putSerializable("obj",(Serializable)serTrip);
                dialogIntent.putExtra("Data",args);
            }
            startActivity(dialogIntent);

            Intent notificationIntent = new Intent(this, DialogActivity.class);
            if(serTrip != null) {
                Bundle sevArgs = new Bundle();
                args.putSerializable("obj",(Serializable)serTrip);
                notificationIntent.putExtra("Data",args);
            }
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Service")
                    .setContentText("Done")
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(pIntent)
                    .build();
            startForeground(1, notification);
        }

        return START_STICKY;
    }

    Trip getTripObj() {
        //Get Trip Object
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("trip", null);
        Type type = new TypeToken<Trip>(){}.getType();
        Trip tripService = gson.fromJson(json, type);
        return tripService;
    }

    void startNotesWindow(){

        Trip myTrip = getTripObj();

        wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        linearLayout = new LinearLayout(this);
        stopWM_IV = new ImageView(this);

        notesTxt = new TextView(this);

        ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        stopWM_IV.setImageResource(R.drawable.ic_clear);
        notesTxt.setText(myTrip.getNote());
        ViewGroup.LayoutParams txtParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        stopWM_IV.setLayoutParams(btnParams);
        notesTxt.setLayoutParams(txtParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(Color.argb(66, 255, 0, 0));
        linearLayout.setLayoutParams(params);
        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(450, 250, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER|Gravity.CENTER;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(stopWM_IV);
        linearLayout.addView(notesTxt);
        wm.addView(linearLayout, parameters);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParameters = parameters;
            int x, y;
            float touchedX, touchedY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParameters.x;
                        y = updatedParameters.y;
                        touchedX = event.getRawX();
                        touchedY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (event.getRawX()-touchedX));
                        updatedParameters.y = (int) (y + (event.getRawY()-touchedY));
                        wm.updateViewLayout(linearLayout, updatedParameters);
                    default:
                        break;
                }
                return false;
            }
        });

        stopWM_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            wm.removeView(linearLayout);
            writeInShared("f");
            }
        });
    }

    //write in shared preference
    void writeInShared(String s) {
        SharedPreferences msgPref = getSharedPreferences("msg", 0);
        SharedPreferences.Editor editor = msgPref.edit();
        editor.putString("msg", s);
        editor.commit();
    }

    //read in shared preference
    String readFromShared() {
        SharedPreferences msgPref = getSharedPreferences("msg", 0);
        String msg = msgPref.getString("msg", "false");
        return msg;
    }
}