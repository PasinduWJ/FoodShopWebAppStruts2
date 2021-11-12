package com.example.action;

import com.example.model.UserModel;
import com.example.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class LoginAction extends ActionSupport implements ModelDriven<UserModel>, ServletRequestAware, ServletResponseAware {

    private boolean adminSwitch;
    private String message;
    private UserModel uModel = new UserModel();
    private boolean remember;
    protected HttpServletRequest servletRequest;
    protected HttpServletResponse servletResponse;

    @Override
    public String execute() {
        UserModel userModel = checkCookie();
        if (userModel == null) {
            return LOGIN;
        } else {
            UserService loginService = new UserService();
            try {
                String res = loginService.verifyLogin(userModel);
                if(res.equals("success")) {
                        Map<String, Object> session = ActionContext.getContext().getSession();
                        session.put("logName", userModel.getUserName());
                        return SUCCESS;
                }else{
                    String resAdmin = loginService.verifyLoginAdmin(userModel);
                    if(resAdmin.equals("success")) {
                        Map<String, Object> session = ActionContext.getContext().getSession();
                        session.put("logName", userModel.getUserName());
                        return "admin";
                    }
                    this.message = "Account is Invalid ";
                    return LOGIN;
                }
            } catch (Exception e) {
                this.message = e.toString();
            }
        }
        return LOGIN;
    }
    public String userLogin(){
        
            UserService loginService = new UserService();
            try {
                String res = loginService.verifyLogin(uModel);
                switch (res) {
                    case "success":
                        Map<String, Object> session = ActionContext.getContext().getSession();
                        session.put("logName", uModel.getUserName());
                        if (this.remember) {
                            Cookie ckUserName = new Cookie("logName", uModel.getUserName());
                            ckUserName.setMaxAge(3600);
                            servletResponse.addCookie(ckUserName);
                            Cookie ckPassword = new Cookie("password", uModel.getPassword());
                            ckPassword.setMaxAge(3600);
                            servletResponse.addCookie(ckPassword);
                        }
                        this.message = uModel.getUserName();
                        return SUCCESS;
                    case "NotPassword":
                        this.message = "Password Not Match";
                        break;
                    case "NotUserName":
                        this.message = "User Not Registered";
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                this.message = e.toString();
            }
        return LOGIN;
    }

    public String adminLogin(){
        
            UserService loginService = new UserService();
            try {
                String res = loginService.verifyLoginAdmin(uModel);
                switch (res) {
                    case "success":
                        Map<String, Object> session = ActionContext.getContext().getSession();
                        session.put("logName", uModel.getUserName());
                        if (this.remember) {
                            Cookie ckUserName = new Cookie("logName", uModel.getUserName());
                            ckUserName.setMaxAge(3600);
                            servletResponse.addCookie(ckUserName);
                            Cookie ckPassword = new Cookie("password", uModel.getPassword());
                            ckPassword.setMaxAge(3600);
                            servletResponse.addCookie(ckPassword);
                        }
                        this.message = uModel.getUserName();
                        return "admin";
                    case "NotPassword":
                        this.message = "Password Not Match";
                        break;
                    case "NotUserName":
                        this.message = "Admin Not Registered";
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                this.message = e.toString();
            }
        return LOGIN;
    }
    
    public String login() {
        if (StringUtils.isEmpty(uModel.getUserName())) {
            addFieldError("userName", "User Name Cannot be Blank");
        }
        if (StringUtils.isEmpty(uModel.getPassword())) {
            addFieldError("password", "Password Cannot be Blank");
        }else{

            if(this.adminSwitch){
                return adminLogin();
            }else{
                return userLogin();
            }
        }
        return LOGIN;
    }

    public String logout() {
//        remove session
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.remove("logName");
        //remove cookue
        for (Cookie ck : servletRequest.getCookies()) {
            if (ck.getName().equalsIgnoreCase("logName")) {
                ck.setMaxAge(0);
                servletResponse.addCookie(ck);
            }
            if (ck.getName().equalsIgnoreCase("password")) {
                ck.setMaxAge(0);
                servletResponse.addCookie(ck);
            }
        }
        return SUCCESS;
    }
    
    private UserModel checkCookie() {
        UserModel userModel = null;
        String userName = "", password = "";
        for (Cookie ck : servletRequest.getCookies()) {
            if (ck.getName().equalsIgnoreCase("logName")) {
                userName = ck.getValue();
            }
            if (ck.getName().equalsIgnoreCase("password")) {
                password = ck.getValue();
            }
        }
        if (!userName.isEmpty() && !password.isEmpty()) {
            userModel = new UserModel(userName, password);
        }
        return userModel;
    }

    @Override
    public UserModel getModel() {
        return uModel;
    }

    public UserModel getuModel() {
        return uModel;
    }

    public void setuModel(UserModel uModel) {
        this.uModel = uModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    public boolean isAdminSwitch() {
        return adminSwitch;
    }

    public void setAdminSwitch(boolean adminSwitch) {
        this.adminSwitch = adminSwitch;
    }

}
