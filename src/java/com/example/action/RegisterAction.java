
package com.example.action;

import com.example.dao.UserDao;
import com.example.model.UserModel;
import com.example.service.UserService;
import com.example.util.DateTimeSetting;
import com.example.util.PasswordEncrypt;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;


public class RegisterAction extends ActionSupport implements ModelDriven<UserModel>{
    
    private String number;
    private String message;
    private UserModel userModel = new UserModel();
    
    @Override
    public void validate() {
        if (StringUtils.isEmpty(userModel.getUserName())) {
            addFieldError("userName", "User Name Cannot be Blank");
        }if (StringUtils.isEmpty(userModel.getPassword())) {
            addFieldError("password", "Password Cannot be Blank");
        }if (StringUtils.isEmpty(userModel.getFirstName())) {
            addFieldError("firstName", "First Name Cannot be Blank");
        }if (StringUtils.isEmpty(userModel.getLastName())) {
            addFieldError("lastName", "Last Name Cannot be Blank");
        }if (StringUtils.isEmpty(userModel.getCon_password())) {
            addFieldError("con_password", "Confirm Password Cannot be Blank");
        } else if (!userModel.getCon_password().equals(userModel.getPassword())) {
            addFieldError("con_password", "Confirm Password Must be Same");
        }
        if (StringUtils.isEmpty(getNumber())) {
            addFieldError("number", "Phome Number Cannot be Blank");
        }else{
            try{
                Integer.parseInt(getNumber());
            }catch(Exception e){
                addFieldError("number", "Only Number Enter");
            }
        }
    }
    
    @Override
    public String execute(){
System.out.println(userModel.toString());
            UserService userService = new UserService();
                try {
                    if(!userService.verifyRegister(userModel)){
                        UserModel um = userModel;
                        um.setPhNumber(Integer.parseInt(getNumber()));
                        um.setPassword(PasswordEncrypt.pEncode(userModel.getPassword()));
                        um.setCrDateTime(DateTimeSetting.getCurrentDateTime());
                        if(UserDao.registerUser(um)){
                            return SUCCESS;
                        }
                    }else{
                        this.message = "User Name Already Exeist";                            
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    this.message = e +"";
                }

        return ERROR;
    }

    @Override
    public UserModel getModel() {
        return userModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    
}
