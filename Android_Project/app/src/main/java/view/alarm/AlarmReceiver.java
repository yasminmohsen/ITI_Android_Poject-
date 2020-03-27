package view.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        IntentFilter filter = new IntentFilter("my.action.data");

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        String action = intent.getAction();
        if(action != null) {
            if (action.equals("my.action.data")) {
                String s = intent.getStringExtra("send");
                if (s != null) {
                    if (s.equals("wait")) {
                        serviceIntent.putExtra("send", "wait");
                    } else if (s.equals("stop")) {
                        serviceIntent.putExtra("send", "stop");
                    } else if (s.equals("run")) {
                        serviceIntent.putExtra("send", "run");
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        }else{
            context.startService(serviceIntent);
        }
    }
}

