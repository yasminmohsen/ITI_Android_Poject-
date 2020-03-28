package Pojos;

public class Users {
    private String userId; //don't save it i just return it "get only"
    private String tripId;
    private String tripName;
    private String startPoint;
    private String endPoint;
    private String note;
    private String tripDirection;
    private String tripStatus;
    private String date;
    private String time;
    private String startUi;
    private String endUi;

    public Users(String tripId, String tripName, String startPoint, String endPoint, String note, String tripDirection, String tripStatus, String date, String time, String startUi, String endUi) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.note = note;
        this.tripDirection = tripDirection;
        this.tripStatus = tripStatus;
        this.date = date;
        this.time = time;
        this.startUi = startUi;
        this.endUi = endUi;
    }

    public Users() {
    }



    public String getUserId() {
        return userId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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
}
