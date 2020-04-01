package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import java.util.Locale;

import Contract.HistoryBase;
import Contract.HomeBase;
import Pojos.Trip;
import Presenter.Presenter;
import view.alarm.AlarmServiceID;
import view.alarm.CancelMyAlarm;
import view.alarm.DialogActivity;
import Presenter.HistoryPresenter;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder>  implements HomeBase, HistoryBase {


    private Context context;
    private List<Trip> tripsList;
    private Presenter presenter;
    private Home home;
private  HistoryPresenter historyPresenter;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView homeTripName;
        private TextView homeStartPoint;
        private TextView homeEndPoint;
        private TextView homeDate;
        private TextView homeTime;
        private Button deleteBtn;
        private Button homeStart;
        private Button editBtn;
        private Button cardCancelBtn;
        private Button showBtn;


        public MyViewHolder(View view) {
            super(view);
            homeTripName = view.findViewById(R.id.H_TRIP);
            homeDate = view.findViewById(R.id.H_DATE);
            deleteBtn = view.findViewById(R.id.trashBtn);
            editBtn = view.findViewById(R.id.editBtn);
            homeStart=view.findViewById(R.id.startBtn);
            cardCancelBtn = view.findViewById(R.id.card_cancel_btn);
            showBtn=view.findViewById(R.id.showDetails);

        }
    }


    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.tripsList = trips;
        presenter = new Presenter(context, this);
        historyPresenter=new HistoryPresenter(this);


    }


    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        final Trip trip = tripsList.get(position);  // object of the trip

        holder.homeTripName.setText(trip.getTripName());

        holder.homeDate.setText(trip.getDate());

        holder.showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, TripDetails.class);
                i.putExtra("sampleObject", trip);
                context.startActivity(i);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CancelMyAlarm().cancelAlarm(v.getContext(), new AlarmServiceID().getAlarmServiceId(trip.getTripId()));

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
                                        presenter.deleteFromFireBaseData(trip);
                                        tripsList.remove(position);
                                        notifyDataSetChanged();


                                        /**** alarm manager cancell*****/


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

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, EditTrip.class);
                i.putExtra("sampleObject", trip);
                context.startActivity(i);



            }
        });



        /********** starting alarm manager ********/


        holder.homeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new CancelMyAlarm().cancelAlarm(v.getContext(), new AlarmServiceID().getAlarmServiceId(trip.getTripId()));
               String alarmDate=trip.getDate();
               String alarmTime= trip.getTime();
               String startLoc=trip.getStartPoint();
               String endLoc=trip.getEndPoint();

                Toast.makeText(context, ""+trip.getTripId(), Toast.LENGTH_SHORT).show();

                //write code here

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + trip.getStartPoint() +"," + trip.getEndPoint());//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);


                // add to firebase history
                trip.setTripStatus("Done");

                historyPresenter.addToFireBaseHistory(trip);
                presenter.deleteTripPresenter(trip);
                presenter.deleteFromFireBaseData(trip);
                tripsList.remove(position);
                notifyDataSetChanged();


                /***** alarm manager cancell ****/



            }
        });

        holder.cardCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CancelMyAlarm().cancelAlarm(v.getContext(), new AlarmServiceID().getAlarmServiceId(trip.getTripId()));


                trip.setTripStatus("Cancelled");

                historyPresenter.addToFireBaseHistory(trip);
                presenter.deleteTripPresenter(trip);
                presenter.deleteFromFireBaseData(trip);
                tripsList.remove(position);
                notifyDataSetChanged();


            }
        });




    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }




    @Override
    public void showOnSucess(List<Trip> tripList) {

    }

    @Override
    public void showOnFail() {

    }

    @Override
    public void showOnSucessFirebase(List<Trip> tripList) {

    }

    @Override
    public void showOnFaiIntenetConnet() {

    }

    @Override
    public void showOnSuccessHistory(List<Trip> tripList) {

    }

    @Override
    public void showOnFailHistory() {

    }

}
