package com.project.digicampus.models;

import android.os.Parcelable;

import java.util.ArrayList;

public class SubjectModel {
    private String name;
    private String description;
    private Boolean lab;
    private ArrayList<String> groups;

    public  SubjectModel(){}


    public SubjectModel(String name, String description, Boolean lab, ArrayList<String> groups) {
        this.name = name;
        this.description = description;
        this.lab = lab;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getLab() {
        return lab;
    }

    public void setLab(Boolean lab) {
        this.lab = lab;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
}
