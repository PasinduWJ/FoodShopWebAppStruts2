
package com.example.model;

public class GridUserOrderModel {
    
    private String foodName;
    private int maxOrder;
    private OrderModel order;

    public GridUserOrderModel() {
    }

    public GridUserOrderModel(String foodName, int maxOrder, OrderModel order) {
        this.foodName = foodName;
        this.maxOrder = maxOrder;
        this.order = order;
    }

    public int getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(int maxOrder) {
        this.maxOrder = maxOrder;
    }

    
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "GridUserOrderModel{" + "foodName=" + foodName + ", maxOrder=" + maxOrder + ", order=" + order + '}';
    }

}
