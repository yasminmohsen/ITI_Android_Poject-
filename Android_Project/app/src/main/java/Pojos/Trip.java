package Pojos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity(tableName = "Trip")
    public class Trip implements Serializable {
    @ColumnInfo(name = "tripName")
    private String tripName;
    @ColumnInfo(name = "startPoint")
    private String startPoint;
    @ColumnInfo(name = "endPoint")
    private String endPoint;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "tripDirection")
    private String tripDirection;
    @ColumnInfo(name = "tripStatus")
    private String tripStatus;

    @ColumnInfo(name = "dateAndTime")
    private Date dateAndTime;
    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "startUi")
    private String startUi;

    @ColumnInfo(name = "endUi")
    private String endUi;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tripId")
    private String tripId;


    public Trip() {
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTripDirection() {
        return tripDirection;
    }

    public void setTripDirection(String tripDirection) {
        this.tripDirection = tripDirection;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartUi() {
        return startUi;
    }

    public void setStartUi(String startUi) {
        this.startUi = startUi;
    }

    public String getEndUi() {
        return endUi;
    }

    public void setEndUi(String endUi) {
        this.endUi = endUi;
    }

    @NonNull
    public String getTripId() {
        return tripId;
    }

    public void setTripId(@NonNull String tripId) {
        this.tripId = tripId;
    }

    public Trip(String tripName, String startPoint, String endPoint, String note, String type, String tripDirection, String tripStatus, Date dateAndTime, String date, String time, String startUi, String endUi, @NonNull String tripId) {
        this.tripName = tripName;
        this.startPoint = startPoint; // for database address
        this.endPoint = endPoint;  // for database address
        this.note = note;
        this.type = type;
        this.tripDirection = tripDirection;
        this.tripStatus = tripStatus;
        this.dateAndTime = dateAndTime;
        this.date = date;     // for alarm manager
        this.time = time;    // for alarm manager
        this.startUi = startUi; // for ui
        this.endUi = endUi;  // for ui
        this.tripId = tripId;
    }
}
