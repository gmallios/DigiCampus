package com.project.digicampus.models;

import java.util.ArrayList;

public class AttendanceDay{
    private Long date;
    private ArrayList<String> userIDs;

    public AttendanceDay(){}

    public AttendanceDay(Long date, ArrayList<String> userIDs) {
        this.date = date;
        this.userIDs = userIDs;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public ArrayList<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(ArrayList<String> userIDs) {
        this.userIDs = userIDs;
    }
}