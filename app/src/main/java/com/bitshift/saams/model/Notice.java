package com.bitshift.saams.model;

import java.io.Serializable;

public class Notice implements Serializable {
    String id;
    String title;
    String datetime;
    String description;
    String noticeBy;
    String circularNo;
    String isRead;

    public Notice(String id, String title, String datetime, String description, String noticeBy, String circularNo, String isRead) {
        this.id = id;
        this.title = title;
        this.datetime = datetime;
        this.description = description;
        this.noticeBy = noticeBy;
        this.circularNo = circularNo;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoticeBy() {
        return noticeBy;
    }

    public void setNoticeBy(String noticeBy) {
        this.noticeBy = noticeBy;
    }

    public String getCircularNo() {
        return circularNo;
    }

    public void setCircularNo(String circularNo) {
        this.circularNo = circularNo;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
