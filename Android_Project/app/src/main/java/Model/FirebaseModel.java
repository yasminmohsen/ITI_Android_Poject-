package Model;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import Contract.AddBase;
import Contract.FirebaseBase;
import Pojos.Trip;
import Pojos.Users;
import Presenter.AddPresenter;

public class FirebaseModel {
    DatabaseReference databaseReferenceUsers;
    Trip trpDetails;
    List<Users> userslist;
    Trip tribObj;
    List<Trip> tripList;
    String userId;
    FirebaseBase f;


    public void addtoFireBase(List<Trip> tArray) {
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("upcoming");
        databaseReferenceUsers.child(userId).removeValue(); // remove method
        trpDetails = new Trip();
        for (int i = 0; i < tArray.size(); i++) {
            trpDetails.setTripId(tArray.get(i).getTripId());
            trpDetails.setTripName(tArray.get(i).getTripName());
            trpDetails.setStartPoint(tArray.get(i).getStartPoint());
            trpDetails.setEndPoint(tArray.get(i).getEndPoint());
            trpDetails.setNote(tArray.get(i).getNote());
            trpDetails.setTripDirection(tArray.get(i).getTripDirection());
            trpDetails.setTripStatus(tArray.get(i).getTripStatus());
            trpDetails.setDate(tArray.get(i).getDate());
            trpDetails.setTime(tArray.get(i).getTime());
            trpDetails.setStartUi(tArray.get(i).getStartUi());
            trpDetails.setEndUi(tArray.get(i).getEndUi());

            databaseReferenceUsers.child(userId).child(tArray.get(i).getTripId()).setValue(trpDetails);
        }

    }

    public FirebaseModel(FirebaseBase f) {


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        this.f = f;

    }

    public void getfromFireBase() {

        userslist = new ArrayList<>();
        tribObj = new Trip();
        tripList = new ArrayList<>();

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("upcoming").child(userId);

        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tripList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    Trip user = userSnapshot.getValue(Trip.class);

                    tripList.add(user);

                }
                f.showOnSuccLoad(tripList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


}
