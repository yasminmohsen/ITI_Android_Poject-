package Model;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Contract.TripDAO;
import Pojos.DateTypeConverter;
import Pojos.Trip;

@Database(entities = {Trip.class}, version = 1)

public abstract class AppDataBase extends RoomDatabase {

    public abstract TripDAO getTriptDAO();

}
