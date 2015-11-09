package paul.labat.com.traveldiary.Util;

import android.support.annotation.Nullable;

import java.util.Calendar;


public class DateTimeModel {

    Calendar calendar = Calendar.getInstance();

    private Integer year = calendar.get(Calendar.YEAR);
    private Integer month = calendar.get(Calendar.MONTH);
    private Integer day = calendar.get(Calendar.DAY_OF_MONTH);
    private Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
    private Integer minutes = calendar.get(Calendar.MINUTE);

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
