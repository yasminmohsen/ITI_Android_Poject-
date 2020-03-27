package Presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contract.TripDAO;
import Contract.HomeBase;
import Model.AppDataBase;
import Pojos.Trip;
import view.Home;

public class Presenter {

    private List<Trip> tripList =new ArrayList<Trip>();
    TripDAO triptDAO;
    final AppDataBase database;
    DatabaseReference databaseReferenceUsers;

    HomeBase h;
    Home home;
    Activity a;







    public Presenter(Context contx, HomeBase ho){


        database = Room.databaseBuilder(contx,AppDataBase.class,"db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();
        h=ho;


    }
    public  void getTripPresenter()
    {


        tripList=triptDAO.getTrips();


        h.showOnSucess(tripList);

    }



    public void deleteTripPresenter(Trip trip)
    {

        triptDAO.deleteTrip(trip);


    }



    public Trip getSpecificTrip(String id)
    {
        Trip t=new Trip();


       t=triptDAO.getTriptWithId(id);

       return  t;


    }




    }


