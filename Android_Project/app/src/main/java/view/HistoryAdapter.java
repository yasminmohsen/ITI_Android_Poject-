package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;

import java.util.List;

import Contract.HistoryBase;
import Pojos.Trip;
import Presenter.HistoryPresenter;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.MyViewHolder>implements HistoryBase{

    private Context context;
    private List<Trip>tripList;
    private HistoryPresenter historyPresenter;




    public class MyViewHolder extends RecyclerView.ViewHolder  {



        private TextView tripTitle;
        private TextView dateTitle;
        private TextView statusTitle;
        private Button trashBtn;
        private Button showDetaisBtn;



        public MyViewHolder(View view) {
            super(view);
            tripTitle=view.findViewById(R.id.tripTitle);
            dateTitle=view.findViewById(R.id.dateTitle);
            statusTitle=view.findViewById(R.id.statusTitle);
            trashBtn=view.findViewById(R.id.hTrashBtn);
            showDetaisBtn=view.findViewById(R.id.hShowDetailsBtn);

        }


    }


    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_item, parent, false);


      return new MyViewHolder(itemView);
    }


    public HistoryAdapter(Context context, List<Trip> trips,HistoryBase hB) {
        this.context = context;
        this.tripList = trips;
        historyPresenter=new HistoryPresenter(this);

    }





    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        final Trip trip = tripList.get(position);  // object of the trip

        String status;
     // write your code here

        holder.tripTitle.setText(trip.getTripName());
        holder.dateTitle.setText(trip.getDate());
        status=trip.getTripStatus();

        if(status.equals("Done"))
        {

            holder.statusTitle.setText("Done");
            holder.statusTitle.setBackgroundColor(-16711936 );


        }
        else{

            holder.statusTitle.setText("Cancelled");
            holder.statusTitle.setBackgroundColor(-65536);

        }


        holder.trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(context);


                builder.setMessage("Do you want to Delete Trip ?");


                builder.setTitle("Alert !");

                builder.setCancelable(false);


                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast toast = Toast.makeText(context, "deleted", Toast.LENGTH_SHORT);
                                        toast.show();
                                        historyPresenter.removeTripFromHistoryFireBase(trip);
                                        tripList.remove(position);
                                        notifyDataSetChanged();


                                    }
                                });


                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {


                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        });

        holder.showDetaisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, TripDetails.class);
                i.putExtra("sampleObject", trip);
                context.startActivity(i);

            }
        });

    }


    @Override
    public void showOnSuccessHistory(List<Trip> tripList) {

    }

    @Override
    public void showOnFailHistory() {

    }
    @Override
    public int getItemCount() {
        return tripList.size();
    }


}
