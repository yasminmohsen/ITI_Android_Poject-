package Presenter;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;

public class Presenter {

    private List<Trip> tripList =new ArrayList<Trip>();
    TripDAO triptDAO;
    final AppDataBase database;


    public Presenter(Context contx){


        database = Room.databaseBuilder(contx,AppDataBase.class,"db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();


    }
    public List<Trip> getTripPresenter()
    {


        tripList=triptDAO.getTrips();



return  tripList;



    }

    public void insertTripPresenter(String tName,String id, String startPoint,String endPoint,String note,String date,String time,String dirction,String staus)
    {
        Trip t=new Trip();
        t.setTripName(tName);
        t.setTripId(id ); // from firebase  the trip id !!
        t.setStartPoint(startPoint);
        t.setEndPoint(endPoint);
        t.setNote(note);
        t.setDate(date);
        t.setTime(time);
        t.setTripDirection(dirction);
        t.setTripStatus(staus);

        triptDAO.insertTrip(t);


    }




    public void deleteTripPresenter(Trip trip)
    {

        triptDAO.deleteTrip(trip);


    }

    public void updateTripPresenter(Trip trip)
    {

        triptDAO.updateTrip(trip);


    }

    public Trip getSpecificTrip(String id)
    {
        Trip t=new Trip();

       t=triptDAO.getTriptWithId(id);
       return  t;
    }


}
