
package com.example.action;

import com.example.dao.AdminDao;
import com.example.dao.FoodDao;
import com.example.dao.OrderDao;
import com.example.dao.UserDao;
import com.example.model.AdminModel;
import com.example.model.FoodModel;
import com.example.model.UserModel;
import com.example.util.DateTimeSetting;
import com.example.util.PasswordEncrypt;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class AdminPageAction extends ActionSupport implements ModelDriven<FoodModel>{

    private FoodModel fModel = new FoodModel();
    private AdminModel adModel =new AdminModel();
    private String adminName;
    private String message;

    @Override
    public String execute(){
    HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
            String log = session.get("loger").toString();
            if(log.equals("admin")){
            this.adminName = session.get("logName").toString();
            
            resMap.put("adminName", getAdminName());
            
            }else if(log.equals("user")){
                return "user";
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
    
    public String getFoodItems(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();

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
    
    public String editFoodItem(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        System.out.println(fModel.toString());
        try {
            boolean valid = false;
                if ( fModel.getMaxOrder() >= OrderDao.getAllOrdersByFoodId(fModel.getId())) {
                    valid = true;
                }
                if (valid) {
                    FoodModel foodModel = new FoodModel(fModel.getId(), fModel.getFoodName(), fModel.getUnitPrice(),
                            fModel.getMaxOrder(), fModel.getAvailable(), DateTimeSetting.getCurrentDateTime(), adminName);

                    if (FoodDao.editFoodItem(foodModel)) {
                        List<FoodModel> foodList = FoodDao.getAllFoodList();
                        JsonArray allOrders = OrderDao.getAllOrders();
                        resMap.put("food", foodList);
                        resMap.put("allOrders", allOrders);
                        this.message = "Succesfully Item Edited..";
                    } else {
                        this.message = "Food Item Edit fail... try with another Food Name";
                    }
                } else {
                    this.message = "Food Item Edit fail... Already order that Food";
                }
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
    
     public String getAllOrders(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
        
            List<FoodModel> foodList = FoodDao.getAllFoodListFor();
            JsonArray allOrders = OrderDao.getAllOrdersForAdmin();
            
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
    
     public String addFoodItem() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {
            FoodModel foodModel = new FoodModel(fModel.getFoodName(), fModel.getUnitPrice(),
                        fModel.getMaxOrder(),DateTimeSetting.getCurrentDateTime(),getAdminName());
            if (FoodDao.addNewFood(foodModel)) {
                List<FoodModel> foodList = FoodDao.getAllFoodListFor();
                JsonArray allOrders = OrderDao.getAllOrdersForAdmin();
            
                resMap.put("food", foodList);
                resMap.put("allOrders", allOrders);
                
                this.message = "Food Added Successfull...";
            } else {
                this.message = "Food Added is Fail...";
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
     
    public String deleteFood() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try {

            if (FoodDao.deleteFoodItem(getAdminName(), DateTimeSetting.getCurrentDateTime(), fModel.getId())) {
                List<FoodModel> foodList = FoodDao.getAllFoodListFor();
                JsonArray allOrders = OrderDao.getAllOrdersForAdmin();
            
                resMap.put("food", foodList);
                resMap.put("allOrders", allOrders);
                
                this.message = "Food Delete Successfull...";
            } else {
                this.message = "Food Delete is Fail...";
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
    
    public String getAllAdmin(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            List<AdminModel> allAdmin = AdminDao.getAllAdmin();
            resMap.put("allAdmin", allAdmin);

            String json = gson.toJson(resMap);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
        }catch(Exception e){
            this.message = e.toString();
            return ERROR;
        }
        return SUCCESS;
    }
     
    public String deleteAdmin(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
            if(AdminDao.deleteAdmin(getAdminName(), fModel.getUpdateBy(), DateTimeSetting.getCurrentDateTime())){
                this.message = "Admiin Delete Successfull";
            }else{
                this.message = "Admin Delete Fail";
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
    
    public String addNewAdmin(){
        try{
        Map resMap = new HashMap();
        Gson gson = new Gson();
        HttpServletResponse response = ServletActionContext.getResponse();
        if(adModel.getAdminName().equals(" ") || adModel.getPassword().equals(" ")){
            this.message = "Admiin Name or Password Cannot be Empty";
        }

            if(AdminDao.registerAdmin(new AdminModel(adModel.getAdminName(), adModel.getPhNumber(), PasswordEncrypt.pEncode(adModel.getPassword()),
                adModel.getAdRole(), DateTimeSetting.getCurrentDateTime(), adModel.getUpdateBy()))){
                this.message = "Admiin Add Successfull";
            }else{
                this.message = "Admin ADD Fail";
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
    
    
    public String editAdminDetails(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resMap = new HashMap();
        Gson gson = new Gson();
        try{
        
            adModel.setUpDateTime(DateTimeSetting.getCurrentDateTime());
            if(AdminDao.editAdmin(getAdModel(),getAdminName())){
                this.message = "Admin Edit Successfull";
            }else{
                this.message = "Admin Edit Fail";
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
    
    
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public FoodModel getModel() {
        return fModel;
    }

    public AdminModel getAdModel() {
        return adModel;
    }

    public void setAdModel(AdminModel adModel) {
        this.adModel = adModel;
    }

    
}
