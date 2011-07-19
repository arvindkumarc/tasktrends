package com.tasktrends.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private final Calendar c = Calendar.getInstance();
    private int hour;
    private int minute;

    public int getYear() {
        return year;
    }

    public void setYear(int mYear) {
        this.year = mYear;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int mMonth) {
        this.month = mMonth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int mDay) {
        this.day = mDay;
    }

    public DateTime() {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        setYear(year);
        setMonth(monthOfYear);
        setDay(dayOfMonth);
    }

    public int getMonthToDisplay() {
        return month + 1;
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        c.set(year, month, day);
        return dateFormat.format(c.getTime());
    }

    public String getTime() {
        return getHourForDisplay() + ":" + getMinuteForDisplay();
    }

    private String getMinuteForDisplay() {
        if (minute < 10)
            return "0" + minute;
        return String.valueOf(minute);
    }

    public String getHourForDisplay() {
        if (hour < 10)
            return "0" + hour;
        return String.valueOf(hour);
    }
}
