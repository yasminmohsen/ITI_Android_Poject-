package view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;

import java.util.ArrayList;
import java.util.List;

import Pojos.Trip;
import Presenter.Presenter;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {


    private Context context;
    private List<Trip> tripsList;
    private Presenter presenter;
    private Home home;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView homeTripName;
        public TextView homeStartPoint;
        public TextView homeEndPoint;
        public TextView homeDate;
        public TextView homeTime;
        public Button deleteBtn;
        public Button homeStart;

        public MyViewHolder(View view) {
            super(view);
            homeTripName = view.findViewById(R.id.H_TRIP);
            homeDate = view.findViewById(R.id.H_DATE);
            deleteBtn = view.findViewById(R.id.trashBtn);


        }
    }


    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.tripsList = trips;
        presenter = new Presenter(context);


    }


    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        final Trip trip = tripsList.get(position);

        holder.homeTripName.setText(trip.getTripName());

        holder.homeDate.setText(trip.getDate());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
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

                                        presenter.deleteTripPresenter(trip);
                                        tripsList.remove(position);
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


    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }


}
