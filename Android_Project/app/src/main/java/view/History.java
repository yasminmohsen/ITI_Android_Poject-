package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Contract.HistoryBase;
import Pojos.Trip;
import Presenter.HistoryPresenter;

public class History extends AppCompatActivity implements HistoryBase {

    private List<Trip> c = new ArrayList<Trip>();
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    HistoryPresenter historyPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.historyRecView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        historyPresenter=new HistoryPresenter(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        Intent a = new Intent(History.this,Home.class);
                        startActivity(a);
                    case R.id.history:

                        break;
                    case R.id.sync:
                        Toast.makeText(History.this, "sync", Toast.LENGTH_SHORT).show();
//                        Intent b = new Intent(Home.this,TripDetails.class);
//                        startActivity(b);
                        break;
                }
                return false;
            }
        });












    }


    @Override
    protected void onStart() {
        super.onStart();

        historyPresenter.getTripFromHistoryFireBase();

    }

    @Override
    public void showOnSuccessHistory(List<Trip> tripList) {
        this.c = tripList;
        historyAdapter = new HistoryAdapter(this, c,this);
        recyclerView.setAdapter(historyAdapter);

    }

    @Override
    public void showOnFailHistory() {

    }
}