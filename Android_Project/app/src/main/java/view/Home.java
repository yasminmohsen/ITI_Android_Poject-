package view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android_project.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //test

    }
}
