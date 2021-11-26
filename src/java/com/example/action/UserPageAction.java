
package com.example.action;

import com.example.dao.FoodDao;
import com.example.dao.OrderDao;
import com.example.dao.UserDao;
import com.example.model.FoodModel;
import com.example.model.OrderModel;
import com.example.model.UserModel;
import com.example.util.DateTimeSetting;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class UserPageAction extends ActionSupport implements ModelDriven<UserModel>{

    private UserModel uModel = new UserModel();
    private String usName;
    private String message;
    
    @Override
    public String execute(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
            String log = session.get("loger").toString();
            if(log.equals("user")){
            uModel.setUserName( session.get("logName").toString());
            resMap.put("userName", uModel.getUserName());
            }else if(log.equals("admin")){
                return "admin";
            }else{
                this.message = "Please LogIn";
                return LOGIN;
            }
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
            List<OrderModel> orderList = OrderDao.getOrderListByUserName(uModel.getUserName());
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
    
    public String getAllCustomers(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            List<UserModel> allCustomers = UserDao.getAllUsers();
            resMap.put("allCustomers", allCustomers);

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }
    public String getCustomerDetails(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            UserModel user = UserDao.getUserByUserNameFor(uModel.getUserName());
            if(user.getUserName() != null){
                List<UserModel> userList = new ArrayList();
                userList.add(user);
                resMap.put("customer", userList);
            }
            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }

    
    public String deleteUser(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            uModel.setUpDateTime(DateTimeSetting.getCurrentDateTime());
            if(UserDao.deleteUserByAdmin(uModel)){
                this.message = "User Delete Successfull";
            }else{
                this.message = "User Delete Fail";
            }
            resMap.put("message", getMessage());

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String editUserDetails(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
        
            uModel.setUpDateTime(DateTimeSetting.getCurrentDateTime());
            if(UserDao.editUserByAdmin(uModel, getUsName())){
                this.message = "User Edit Successfull";
            }else{
                this.message = "User Edit Fail";
            }
            resMap.put("message", getMessage());

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
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
    public UserModel getModel() {
        return uModel;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

}
