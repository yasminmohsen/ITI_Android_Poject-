package view.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class CancelMyAlarm {

    public void cancelAlarm(Context context, int serviceId) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent cancelServiceIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent cancelServicePendingIntent = PendingIntent.getBroadcast(
                context,
                serviceId, // integer constant used to identify the service
                cancelServiceIntent,
                0 //no FLAG needed for a service cancel
        );
        am.cancel(cancelServicePendingIntent);
    }

}