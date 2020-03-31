package Presenter;

import java.util.List;

import Contract.HistoryBase;
import Contract.HistoryFireBase;
import Model.HistoryModel;
import Pojos.Trip;

public class HistoryPresenter implements HistoryFireBase {

    HistoryModel historyModel;
    HistoryBase h;


    public HistoryPresenter(HistoryBase hB) {
      historyModel=new HistoryModel(this);
      h=hB;

    }

    public  void addToFireBaseHistory(Trip t)
    {

        historyModel.addHistorytoFireBase(t);

    }

    public void getTripFromHistoryFireBase() {

        historyModel.getfromFireBase();

    }


    public void removeTripFromHistoryFireBase(Trip t) {

        historyModel.deleteFromFirebase(t);

    }




    @Override
    public void showOnSuccLoadHistory(List<Trip> t) {
        h.showOnSuccessHistory(t);

    }
}
