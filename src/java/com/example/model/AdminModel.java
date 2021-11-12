
package com.example.model;

import java.io.Serializable;

public class AdminModel implements Serializable{
    
    private String adminName;
    private int phNumber;
    private String password;
    private String Role;
    private Boolean state;
    private String crDateTime, upDateTime, updateBy;

    public AdminModel() {
    }

    public AdminModel(String adminName, int pNumber, String password, String Role, Boolean state, String cDateTime, String uDateTime, String updateBy) {
        this.adminName = adminName;
        this.phNumber = pNumber;
        this.password = password;
        this.Role = Role;
        this.state = state;
        this.crDateTime = cDateTime;
        this.upDateTime = uDateTime;
        this.updateBy = updateBy;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(int phNumber) {
        this.phNumber = phNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
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
        return "AdminModel{" + "adminName=" + adminName + ", pNumber=" + phNumber + ", password=" + password + ", Role=" + Role + ", state=" + state + ", cDateTime=" + crDateTime + ", uDateTime=" + upDateTime + ", updateBy=" + updateBy + '}';
    }

}
