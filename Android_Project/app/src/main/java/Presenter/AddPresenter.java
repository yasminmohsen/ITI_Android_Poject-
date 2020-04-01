package Presenter;

import android.content.Context;

import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contract.AddBase;
import Contract.HomeBase;
import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;

public class AddPresenter {


    private List<Trip> tripList =new ArrayList<Trip>();
    TripDAO triptDAO;
    final AppDataBase database;
    DatabaseReference databaseReferenceUsers;
    AddBase addBase;
    Trip newtrip;


    public AddPresenter(Context contx, AddBase add){


        database = Room.databaseBuilder(contx,AppDataBase.class,"db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();
        addBase=add;



    }


    public void insertTripPresenter(Trip t)
    {
        newtrip=t;
        String id= newtrip.getTripId();
        Trip trip = triptDAO.getTriptWithId(id);
        if(trip == null) {
            if (newtrip.getTripName().isEmpty()|| newtrip.getTripId().isEmpty()|| newtrip.getDate().isEmpty()||
                    newtrip.getTime().isEmpty()|| newtrip.getStartUi().isEmpty()||newtrip.getEndUi().isEmpty()||
                    newtrip.getStartPoint().isEmpty()||newtrip.getEndPoint().isEmpty()||newtrip.getTripStatus().isEmpty()||newtrip.getTripDirection().isEmpty())
            {
                addBase.showOnFailFail();
            } else {

                triptDAO.insertTrip(newtrip);
                addBase.showOnSucessAdd();
            }
        }

    }

}
