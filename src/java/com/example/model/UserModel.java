package com.example.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String firstName, lastName, userName, password, con_password;
    private int phNumber;
    private Boolean state;
    private String crDateTime, upDateTime, updateBy;

    public UserModel() {
    }

    public UserModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserModel(String firstName, String lastName, String userName, String password, String con_password, int pNumber, Boolean state, String cDateTime, String uDateTime, String updateBy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.con_password = con_password;
        this.phNumber = pNumber;
        this.state = state;
        this.crDateTime = cDateTime;
        this.upDateTime = uDateTime;
        this.updateBy = updateBy;
    }

    public UserModel(String firstName, String lastName, String userName, String password, int pNumber, Boolean state, String cDateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phNumber = pNumber;
        this.state = state;
        this.crDateTime = cDateTime;
    }

    public UserModel(String firstName, String lastName, String userName, int phNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phNumber = phNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCon_password() {
        return con_password;
    }

    public void setCon_password(String con_password) {
        this.con_password = con_password;
    }

    public int getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(int phNumber) {
        this.phNumber = phNumber;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCrDateTime() {
        return crDateTime;
    }

    public void setCrDateTime(String crDateTime) {
        this.crDateTime = crDateTime;
    }

    public String getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(String upDateTime) {
        this.upDateTime = upDateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "UserModel{" + "firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", password=" + password + ", con_password=" + con_password + ", phNumber=" + phNumber + ", state=" + state + ", crDateTime=" + crDateTime + ", upDateTime=" + upDateTime + ", updateBy=" + updateBy + '}';
    }

}
