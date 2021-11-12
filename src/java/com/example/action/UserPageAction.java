
package com.example.action;

import com.example.dao.FoodDao;
import com.example.dao.OrderDao;
import com.example.model.FoodModel;
import com.example.model.OrderModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class UserPageAction extends ActionSupport{

    private String userName;
    private String message;
    
    @Override
    public String execute(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
            this.userName = session.get("logName").toString();
            
            resMap.put("userName", getUserName());
            String json = gson.toJson(resMap);

            response.setContentType("application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String getOrders(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
        
            List<FoodModel> foodList = FoodDao.getAllFoodList();
            JsonArray allOrders = OrderDao.getAllOrders();
            
            resMap.put("food", foodList);
            resMap.put("allOrders", allOrders);

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String getFoodItems(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            List<FoodModel> foodList = FoodDao.getAllFoodListFor();
            List<OrderModel> orderList = OrderDao.getOrderListByUserName(getUserName());
            resMap.put("food", foodList);
            resMap.put("order", orderList);

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
