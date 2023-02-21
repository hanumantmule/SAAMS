package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Events implements Serializable {

    String title;
    String description;
    String date;
    String location;
    String coverImage;
    ArrayList<String> imageGallery;
    String hostedBy;
    String hostDetails;

    public Events(String title, String description, String date, String location, String coverImage, ArrayList<String> imageGallery, String hostedBy, String hostDetails) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.coverImage = coverImage;
        this.imageGallery = imageGallery;
        this.hostedBy = hostedBy;
        this.hostDetails = hostDetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getcoverImage() {
        return coverImage;
    }

    public void setcoverImage(String startImage) {
        this.coverImage = startImage;
    }

    public ArrayList<String> getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(ArrayList<String> imageGallery) {
        this.imageGallery = imageGallery;
    }

    public String getHostedBy() {
        return hostedBy;
    }

    public void setHostedBy(String hostedBy) {
        this.hostedBy = hostedBy;
    }

    public String getHostDetails() {
        return hostDetails;
    }

    public void setHostDetails(String hostDetails) {
        this.hostDetails = hostDetails;
    }
}
