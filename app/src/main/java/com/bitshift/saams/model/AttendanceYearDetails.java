package com.bitshift.saams.model;

import java.io.Serializable;

public class AttendanceYearDetails implements Serializable {
    String month;
    String days;
    String present;
    String absent;
    String percentage;

    public AttendanceYearDetails(String month, String days, String present, String absent, String percentage) {
        this.month = month;
        this.days = days;
        this.present = present;
        this.absent = absent;
        this.percentage = percentage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
