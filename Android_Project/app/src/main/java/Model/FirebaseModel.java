package Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Pojos.Trip;
import Pojos.Users;

public class FirebaseModel {
    DatabaseReference databaseReferenceUsers;
    Trip trp;

    public void addtoFireBase(List<Trip> tArray){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("upcoming");
        databaseReferenceUsers.child(userId).removeValue(); // remove method
        Users trpDetails = new Users();
        for (int i=0; i < tArray.size(); i++){
            trpDetails.setTripId(tArray.get(i).getTripId());
            trpDetails.setTripName(tArray.get(i).getTripName());
            trpDetails.setStartPoint(tArray.get(i).getStartPoint());
            trpDetails.setEndPoint(tArray.get(i).getEndPoint());
            trpDetails.setNote(tArray.get(i).getNote());
            trpDetails.setTripDirection(tArray.get(i).getTripDirection());
            trpDetails.setTripStatus(tArray.get(i).getTripStatus());
            trpDetails.setDate(tArray.get(i).getDate());
            trpDetails.setTime(tArray.get(i).getTime());
            databaseReferenceUsers.child(userId).child(tArray.get(i).getTripId()).setValue(trpDetails);
        }
       // trp = new Trip();
        //String trip = databaseReferenceUsers.push().getKey();
    }

    public FirebaseModel() {
    }
}
