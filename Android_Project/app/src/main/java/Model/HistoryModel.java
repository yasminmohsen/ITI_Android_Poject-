package Model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Contract.HistoryFireBase;
import Pojos.Trip;

public class HistoryModel  {
    DatabaseReference databaseReferenceUsers;

    Trip tripsList;
    String userId;

    List<Trip>tripList;
    HistoryFireBase hB;

    public void addHistoryToFireBase(Trip trip ){

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("History");
      Trip trpObj=new Trip();
            trpObj.setTripId(trip.getTripId());
            trpObj.setTripName(trip.getTripName());
            trpObj.setStartPoint(trip.getStartPoint());
            trpObj.setEndPoint(trip.getEndPoint());
            trpObj.setNote(trip.getNote());
            trpObj.setTripDirection(trip.getTripDirection());
            trpObj.setTripStatus(trip.getTripStatus());
            trpObj.setDate(trip.getDate());
            trpObj.setTime(trip.getTime());
            trpObj.setStartUi(trip.getStartUi());
            trpObj.setEndUi(trip.getEndUi());

            databaseReferenceUsers.child(userId).child(trip.getTripId()).setValue(trpObj);
        }


    public HistoryModel(HistoryFireBase h) {

         userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
         hB=h;

    }


    public void getfromFireBase() {


     tripList = new ArrayList<>();

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("History").child(userId);

        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tripList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    Trip user = userSnapshot.getValue(Trip.class);

                    tripList.add(user);

                }
             hB.showOnSuccLoadHistory(tripList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    public void deleteFromFirebase(Trip trip)
    {
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("History");
        databaseReferenceUsers.child(userId).child(trip.getTripId()).removeValue(); // remove method


    }


}
