package Contract;

import java.util.List;

import Pojos.Trip;

public interface HistoryBase {


    public void showOnSuccessHistory(List<Trip> tripList);

    public void showOnFailHistory();

}
