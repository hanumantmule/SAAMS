package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Gallery implements Serializable {

    String group;
    ArrayList<GalleryItems> items;

    public Gallery(String group, ArrayList<GalleryItems> items) {
        this.group = group;
        this.items = items;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<GalleryItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<GalleryItems> items) {
        this.items = items;
    }
}
