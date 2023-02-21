package com.bitshift.saams.model;

import java.io.Serializable;

public class Subject implements Serializable {
    String id;
    String subject_name;
    String subject_type;

    public Subject(String id, String subject_name, String subject_type) {
        this.id = id;
        this.subject_name = subject_name;
        this.subject_type = subject_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(String subject_type) {
        this.subject_type = subject_type;
    }
}
