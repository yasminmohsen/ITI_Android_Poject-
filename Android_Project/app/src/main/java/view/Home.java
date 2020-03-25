package view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
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

import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;
import Presenter.Presenter;

public class Home extends AppCompatActivity {

   FloatingActionButton floating;
   Toolbar toolbar;

    int x=0;
    private List<Trip> c =new ArrayList<Trip>();
    private Presenter presenter;
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private  Button sync;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter =new Presenter(getApplicationContext());

        recyclerView=findViewById(R.id.recycler);

         toolbar=findViewById(R.id.toolbar);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        floating=(FloatingActionButton)findViewById(R.id.floatingBtn);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),AddNewTrip.class);
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
         c=presenter.getTripPresenter(); // array from room
         if(c.isEmpty()==false) {
             tripAdapter = new TripAdapter(this, c);
             recyclerView.setAdapter(tripAdapter);
         }

         else{

             Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
         }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sync, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
      if(item.getItemId()==R.id.syncBtn)
      {

          //fire base code

          Toast.makeText(getApplicationContext(), " sync", Toast.LENGTH_SHORT).show();

      }


        return super.onOptionsItemSelected(item);
    }
}



