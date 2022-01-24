package com.project.digicampus.models;

import java.util.Date;

public class AnnouncementModel {
    private String title;
    private String content;
    private long date;
    private String subjectID;

    public AnnouncementModel(String title, String content, long date, String subjectID) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.subjectID = subjectID;
    }

    public AnnouncementModel(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
}
