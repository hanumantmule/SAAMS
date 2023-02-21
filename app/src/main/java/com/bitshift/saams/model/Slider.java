package com.bitshift.saams.model;


public class Slider {

        // image url is used to
        // store the url of image
        int id;
        String imageUrl;

    public Slider(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public Slider() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imageUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }

}
