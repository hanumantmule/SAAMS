package com.bitshift.saams.model;

import java.io.Serializable;

public class TimeTable implements Serializable {

    String subjectName;
    String timeSlot;
    String teacherName;
    String day;
    String lectureNo;
    String teacherImg;

    public TimeTable(String subjectName, String timeSlot, String teacherName, String day, String lectureNo, String teacherImg) {
        this.subjectName = subjectName;
        this.timeSlot = timeSlot;
        this.teacherName = teacherName;
        this.day = day;
        this.lectureNo = lectureNo;
        this.teacherImg = teacherImg;
    }

    public String getLectureNo() {
        return lectureNo;
    }

    public void setLectureNo(String lectureNo) {
        this.lectureNo = lectureNo;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
