package com.bitshift.saams.model;

import java.io.Serializable;

public class ExamScore implements Serializable {
    String examName;
    String dateTime;
    String marksObtaibed;
    String marksOutOf;
    String grade;

    public ExamScore(String examName, String dateTime, String marksObtaibed, String marksOutOf, String grade) {
        this.examName = examName;
        this.dateTime = dateTime;
        this.marksObtaibed = marksObtaibed;
        this.marksOutOf = marksOutOf;
        this.grade = grade;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMarksObtaibed() {
        return marksObtaibed;
    }

    public void setMarksObtaibed(String marksObtaibed) {
        this.marksObtaibed = marksObtaibed;
    }

    public String getMarksOutOf() {
        return marksOutOf;
    }

    public void setMarksOutOf(String marksOutOf) {
        this.marksOutOf = marksOutOf;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
