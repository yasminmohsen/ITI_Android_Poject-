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


    public AddPresenter(Context contx, AddBase add){


        database = Room.databaseBuilder(contx,AppDataBase.class,"db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();
        addBase=add;



    }



    public void insertTripPresenter(String tName,String id, String startPoint,String endPoint,String note,String date,String time,String dirction,String staus,String startUi,String endUi)
    {

        if((tName.isEmpty()||startPoint.isEmpty()||startUi.isEmpty()||endPoint.isEmpty()||endUi.isEmpty()||date.isEmpty()||time.isEmpty()||dirction.isEmpty()))
        {
            addBase.showOnFailFail();
        }
        else{

            Trip t=new Trip();
            t.setTripName(tName);
            t.setTripId(id);
            t.setStartPoint(startPoint);
            t.setEndPoint(endPoint);
            t.setNote(note);
            t.setDate(date);
            t.setTime(time);
            t.setTripDirection(dirction);
            t.setTripStatus(staus);
            // new variable
            t.setStartUi(startUi);
            t.setEndUi(endUi);

            triptDAO.insertTrip(t);

            addBase.showOnSucessAdd();
        }



    }


}
