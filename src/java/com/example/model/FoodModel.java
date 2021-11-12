
package com.example.model;

import java.io.Serializable;

public class FoodModel implements Serializable{
    
    private int id;
    private String foodName;
    private double unitPrice;
    private int maxOrder;
    private Boolean available;
    private String upDateTime, updateBy;
    private Boolean state;
    
    public FoodModel() {
    }

    public FoodModel(String foodName, double unitPrice, int maxOrder, String upDateTime, String updateBy) {
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.maxOrder = maxOrder;
        this.upDateTime = upDateTime;
        this.updateBy = updateBy;
    }

    public FoodModel(int id, String foodName, double unitPrice, int maxOrder, Boolean available, String upDateTime, String updateBy, Boolean state) {
        this.id = id;
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.maxOrder = maxOrder;
        this.available = available;
        this.upDateTime = upDateTime;
        this.updateBy = updateBy;
        this.state = state;
    }

    public FoodModel(int id, String foodName, double unitPrice, int maxOrder, Boolean available, String upDateTime, String updateBy) {
        this.id = id;
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.maxOrder = maxOrder;
        this.available = available;
        this.upDateTime = upDateTime;
        this.updateBy = updateBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(int maxOrder) {
        this.maxOrder = maxOrder;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FoodModel{" + "id=" + id + ", foodName=" + foodName + ", unitPrice=" + unitPrice + ", maxOrder=" + maxOrder + ", available=" + available + ", upDateTime=" + upDateTime + ", updateBy=" + updateBy + ", state=" + state + '}';
    }

}
