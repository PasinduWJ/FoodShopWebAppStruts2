
package com.example.model;

import java.io.Serializable;

public class OrderModel implements Serializable{
    private int id;
    private String userName;
    private int foodId, quantity;
    private String crDateTime, upDateTime, updateBy;
    private Boolean delivery;
    private Boolean state;

    public OrderModel() {
    }

    public OrderModel(int id, String userName, int quantity, String uDateTime) {
        this.id = id;
        this.userName = userName;
        this.quantity = quantity;
        this.upDateTime = uDateTime;
    }

    public OrderModel(String userName, int foodId, int quantity, String upDateTime) {
        this.userName = userName;
        this.foodId = foodId;
        this.quantity = quantity;
        this.upDateTime = upDateTime;
    }
    

    public OrderModel(int id, int foodId, int quantity, String uDateTime, Boolean delivery) {
        this.id = id;
        this.foodId = foodId;
        this.quantity = quantity;
        this.upDateTime = uDateTime;
        this.delivery = delivery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderModel{" + "id=" + id + ", userName=" + userName + ", foodId=" + foodId + ", quantity=" + quantity + ", crDateTime=" + crDateTime + ", upDateTime=" + upDateTime + ", updateBy=" + updateBy + ", delivery=" + delivery + ", state=" + state + '}';
    }

}
