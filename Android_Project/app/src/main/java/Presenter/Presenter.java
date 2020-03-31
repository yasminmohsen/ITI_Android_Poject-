package Presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contract.AddBase;
import Contract.FirebaseBase;
import Contract.TripDAO;
import Contract.HomeBase;
import Model.AppDataBase;
import Model.FirebaseModel;
import Pojos.Trip;
import view.Home;

public class Presenter implements AddBase,FirebaseBase{

    private List<Trip> tripList = new ArrayList<Trip>();

    TripDAO triptDAO;
    final AppDataBase database;
    DatabaseReference databaseReferenceUsers;
    FirebaseModel firebaseModel;
    AddPresenter addPresenter;

    HomeBase h;
    Home home;
    Activity a;
    Context contx;
    boolean result;



    public Presenter(Context contx, HomeBase ho) {


        database = Room.databaseBuilder(contx, AppDataBase.class, "db_Trps").allowMainThreadQueries().build();
        triptDAO = database.getTriptDAO();
        h = ho;
        this.contx = contx;

        addPresenter = new AddPresenter(contx, this);
        firebaseModel = new FirebaseModel(this);

    }

    public void getTripPresenter() {

        result = isConnectedToInternet();
        tripList = triptDAO.getTrips();

        if (tripList.isEmpty()) {
            if(result==true) {
                retrieveTripfromFirebase();
            }

        }
        h.showOnSucess(tripList);

    }


    public void deleteTripPresenter(Trip trip) {

        triptDAO.deleteTrip(trip);


    }


    public Trip getSpecificTrip(String id) {
        Trip t = new Trip();


        t = triptDAO.getTriptWithId(id);

        return t;


    }

    public void addTriptoFirebase(List<Trip> arrTrips) {

        firebaseModel.addtoFireBase(arrTrips);

    }


    public void retrieveTripfromFirebase() {

        firebaseModel.getfromFireBase();

    }


    public void addToHistoryFireBase() {

        firebaseModel.getfromFireBase();

    }





    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) contx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    @Override
    public void showOnSuccLoad(List<Trip> t) {

        tripList = t;
        if (tripList.isEmpty() == false) {
            for (int i = 0; i < tripList.size(); i++) {

                  addPresenter.insertTripPresenter(tripList.get(i));
            }
            h.showOnSucess(tripList);
        }

    }

    @Override
    public void showOnSucessAdd() {

    }

    @Override
    public void showOnFailFail() {

    }



}
