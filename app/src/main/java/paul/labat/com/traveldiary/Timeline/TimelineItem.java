package paul.labat.com.traveldiary.Timeline;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimelineItem {
    private String dayMinute;
    private String year;
    private String month;
    private String dayHour;
    private String dayNumber;
    private String dayString;
    private String summary;
    private String cardUUID;
    private String location;

    public String getDayMinute() {
        return dayMinute;
    }

    public void setDayMinute(String dayMinute) {
        this.dayMinute = dayMinute;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDayHour() {
        return dayHour;
    }

    public void setDayHour(String dayHour) {
        this.dayHour = dayHour;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayString() {
        return dayString;
    }

    public void setDayString(String dayString) {
        this.dayString = dayString;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(String cardUUID) {
        this.cardUUID = cardUUID;
    }

    public Date getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(dayNumber), Integer.valueOf(dayHour), Integer.valueOf(dayMinute));
        return calendar.getTime();
    }
}
