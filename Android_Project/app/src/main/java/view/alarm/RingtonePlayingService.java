package view.alarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.android_project.R;

import static view.alarm.NotificationService.CHANNEL_ID;

public class RingtonePlayingService extends Service {

    MediaPlayer mMediaPlayer;

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
            if(s.equals("wait")){
                mMediaPlayer.stop();
            } else if(s.equals("stop")){
                mMediaPlayer.stop();
                stopSelf();
            } else if(s.equals("run")){
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
            }
        }else {

            mMediaPlayer = MediaPlayer.create(this, R.raw.a);
            mMediaPlayer.start();
            mMediaPlayer.setLooping(true);

            Intent notificationIntent = new Intent(this, DialogActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Service")
                    .setContentText("Done")
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(pIntent)
                    .build();

            startForeground(1, notification);



            Intent dialogIntent = new Intent(this, DialogActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

        }

        return START_STICKY;
    }
}