package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.List;

public class Attendance implements Serializable {

    String year;
    List<AttendanceYearDetails> attendanceDetails;

    public Attendance(String year, List<AttendanceYearDetails> attendanceDetails) {
        this.year = year;
        this.attendanceDetails = attendanceDetails;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<AttendanceYearDetails> getAttendanceDetails() {
        return attendanceDetails;
    }

    public void setAttendanceDetails(List<AttendanceYearDetails> attendanceDetails) {
        this.attendanceDetails = attendanceDetails;
    }
}
