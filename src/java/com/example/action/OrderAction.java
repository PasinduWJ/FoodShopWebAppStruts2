package com.example.action;

import com.example.dao.FoodDao;
import com.example.dao.OrderDao;
import com.example.model.FoodModel;
import com.example.model.OrderModel;
import com.example.util.DateTimeSetting;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OrderAction extends ActionSupport implements ModelDriven<OrderModel>{

    private OrderModel orderModel = new OrderModel();
    private String newOrder;
    private String message;

    public String addNewOrder() {
        Map resMap = new HashMap();
        Gson gson = new Gson();
        List<OrderModel> order = new ArrayList();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(getNewOrder());
            List list = (ArrayList) obj;
            boolean valid = true;
            for (int i = 0; i < list.size(); i++) {
                JSONObject job = (JSONObject) list.get(i);
                int fId = Integer.parseInt(job.get("foodId").toString());
                if (FoodDao.getMaxOrdersByFoodId(fId) <= OrderDao.getAllOrdersByFoodId(fId)) {
                    valid = false;
                    break;
                }
                order.add(new OrderModel(orderModel.getUserName(), fId, Integer.parseInt(job.get("quantity").toString()),
                        DateTimeSetting.getCurrentDateTime()));
            }
            if (valid) {
                if (OrderDao.saveOrder(order)) {
                    this.message = "Order Added Succesfully";
                } else {
                    this.message = "Order Adding fail Try Again !!";
                }
            } else {
                this.message = "Oder Add fail... Food Are Not Awailable..";
            }
            HttpServletResponse response = ServletActionContext.getResponse();

            resMap.put("message", getMessage());
            String json = gson.toJson(resMap);

            response.setContentType("application/json");
            response.getWriter().write(json);

        } catch (Exception e) {
            this.message = e.toString();
            return ERROR;
        }

        return SUCCESS;
    }

    public String deleteOrder() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {
            if (OrderDao.deleteOrderById(orderModel.getId(), orderModel.getUserName(), DateTimeSetting.getCurrentDateTime())) {
                List<OrderModel> orderList = OrderDao.getOrderListByUserName(orderModel.getUserName());
                resMap.put("order", orderList);
                this.message = "Order Deelete Successfull...";
            } else {
                this.message = "Order Deelete is Fail...";
            }
            resMap.put("message", getMessage());
            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (Exception e) {
            this.message = e.toString();
        }
        return SUCCESS;
    }
    
    public String deleteOrderAdmin() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {
            if (OrderDao.deleteOrderById(orderModel.getId(), orderModel.getUserName(), DateTimeSetting.getCurrentDateTime())) {
                List<FoodModel> foodList = FoodDao.getAllFoodListFor();
                JsonArray allOrders = OrderDao.getAllOrdersForAdmin();
            
                resMap.put("food", foodList);
                resMap.put("allOrders", allOrders);
                
                this.message = "Order Deelete Successfull...";
            } else {
                this.message = "Order Deelete is Fail...";
            }
            resMap.put("message", getMessage());
            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (Exception e) {
            this.message = e.toString();
        }
        return SUCCESS;
    }
    
    public String sendOrder() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {
            if (OrderDao.sendOrder(orderModel.getId(), orderModel.getUserName(), DateTimeSetting.getCurrentDateTime())) {
                List<FoodModel> foodList = FoodDao.getAllFoodListFor();
                JsonArray allOrders = OrderDao.getAllOrdersForAdmin();
            
                resMap.put("food", foodList);
                resMap.put("allOrders", allOrders);
                
                this.message = "Order Send Successfull...";
            } else {
                this.message = "Order Send is Fail...";
            }
            resMap.put("message", getMessage());
            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (Exception e) {
            this.message = e.toString();
        }
        return SUCCESS;
    }

    public String editOrder() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {
            boolean valid = false;
            int fId = orderModel.getFoodId();
            System.out.println(fId);
            if (FoodDao.getMaxOrdersByFoodId(fId) > (OrderDao.getAllOrdersByFoodId(fId) + Integer.parseInt(getNewOrder()) - orderModel.getQuantity()-1 )) {
                valid = true;
            }
            if (valid) {
                OrderModel editOrder = new OrderModel(orderModel.getId(), orderModel.getUserName(), Integer.parseInt(getNewOrder()), DateTimeSetting.getCurrentDateTime());
                System.out.println(editOrder);
                if (OrderDao.editOrder(editOrder)) {
                    List<OrderModel> orderList = OrderDao.getOrderListByUserName(orderModel.getUserName());
                    resMap.put("order", orderList);
                    this.message = "Order Edit Successful...";
                } else {
                    this.message = "Order Edit Fail...";
                }
            } else {
                this.message = "Order Edit Fail... Food Not Available Now..";
            }
            resMap.put("message", getMessage());
            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } catch (Exception e) {
            this.message = e.toString();
        }
            
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public OrderModel getModel() {
        return orderModel;
    }

    public String getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(String newOrder) {
        this.newOrder = newOrder;
    }
    
}
