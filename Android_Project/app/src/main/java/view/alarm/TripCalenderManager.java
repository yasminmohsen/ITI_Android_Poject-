package view.alarm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TripCalenderManager {

    public Calendar calendar;

    public TripCalenderManager(){

        // instantiate calender
        calendar = Calendar.getInstance();
    }

    //Show Date Dialog
    public void showDateDialog(final TextView dateInput, Context activity) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateInput.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(activity, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //show Time Dialog
    public void showTimeDialog(final TextView timeInput, Context context) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                timeInput.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        new TimePickerDialog(context, timeSetListener, calendar.get(calendar.HOUR_OF_DAY), calendar.get(calendar.MINUTE), false).show();
    }
}
