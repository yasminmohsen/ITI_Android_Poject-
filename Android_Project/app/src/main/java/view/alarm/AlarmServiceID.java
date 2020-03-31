package view.alarm;

public class AlarmServiceID {

    public int getAlarmServiceId(String s){
        String numberOnly= s.replaceAll("[^0-9]", "");
        long n = Long.parseLong(numberOnly);
        return (int)n;
    }
}
