package com.project.digicampus.models;

import java.util.ArrayList;

public class GroupModel {
    private ArrayList<String> assignedProfessors;
    private ArrayList<String> assignedStudents;
    private ArrayList<Long> lectureDays;
    private ArrayList<AttendanceDay> attendanceDays;

    public GroupModel(){}

    public GroupModel(ArrayList<String> assignedProfessors, ArrayList<String> assignedStudents, ArrayList<Long> lectureDays, ArrayList<AttendanceDay> attendanceDays) {
        this.assignedProfessors = assignedProfessors;
        this.assignedStudents = assignedStudents;
        this.lectureDays = lectureDays;
        this.attendanceDays = attendanceDays;
    }

    public ArrayList<String> getAssignedProfessors() {
        return assignedProfessors;
    }

    public void setAssignedProfessors(ArrayList<String> assignedProfessors) {
        this.assignedProfessors = assignedProfessors;
    }

    public ArrayList<String> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(ArrayList<String> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    public ArrayList<Long> getLectureDays() {
        return lectureDays;
    }

    public void setLectureDays(ArrayList<Long> lectureDays) {
        this.lectureDays = lectureDays;
    }

    public ArrayList<AttendanceDay> getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(ArrayList<AttendanceDay> attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

}

