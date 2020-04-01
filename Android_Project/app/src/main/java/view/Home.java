package view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

/*************/

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                         break;
                    case R.id.history:
                        Toast.makeText(Home.this, "History", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(Home.this,History.class);
                        startActivity(a);
                        break;
                    case R.id.sync:
                        Toast.makeText(Home.this, "sync", Toast.LENGTH_SHORT).show();
                         presenter.addTriptoFirebase(c);

                }
                return false;
            }
        });

/************/




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

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        String pref = prefs.getString("retrieve", " ");//"No name defined" is the default value.

        presenter.getTripPresenter(pref);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sync, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent= new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            //            presenter.addTriptoFirebase(this.c);
           Toast.makeText(getApplicationContext(), "logout Successed", Toast.LENGTH_SHORT).show();

        }


        return super.onOptionsItemSelected(item);
    }


    public void showData(List<Trip> t) {
        this.c = t;

    }

    @Override
    public void showOnSucess(List<Trip> tripList) {

        SharedPreferences.Editor editor = getSharedPreferences("PrefName", MODE_PRIVATE).edit();
        editor.putString("retrieve", "yes");
        editor.apply();

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




    /*********/






}



