package view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Contract.HomeBase;
import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;
import Presenter.Presenter;

public class Home extends AppCompatActivity implements HomeBase {

    FloatingActionButton floating;
    Toolbar toolbar;


    private List<Trip> c = new ArrayList<Trip>();
    private Presenter presenter;
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private Button sync;
    public static final String PrefName = "MyPrefFile";
    public static final String counter = "Counter";

    HomeBase h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter = new Presenter(getApplicationContext(), this);

        recyclerView = findViewById(R.id.recycler);

        toolbar = findViewById(R.id.toolbar);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        final SharedPreferences prefs = Home.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        final long count = prefs.getLong(counter, 0);

        floating = (FloatingActionButton) findViewById(R.id.floatingBtn);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddNewTrip.class);

                startActivity(intent);


            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //test

    }


    @Override
    protected void onStart() {
        super.onStart();


        presenter.getTripPresenter();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sync, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.syncBtn) {
//            onStart();
            presenter.addTriptoFirebase(this.c);
            Toast.makeText(getApplicationContext(), "sync", Toast.LENGTH_SHORT).show();

        }


        return super.onOptionsItemSelected(item);
    }


    public void showData(List<Trip> t) {
        this.c = t;

    }

    @Override
    public void showOnSucess(List<Trip> tripList) {
        this.c = tripList;
        tripAdapter = new TripAdapter(this, c);
        recyclerView.setAdapter(tripAdapter);
    }

    @Override
    public void showOnFail() {

    }

    @Override
    public void showOnSucessFirebase(List<Trip> tripList) {
        Toast.makeText(this,"open internet connection",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOnFaiIntenetConnet() {
Toast.makeText(this,"open internet connection",Toast.LENGTH_LONG).show();
    }




    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

}



