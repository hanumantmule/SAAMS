package com.bitshift.saams.model;

public class Notification {

    String id;
    String title;
    String message;
    String type;
    String type_id;
    String image;

    public Notification(String id, String title, String message, String type, String type_id, String image) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.type_id = type_id;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
