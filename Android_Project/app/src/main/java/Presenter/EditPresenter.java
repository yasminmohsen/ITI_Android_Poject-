package Presenter;

import android.content.Context;

import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contract.AddBase;
import Contract.EditBase;
import Contract.TripDAO;
import Model.AppDataBase;
import Pojos.Trip;

public class EditPresenter {


    private List<Trip> tripList =new ArrayList<Trip>();
    TripDAO triptDAO;
    final AppDataBase database;
    DatabaseReference databaseReferenceUsers;
    EditBase edit;


    public EditPresenter(Context contx, EditBase e){


        database = Room.databaseBuilder(contx,AppDataBase.class,"db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();
        edit=e;



    }


    public void updateTripPresenter(Trip trip)
    {

        triptDAO.updateTrip(trip);
        edit.showOnSucessEdit();

    }



}
