package Contract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import Pojos.Trip;
@Dao
public interface TripDAO {

    @Insert
    public void insertTrip(Trip...trips);

    @Update
    public  void updateTrip(Trip...trips);

    @Delete
    public  void deleteTrip(Trip...trips);

    @Query("SELECT * FROM Trip")
    public List<Trip> getTrips();

    @Query("SELECT * FROM Trip where tripId=:number")
    public Trip getTriptWithId(String number);


}
