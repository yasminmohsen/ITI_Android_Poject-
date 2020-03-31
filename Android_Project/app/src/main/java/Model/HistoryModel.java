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

import Pojos.Trip;
import Pojos.Users;

public class HistoryModel {
    DatabaseReference databaseReferenceUsers;
    Users trpDetails;
    List<Users> userslist;
    Trip tripsList;

    public void addHistorytoFireBase(List<Trip> tArray){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("History");
        databaseReferenceUsers.child(userId).removeValue();
        trpDetails = new Users();
        for (int i=0; i < tArray.size(); i++){
            trpDetails.setTripId(tArray.get(i).getTripId());
            trpDetails.setTripName(tArray.get(i).getTripName());
            trpDetails.setStartPoint(tArray.get(i).getStartPoint());
            trpDetails.setEndPoint(tArray.get(i).getEndPoint());
            trpDetails.setNote(tArray.get(i).getNote());
            trpDetails.setTripDirection(tArray.get(i).getTripDirection());
            tripsList.setTripStatus(tArray.get(i).getTripStatus());
//            if ( tArray.get(i).getTripStatus().equals(a) ){   //if we return flag
//                trpDetails.setTripStatus("Cancel");
//            }else{
//                trpDetails.setTripStatus("Done");
//            }
            trpDetails.setDate(tArray.get(i).getDate());
            trpDetails.setTime(tArray.get(i).getTime());
            trpDetails.setStartUi(tArray.get(i).getStartUi());
            trpDetails.setEndUi(tArray.get(i).getEndUi());

            databaseReferenceUsers.child(userId).child(tArray.get(i).getTripId()).setValue(trpDetails);
        }
    }

    public HistoryModel() {
    }

//    public Trip getHistoryfromFireBase(){
//
//        userslist = new ArrayList<>();
//        tripsList = new Trip();
//
//        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                userslist.clear(); // if it have any user previous as status snapshot obj will have user every time at excute
//
//                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
//                    Users user = userSnapshot.getValue(Users.class);
//                    userslist.add(user);
//                }
//
//
//                for (int i=0; i < userslist.size(); i++){
//                    tripsList.setTripId(userslist.get(i).getTripId());
//                    tripsList.setTripName(userslist.get(i).getTripName());
//                    tripsList.setStartPoint(userslist.get(i).getStartPoint());
//                    tripsList.setEndPoint(userslist.get(i).getEndPoint());
//                    tripsList.setNote(userslist.get(i).getNote());
//                    tripsList.setTripDirection(userslist.get(i).getTripDirection());  //get LatLng to HistoryMap
//                    tripsList.setTripStatus(userslist.get(i).getTripStatus());
//                    tripsList.setDate(userslist.get(i).getDate());
//                    tripsList.setTime(userslist.get(i).getTime());
//                    tripsList.setStartUi(userslist.get(i).getStartUi());
//                    tripsList.setEndUi(userslist.get(i).getEndUi());
//                }
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError){
//
//            }
//
//        });
//            return tripsList;
//
//    }

}
