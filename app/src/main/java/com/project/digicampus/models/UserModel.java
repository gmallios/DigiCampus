package com.project.digicampus.models;

public class UserModel {

    private String firebaseUID;
    private String email;
    private String firstName;
    private String lastName;
    private Integer userType;

    public UserModel(String firebaseUID, String email, String firstName, String lastName, Integer userType) {
        this.firebaseUID = firebaseUID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType.toString();
    }


}

