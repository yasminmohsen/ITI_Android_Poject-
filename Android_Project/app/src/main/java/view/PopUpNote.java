package view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.android_project.R;

import Pojos.Trip;

public class PopUpNote extends AppCompatActivity {

    private String noteText;
    private TextView myNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_note);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        myNote = (TextView) findViewById(R.id.showNoteText);

        Intent i = getIntent();
        noteText = i.getExtras().getString("note");

        if (noteText == "") {


            myNote.setText("No Notes ");

        } else {
            myNote.setText(noteText);

        }


    }
}
