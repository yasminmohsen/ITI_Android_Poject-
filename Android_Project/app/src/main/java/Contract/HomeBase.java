package Contract;

import java.util.List;

import Pojos.Trip;

public interface HomeBase {


    public  void   showOnSucess(List<Trip> tripList);
    public void showOnFail();
    public  void   showOnSucessFirebase(List<Trip> tripList);
    public void showOnFaiIntenetConnet();

}
