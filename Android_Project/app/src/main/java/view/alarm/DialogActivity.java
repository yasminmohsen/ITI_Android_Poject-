package view.alarm;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.R;

public class DialogActivity extends AppCompatActivity {

    Button snoozeBtn;
    Button startBtn;
    Button cancelBtn;
    AlarmReceiver alarmReceiver;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        alarmReceiver = new AlarmReceiver();

        snoozeBtn = findViewById(R.id.snooze_btn);
        startBtn = findViewById(R.id.start_btn);
        cancelBtn = findViewById(R.id.cancel_btn);

        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentFilter filter = new IntentFilter("my.action.data");
                registerReceiver(alarmReceiver, filter);
                Intent intent = new Intent("my.action.data");
                intent.putExtra("send", "wait");
                intent.setAction("my.action.data");
                sendBroadcast(intent);
                finish();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter = new IntentFilter("my.action.data");
                registerReceiver(alarmReceiver, filter);
                Intent intent = new Intent("my.action.data");
                intent.putExtra("send", "stop");
                intent.setAction("my.action.data");
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
