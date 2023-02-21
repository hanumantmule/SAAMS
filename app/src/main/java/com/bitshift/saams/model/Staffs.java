package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Staffs implements Serializable {

    String name;
    String qualification;
    String subject;
    String profile_pic;

    public Staffs(String name, String qualification, String subject, String profile_pic) {
        this.name = name;
        this.qualification = qualification;
        this.subject = subject;
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
