package com.bitshift.saams.model;

import java.io.Serializable;

public class GalleryItems implements Serializable {

    String id;
    String image;
    String date;

    public GalleryItems(String id, String image, String date) {
        this.id = id;
        this.image = image;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
