package view;

import android.content.Context;
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

public class TripAdapter extends  RecyclerView.Adapter<TripAdapter.MyViewHolder> {


    private Context context;
    private List<Trip> tripsList;
    private  Presenter presenter;
    private  Home home;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView homeTripName;
        public TextView homeStartPoint;
        public  TextView homeEndPoint;
        public  TextView homeDate;
        public  TextView homeTime;
        public Button homeDelete;
        public Button homeStart;

        public MyViewHolder(View view) {
            super(view);
            homeTripName = view.findViewById(R.id.H_TRIP);
//            homeStartPoint = view.findViewById(R.id.h_start_point);
//            homeEndPoint= view.findViewById(R.id.h_end_point);
          homeDate= view.findViewById(R.id.H_DATE);
////            homeTime= view.findViewById(R.id.h_time);
//            homeDelete= view.findViewById(R.id.h_delete_btn);
//            homeDelete= view.findViewById(R.id.h_delete_btn);
//            homeStart=view.findViewById(R.id.h_start_btn);

        }
    }



    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.tripsList = trips;


    }


    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.  , parent, false);
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

       //     View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        final Trip trip = tripsList.get(position);

        holder.homeTripName.setText(trip.getTripName());
//        holder.homeStartPoint.setText(trip.getStartPoint());
//        holder.homeEndPoint.setText(trip.getEndPoint());
       holder.homeDate.setText(trip.getDate());
//        holder.homeTime.setText(trip.getTime());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.delete(contact);
//                notifyDataSetChanged();
//                long x= contact.getId();
//                String y= ""+(int)x;
//                Toast toast = Toast.makeText(context, y, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }









}
