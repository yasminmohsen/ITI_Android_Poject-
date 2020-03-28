package view.alarm;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.R;

import java.net.URI;

public class DialogActivity extends AppCompatActivity {

    Button snoozeBtn;
    Button startBtn;
    Button cancelBtn;
    AlarmReceiver alarmReceiver;

    MediaPlayer mMediaPlayer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        alarmReceiver = new AlarmReceiver();

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
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
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
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }
}
