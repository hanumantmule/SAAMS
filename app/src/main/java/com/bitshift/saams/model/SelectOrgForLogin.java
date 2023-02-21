package com.bitshift.saams.model;

import java.io.Serializable;

public class SelectOrgForLogin implements Serializable {
    String id;
    String student_name;
    String MainOrgName;
    String SubOrgName;
    String section_name;

    public SelectOrgForLogin(String id, String student_name, String MainOrgName, String subOrgName, String section_name) {
        this.id = id;
        this.student_name = student_name;
        MainOrgName = MainOrgName;
        SubOrgName = subOrgName;
        this.section_name = section_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getMainorgName() {
        return MainOrgName;
    }

    public void setMainorgName(String MainOrgName) {
        MainOrgName = MainOrgName;
    }

    public String getSubOrgName() {
        return SubOrgName;
    }

    public void setSubOrgName(String subOrgName) {
        SubOrgName = subOrgName;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }
}
