package view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.List;

import Pojos.Trip;
import Presenter.Presenter;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{

    private Context context;
    private List<Trip>tripList;


    public class MyViewHolder extends RecyclerView.ViewHolder{



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


    public HistoryAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.tripList = trips;
//        presenter = new Presenter(context, this);


    }





    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Trip trip = tripList.get(position);  // object of the trip

     // write your code here




    }



    @Override
    public int getItemCount() {
        return tripList.size();
    }


}
